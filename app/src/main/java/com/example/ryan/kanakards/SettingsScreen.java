package com.example.ryan.kanakards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class SettingsScreen extends AppCompatActivity {
    CheckBox hiraCheck;
    CheckBox kataCheck;
    Button saveButt;
    boolean hiraSet;
    boolean kataSet;
    boolean customSet;
    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        Intent intent = getIntent();
        hiraSet = intent.getBooleanExtra("hiraCheck", true);
        kataSet = intent.getBooleanExtra("kataCheck", true);
        customSet = intent.getBooleanExtra("customCheck", false);
        fileName = intent.getStringExtra("fileName");
        hiraCheck = findViewById(R.id.checkHira);
        kataCheck = findViewById(R.id.checkKata);
        saveButt = findViewById(R.id.saveButt);

        hiraCheck.setChecked(hiraSet);
        kataCheck.setChecked(kataSet);

        hiraCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hiraSet = isChecked;
            }});

        kataCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                kataSet = isChecked;
            }});


        saveButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent result = new Intent();
                result.putExtra("hiraCheck", hiraSet);
                result.putExtra("kataCheck", kataSet);
                result.putExtra("customCheck", customSet);
                result.putExtra("fileName", fileName);
                setResult(SettingsScreen.RESULT_OK, result);
                finish();
            }
        });
    }
}
