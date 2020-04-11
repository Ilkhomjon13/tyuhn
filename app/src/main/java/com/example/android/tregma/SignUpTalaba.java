package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpTalaba extends AppCompatActivity {
    Button buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgntalaba);

        Button ButtonSgnTalaba = (Button) findViewById(R.id.ButtonSgnTalaba);

        ButtonSgnTalaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent;
                    intent = new Intent(SignUpTalaba.this, Talaba.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                } //tugadi
            }
        });

    }
}

