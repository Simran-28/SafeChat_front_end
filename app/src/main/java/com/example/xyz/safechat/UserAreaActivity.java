package com.example.xyz.safechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import javax.xml.transform.Templates;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername= (EditText) findViewById(R.id.etusername);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");

        String message = name + "welcome to your user area";
        welcomeMessage.setText(message);
        etUsername.setText(username);
    }
}
