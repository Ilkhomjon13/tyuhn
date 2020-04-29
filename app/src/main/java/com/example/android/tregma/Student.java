package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.tregma.help.SessionManager;
import com.example.android.tregma.database.SQLiteHandler;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class Student extends AppCompatActivity {

    private SessionManager sessionManager;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        sessionManager = new SessionManager(getApplicationContext());
        if(!sessionManager.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandler(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

        String userDetails = (user.get("name") + " " + user.get("surname") + " " + user.get("father_name"));

        ((TextView)findViewById(R.id.user_details)).setText(userDetails);
        ((TextView)findViewById(R.id.level)).setText(user.get("level"));
        ((TextView)findViewById(R.id.profession)).setText(user.get("profession"));

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

        startActivity(new Intent(Student.this, LoginPage.class));
        finish();
    }
}

