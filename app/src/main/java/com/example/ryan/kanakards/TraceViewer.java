package com.example.ryan.kanakards;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TraceViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_viewer);

        //TODO something needs to go here
        Toast.makeText(getApplicationContext(), "You made it to the Tracing Section!", Toast.LENGTH_SHORT).show();
    }
}
