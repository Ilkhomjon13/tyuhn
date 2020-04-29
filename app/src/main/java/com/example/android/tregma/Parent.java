package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.tregma.help.SessionManager;
import com.example.android.tregma.database.SQLiteHandler;

import androidx.appcompat.app.AppCompatActivity;

public class Parent extends AppCompatActivity {

    private SessionManager sessionManager;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents);

        sessionManager = new SessionManager(getApplicationContext());
        if(!sessionManager.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandler(getApplicationContext());

        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void logoutUser() {
        sessionManager.setLogin(false);
        db.deleteUsers();

        startActivity(new Intent(Parent.this, LoginPage.class));
        finish();
    }
}

