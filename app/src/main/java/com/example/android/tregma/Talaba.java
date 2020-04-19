package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.tregma.config.SessionManager;

import androidx.appcompat.app.AppCompatActivity;

public class Talaba extends AppCompatActivity {
    private Button logoutBtn;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talaba);

        sessionManager = new SessionManager(getApplicationContext());
        if(!sessionManager.isLoggedIn()) {
            logoutUser();
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ((TextView)findViewById(R.id.user_details)).setText(extras.getString("name") + " " + extras.getString("surname") + " " + extras.getString("father_name"));
            ((TextView)findViewById(R.id.level)).setText(extras.getString("level"));
            ((TextView)findViewById(R.id.profession)).setText(extras.getString("profession"));
        }

        logoutBtn = (Button)findViewById(R.id.btnLogOut);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        sessionManager.setLogin(false);
        startActivity(new Intent(Talaba.this, SignUpTalaba.class));
        finish();
    }
}

