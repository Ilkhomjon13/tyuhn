package com.example.android.tregma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linear1;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear3;
    private LinearLayout linear2;
    private LinearLayout linear6;
    private LinearLayout linear7;


    private Intent i = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear6 = (LinearLayout) findViewById(R.id.linear6);
        linear7 = (LinearLayout) findViewById(R.id.linear7);


        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), SignUpTalaba.class);
                startActivity(i);
            }
        });

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), SignUpOq.class);
                startActivity(i);
            }
        });

        linear6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), SignUpOtaOna.class);
                startActivity(i);
            }
        });

        linear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), Info.class);
                startActivity(i);
            }
        });


    }

    }


