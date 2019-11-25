package com.example.ryan.kanakards;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Vector;

public class SettingsScreen extends AppCompatActivity {
    public static final int RESULT_FOR_PICK = 1;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    ScrollView customView;
    LinearLayout linearLayout;
    save saver;
    CheckBox hiraCheck;
    CheckBox kataCheck;
    CheckBox customCheck;
    RadioGroup customGroup;
    Button saveButt;
    Button loadButt;
    boolean hiraSet;
    boolean kataSet;
    boolean customSet;
    String fileName;
    String customNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings_screen);
        Intent intent = getIntent();
        saver = new save(getApplicationContext());
        customView = findViewById(R.id.customView);
        customGroup = new RadioGroup(this);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.addView(customGroup);
        hiraSet = intent.getBooleanExtra("hiraCheck", true);
        kataSet = intent.getBooleanExtra("kataCheck", true);
        customSet = intent.getBooleanExtra("customCheck", false);
        fileName = intent.getStringExtra("fileName");
        customNames = intent.getStringExtra("customNames");
        hiraCheck = findViewById(R.id.checkHira);
        kataCheck = findViewById(R.id.checkKata);
        customCheck = findViewById(R.id.checkCustom);
        saveButt = findViewById(R.id.saveButt);
        loadButt = findViewById(R.id.loadCharacters);

        hiraCheck.setChecked(hiraSet);
        kataCheck.setChecked(kataSet);
        customCheck.setChecked(customSet);

        if(!customSet)
            linearLayout.setVisibility(View.GONE);
        else
            linearLayout.setVisibility(View.VISIBLE);

        String[] customButts = customNames.split(" ");
        if(!customNames.equals("")) {
            for (int x = 1; x < customButts.length; x++) { //set to 1 so that is skips the blank space
                RadioButton temp = new RadioButton(this);
                temp.setText(customButts[x]);
                temp.setId(x);
                customGroup.addView(temp);
            }
            if(!fileName.equals("")){
                RadioButton tempo;
                for (int x = 1; x < customButts.length; x++){
                    tempo = findViewById(x);
                    if(tempo.getText().toString().equals(fileName))
                        tempo.setChecked(true);
                }
            }
        }

        hiraCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hiraSet = isChecked;
                if(isChecked) {
                    customSet = false;
                    customCheck.setChecked(false);
                }
                linearLayout.setVisibility(View.GONE);
            }});

        kataCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                kataSet = isChecked;
                if(isChecked) {
                    customSet = false;
                    customCheck.setChecked(false);
                }
                linearLayout.setVisibility(View.GONE);
            }});

        customCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customSet = isChecked;

                if(customSet){
                    hiraCheck.setChecked(false);
                    kataCheck.setChecked(false);
                    hiraCheck.setClickable(false);
                    kataCheck.setClickable(false);
                    hiraCheck.setAlpha(0.5f);
                    kataCheck.setAlpha(0.5f);
                    hiraSet = false;
                    kataSet = false;
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else{
                    hiraCheck.setClickable(true);
                    kataCheck.setClickable(true);
                    hiraCheck.setAlpha(1.0f);
                    kataCheck.setAlpha(1.0f);
                    linearLayout.setVisibility(View.GONE);
                }
            }});

        customGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton temporary = findViewById(i);
                fileName = temporary.getText().toString();
            }
        });
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
                result.putExtra("customNames", customNames);
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
    }

    public void loadIn(Uri uri){
        try{
            InputStream file = getContentResolver().openInputStream(uri);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, "UTF8"));
            String data;
            String fileName = getFileName(uri);
            Vector<String> contents = new Vector<>(0);
            FileValidity isValid = new FileValidity(contents, fileName);
            if(saver.doesExist(fileName)){
                Toast.makeText(getApplicationContext(), "File name already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            while((data = read.readLine()) != null){
                contents.add(data);
            }
            if(isValid.checkValid()) {
                Toast.makeText(getApplicationContext(), "Successfully loaded", Toast.LENGTH_SHORT).show();
                RadioButton temp = new RadioButton(this);
                temp.setText(fileName);
                customGroup.addView(temp);
                saveCustom(contents, fileName);
            }
            else
                Toast.makeText(getApplicationContext(), "The file is invalid", Toast.LENGTH_SHORT).show();
            read.close();
        }
        catch (IOException e){
            Toast.makeText(getApplicationContext(), "*internal screaming*", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileName(Uri uri) {    //developer.android.com example code
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void saveCustom(Vector<String> contents, String fileName){
        saver.saveCustom(contents, fileName);
        SettingsPacket temp = saver.load();
        customNames = temp.getCustomNames();
    }
}
