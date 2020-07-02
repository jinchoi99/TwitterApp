package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReplyActivity extends AppCompatActivity {

    EditText etCompose;
    Button btnReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        getSupportActionBar().setTitle("Twitter");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff1da1f2")));
        getSupportActionBar().setLogo(getDrawable(R.drawable.ic_logo));
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        etCompose = findViewById(R.id.etCompose);
        btnReply = findViewById(R.id.btnReply);

        etCompose.setText(getIntent().getStringExtra("username"));

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}