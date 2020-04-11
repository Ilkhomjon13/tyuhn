package com.example.android.tregma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Oqtuvchi extends AppCompatActivity {
    Button buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oqtuvchi);

        Button ButtonJurnal = (Button) findViewById(R.id.ButtonJurnal);

        ButtonJurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent;
                    intent = new Intent(Oqtuvchi.this, Jurnal.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                } //tugadi
            }
        });


    }
}

