package com.example.grannyknow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private Button registerSignup;
    private TextView welcomeText;
    //private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        registerSignup = findViewById(R.id.registerSignup);
      //  login = findViewById(R.id.login);
        welcomeText = findViewById(R.id.WelcomeText);
        registerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                //finish();
            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        welcomeText.setText("Welcome, visitor!");
    }
}
