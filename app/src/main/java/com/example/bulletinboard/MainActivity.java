package com.example.bulletinboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bulletinboard.util.UserToken;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // clear token on boot for test
        // TODO remove this line
        UserToken.setToken(this, null);

        Intent intent = new Intent(this, BulletinActivity.class);
        startActivity(intent);
        finish();
    }

}