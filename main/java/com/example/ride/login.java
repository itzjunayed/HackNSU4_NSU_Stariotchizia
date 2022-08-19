package com.example.ride;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {
    AppCompatButton loginProfile;
    EditText nameinput, passwordinput;
    String name, pass
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameinput = findViewById(R.id.usernameSignup);

        passwordinput = findViewById(R.id.passwordSignup);

        loginProfile = findViewById(R.id.logintoprofile);
        loginProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameinput.getText().toString();
                email = emailinput.getText().toString();
                nid = nidinput.getText().toString();
                password = passwordinput.getText().toString();
                User user = new User(name, email, password, nid);
                Intent intent = new Intent(signUp.this,login.class);
                startActivity(intent);
            }
        });


    }
}