package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Note extends AppCompatActivity {

    TextView tv_userNote;
    int REQUEST_NOTE_EDIT = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("책 제목 들어갈 자리");
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv_userNote = (TextView)findViewById(R.id.tv_note_userNote);

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_userNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditNote.class);
                startActivityForResult(intent,REQUEST_NOTE_EDIT);
            }
        });

    }
}