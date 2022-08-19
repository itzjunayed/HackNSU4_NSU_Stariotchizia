package com.example.ride;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    AppCompatButton loginBtn;
    TextView createText;
    EditText nameinput, passwordinput;
    String name, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameinput = findViewById(R.id.usernameSignup);
        passwordinput = findViewById(R.id.passwordSignup);

        loginBtn = findViewById(R.id.logintoprofile);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,home.class);
                startActivity(intent);
            }
        });

        createText = findViewById(R.id.signup);
        createText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,signUp.class);
                startActivity(intent);
            }
        });


    }
}