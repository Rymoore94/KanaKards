package com.example.ryan.kanakards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingsScreen extends AppCompatActivity {
    public static final int RESULT_FOR_PICK = 1;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    CheckBox hiraCheck;
    CheckBox kataCheck;
    CheckBox customCheck;
    Button saveButt;
    Button loadButt;
    TextView pathBox;
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
        customCheck = findViewById(R.id.checkCustom);
        saveButt = findViewById(R.id.saveButt);
        loadButt = findViewById(R.id.loadCharacters);
        pathBox = findViewById(R.id.pathBox);

        hiraCheck.setChecked(hiraSet);
        kataCheck.setChecked(kataSet);
        customCheck.setChecked(customSet);

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

        customCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customSet = isChecked;

                if(customSet == true){
                    hiraCheck.setChecked(false);
                    kataCheck.setChecked(false);
                    hiraCheck.setClickable(false);
                    kataCheck.setClickable(false);
                    hiraCheck.setAlpha(0.5f);
                    kataCheck.setAlpha(0.5f);
                    hiraSet = false;
                    kataSet = false;
                }
                else{
                    hiraCheck.setClickable(true);
                    kataCheck.setClickable(true);
                    hiraCheck.setAlpha(1.0f);
                    kataCheck.setAlpha(1.0f);
                }
            }});

        loadButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(SettingsScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingsScreen.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_EXTERNAL_STORAGE);
                }

                Intent pickFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                pickFile.setType("*/*");
                pickFile = Intent.createChooser(pickFile, "this is a message");
                startActivityForResult(pickFile, RESULT_FOR_PICK);
            }
        });

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

    public void onActivityResult(int request, int result, Intent data){
        if(request == RESULT_FOR_PICK && result == -1){
            Uri uri = data.getData();
            loadIn(uri);
        }
        else{}
    }

    public void loadIn(Uri uri){    //TODO have it store the data after checking to make sure its valid
        try{
            InputStream file = getContentResolver().openInputStream(uri);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, "UTF8"));
            String data;
            Toast.makeText(SettingsScreen.this, "Seems to have worked...", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            Toast.makeText(SettingsScreen.this, "*internal screaming*", Toast.LENGTH_SHORT).show();
        }
    }
}
