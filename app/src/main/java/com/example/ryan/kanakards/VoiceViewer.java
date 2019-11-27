package com.example.ryan.kanakards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class VoiceViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_viewer);

        //TODO something needs to go here
        Toast.makeText(getApplicationContext(), "You made it to the Voice Section!", Toast.LENGTH_SHORT).show();
    }
}
