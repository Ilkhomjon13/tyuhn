package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpOtaOna extends AppCompatActivity {
    Button buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgnotaona);

        Button ButtonSgnOtaona = (Button) findViewById(R.id.ButtonSgnOtaona);

        ButtonSgnOtaona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent;
                    intent = new Intent(SignUpOtaOna.this, OtaOna.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                } //tugadi
            }
        });

    }
}

