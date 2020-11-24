package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bulletinboard.json.LoginResponse;
import com.example.bulletinboard.util.UserToken;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String URL_LOGIN = "https://vcapi.lvdaqian.cn/login";
    private static String article_id = null;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.putExtra("article_id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        article_id = intent.getStringExtra("article_id");

        Button loginButton = ((Button) findViewById(R.id.login));
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        setLoginValidity();

        usernameText.addTextChangedListener(getEmptyTextWatcher(loginButton));
        passwordText.addTextChangedListener(getEmptyTextWatcher(loginButton));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                sendLoginPost(username, password);
            }
        });

    }

    private void sendLoginPost(String username, String password) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(URL_LOGIN)
                .post(body)
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
                        LoginResponse respData = gson.fromJson(responseString, LoginResponse.class);
                        int retcode = respData.code;
                        switch (retcode) {
                            case 0:
                                UserToken.setToken(LoginActivity.this, respData.token);
                                makeToast("登录成功");
                                finish();
                                break;
                            default:
                                makeToast("未知错误 " + respData.message);
                                break;
                        }
                        break;
                    default:
                        makeToast("网络错误("+ response.code() +")");
                        break;
                }
            }
        });
    }

    private TextWatcher getEmptyTextWatcher(Button button) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginValidity();
            }
        };
    }

    private void setLoginValidity() {
        int len1 = ((EditText)findViewById(R.id.username)).getText().toString().length();
        int len2 = ((EditText)findViewById(R.id.password)).getText().toString().length();
        ((Button) findViewById(R.id.login)).setEnabled(len1 != 0 && len2 != 0);
    }

    private void makeToast(String text) {
        LoginActivity.this.runOnUiThread(() ->
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show());
    }

}