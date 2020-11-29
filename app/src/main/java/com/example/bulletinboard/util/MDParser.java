package com.example.bulletinboard.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulletinboard.asset.Asset;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDParser {

    private static final String TAG = "MDParser";
    private static final int TITLE1_SIZE = 24;
    private static final int TITLE2_SIZE = 20;
    private static final int TEXT_SIZE = 16;
    private static final int LIST_MARGIN = 20;
    private static final int BULLET_MARGIN = 0;

    // parse Bold & Italic
    public static SpannableString parseEmphasis(String raw) {
        String line = raw.trim();
        String content = "";
        Pattern pattern = Pattern.compile("(\\*{1,3})([^\\*]+?)\\1");
        Matcher matcher = pattern.matcher(line);
        int end = 0;
        List<EmphToken> tokens = new ArrayList<>();
        while (matcher.find()) {
            content += line.substring(end, matcher.start());
            int type = matcher.group(1).length();
            String text = matcher.group(2);
            end = matcher.end();
            int s = content.length();
            content += text;
            int e = content.length();
            tokens.add(new EmphToken(type, s, e));
        }
        content += line.substring(end);
        SpannableString string = new SpannableString(content);
        int[] style = {Typeface.ITALIC, Typeface.BOLD, Typeface.BOLD_ITALIC};
        for (EmphToken t : tokens) {
            string.setSpan(new StyleSpan(style[t.type - 1]), t.start, t.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return string;
    }

    // parse Image
    public static SpannableString parseImage(Context ctx, String line) {
        // assume image is declared in one single line
        Pattern pattern = Pattern.compile("!\\[(.*?)\\]\\((.*)\\)");
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.matches()) {
            String title = matcher.group(1);
            String path = matcher.group(2);
            Bitmap image = Asset.loadImageAssetThumb(ctx.getAssets(), path);
            SpannableString ret = new SpannableString("  ");
            ret.setSpan(new ImageSpan(ctx, image, ImageSpan.ALIGN_BOTTOM), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return ret;
        }
        return null;
    }

    // parse Title & Unordered List & List
    public static SpannableString parseLine(Context ctx, String raw) {
        String line = raw.trim();
        SpannableString imgSpan = parseImage(ctx, line);
        if (imgSpan != null) {
            return imgSpan;
        }
        Pattern pattern = Pattern.compile("^(##|#|-|\\d+\\.|) *(.*)");
        Matcher matcher = pattern.matcher(line);
        String header, content;
        if (matcher.matches()) {
            header = matcher.group(1);
            content = matcher.group(2);
        } else {
            header = "";
            content = line;
        }
        if (header.length() > 0 && header.charAt(0) <= '9' && header.charAt(0) >= '0') {
            content = header + " " + content;
        }
        SpannableString ret = parseEmphasis(content);
        ret.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ret.setSpan(new AbsoluteSizeSpan(TEXT_SIZE, true), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        switch (header) {
            case "##":
                ret.setSpan(new AbsoluteSizeSpan(TITLE2_SIZE, true), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ret.setSpan(new StyleSpan(Typeface.BOLD), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "#":
                ret.setSpan(new AbsoluteSizeSpan(TITLE1_SIZE, true), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ret.setSpan(new StyleSpan(Typeface.BOLD), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "":
                break;
            case "-":
                ret.setSpan(new BulletSpan(BULLET_MARGIN), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            default:
                ret.setSpan(new LeadingMarginSpan.Standard(LIST_MARGIN), 0, ret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        return ret;
    }

    // Main Function, parse the whole MarkDown String
    public static SpannableStringBuilder parse(Context ctx, String raw) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String pre = preprocess(raw);
        for (String line : pre.split("\n")) {
            SpannableString ret = parseLine(ctx, line);
            builder.append(ret);
            builder.append("\n");
        }
        return builder;
    }

    // auto add an empty line before a Title
    private static String preprocess(String raw) {
        String ret = raw;
        // ret = ret.replaceAll(" *\\\\n *##", "##");
        ret = ret.replaceAll("##", "\n##");
        return ret;
    }

    // used for parsing emphasis
    private static class EmphToken {
        public int type;
        public int start;
        public int end;

        public EmphToken(int t, int s, int e) {
            this.type = t;
            this.start = s;
            this.end = e;
        }
    }
}
