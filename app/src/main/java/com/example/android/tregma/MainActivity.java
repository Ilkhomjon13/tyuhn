package com.example.android.tregma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.tregma.help.CheckConnectivity;
import com.example.android.tregma.help.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn()) {

            String userType = session.getUserType();
            if(userType != null) {
                switch (userType) {
                    case "s":
                        startActivity(new Intent(MainActivity.this, Student.class));
                        finish();
                        break;
                    case "t":
                        startActivity(new Intent(MainActivity.this, Teacher.class));
                        finish();
                        break;
                    case "p":
                        startActivity(new Intent(MainActivity.this, Parent.class));
                        finish();
                        break;
                }
            }
        }

        CheckConnectivity.isConnected(MainActivity.this);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginPage.class));
                finish();
            }
        });

        findViewById(R.id.info_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Info.class));
                finish();
            }
        });

    }
}


