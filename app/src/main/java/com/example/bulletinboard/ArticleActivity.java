package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bulletinboard.json.ArticleResponse;
import com.example.bulletinboard.util.CustomActionBar;
import com.example.bulletinboard.util.MDParser;
import com.example.bulletinboard.util.User;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = "ArticleActivity";
    private static final String URL_ARTICLE = "https://vcapi.lvdaqian.cn/article/";
    private static String article_id;
    private CustomActionBar customActionBar;

    public static void actionStart(Context context, String id, String title, String info) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("info", info);
        intent.setClass(context, ArticleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String info = intent.getStringExtra("info");
        setText(R.id.title, title);
        setText(R.id.info, info);
        article_id = id;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            CustomActionBar customed = new CustomActionBar(this);
            this.customActionBar = customed;
            customed.replaceActionBar(actionBar);
            customed.setTitle(getResources().getString(R.string.app_name));
            customed.setImageLeft(R.drawable.back_arrow);
            customed.getButtonLeft().setOnClickListener(v -> finish());
            customed.setImageRight(R.drawable.star_fill_white);
            setRightButtonByFavor();
            this.customActionBar.getButtonRight().setOnClickListener(v -> {
                User.swapFavorite(this, article_id);
                if (User.getFavorite(v.getContext()).contains(article_id))
                    makeToast("收藏成功");
                setRightButtonByFavor();
            });
        }

        getArticle(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case LoginActivity.LOGIN_SUC:
                getArticle(article_id);
                break;
            default:
                // Return from LoginActivity and Login failed,
                // Go back to BulletinList
                finish();
                return;
        }
    }

    private String getToken() {
        String token = User.getToken(this);
        if (token == null) {
            LoginActivity.actionStart(this);
        }
        return token;
    }

    private void getArticle(String id) {
        String token = getToken();
        if (token == null) {
            Toast.makeText(this, "请登录后查看文章", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder()
                .url(URL_ARTICLE + id + "?markdown=1")
                .addHeader("Authorization", "Bearer " + token)
                .method("GET", null)
                .build();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                makeToast("无法与服务器连接");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                switch (response.code()) {
                    case 200:
                        String responseString = response.body().string();
                        ArticleResponse respData = gson.fromJson(responseString, ArticleResponse.class);
                        int retcode = respData.code;
                        switch (retcode) {
                            case 0:
                                setTextMD(R.id.content, respData.data);
                                break;
                            default:
                                makeToast("未知错误 " + respData.message);
                                break;
                        }
                        break;
                    case 401:
                        // unauthorized
                        // remove saved token and call LOGIN
                        makeToast("登录过期，请重新登录");
                        User.setToken(ArticleActivity.this, null);
                        getToken();
                        break;
                    default:
                        makeToast("网络错误(" + response.code() + ")");
                        break;
                }
            }
        });
    }

    private void makeToast(String text) {
        runOnUiThread(() -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show());
    }

    private void setTextMD(int res_id, String text) {
        runOnUiThread(() -> ((TextView) findViewById(res_id)).setText(MDParser.parse(getApplicationContext(), text)));
    }

    private void setText(int res_id, String text) {
        runOnUiThread(() -> ((TextView) findViewById(res_id)).setText(text));
    }

    private void setRightButtonByFavor() {
        if (User.getFavorite(this).contains(article_id)) {
            this.customActionBar.getButtonRight().setImageResource(R.drawable.star_fill);
        } else {
            this.customActionBar.getButtonRight().setImageResource(R.drawable.star_empty);
        }
    }
}