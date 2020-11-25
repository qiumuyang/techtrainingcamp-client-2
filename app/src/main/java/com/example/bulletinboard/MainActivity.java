package com.example.bulletinboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test token-update by setting invalid token on boot
        // TODO remove this line
        // User.setToken(this, "123");

        Intent intent = new Intent(this, BulletinActivity.class);
        startActivity(intent);
        finish();
    }

}