package com.example.ride;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class signUp extends AppCompatActivity {
    AppCompatButton registerButton;
    EditText nameinput, emailinput, nidinput, passwordinput;
    String name, email, nid, password;
    TextView loginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameinput = findViewById(R.id.usernameSignup);
        emailinput = findViewById(R.id.emailSignup);
        nidinput = findViewById(R.id.nidSignup);
        passwordinput = findViewById(R.id.passwordSignup);

        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
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
        loginText = findViewById(R.id.login);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUp.this,login.class);
                startActivity(intent);
            }
        });
    }
}