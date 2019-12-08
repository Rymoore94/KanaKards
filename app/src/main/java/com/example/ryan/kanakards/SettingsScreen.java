package com.example.ryan.kanakards;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Vector;

public class SettingsScreen extends AppCompatActivity {
    private static final int RESULT_FOR_PICK = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private LinearLayout linearLayout;
    private Save saver;
    private CheckBox hiraCheck;
    private CheckBox kataCheck;
    private CheckBox customCheck;
    private CheckBox traceCheck;
    private CheckBox voiceCheck;
    private RadioGroup customGroup;
    private boolean hiraSet;
    private boolean kataSet;
    private boolean customSet;
    private boolean traceSet;
    private boolean voiceSet;
    private String fileName;
    private String customNames;
    private DialogInterface.OnClickListener alertListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings_screen);
        Intent intent = getIntent();
        saver = new Save(getApplicationContext());
        customGroup = new RadioGroup(this);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.addView(customGroup);
        hiraSet = intent.getBooleanExtra("hiraCheck", true);
        kataSet = intent.getBooleanExtra("kataCheck", true);
        customSet = intent.getBooleanExtra("customCheck", false);
        traceSet = intent.getBooleanExtra("traceCheck", false);
        voiceSet = intent.getBooleanExtra("voiceCheck", false);
        fileName = intent.getStringExtra("fileName");
        customNames = intent.getStringExtra("customNames");
        hiraCheck = findViewById(R.id.checkHira);
        kataCheck = findViewById(R.id.checkKata);
        customCheck = findViewById(R.id.checkCustom);
        traceCheck = findViewById(R.id.checkTrace);
        voiceCheck = findViewById(R.id.checkVoice);
        Button saveButt = findViewById(R.id.saveButt);
        Button loadButt = findViewById(R.id.loadCharacters);
        ImageView resetButt = findViewById(R.id.resetButt);

        hiraCheck.setChecked(hiraSet);
        kataCheck.setChecked(kataSet);
        customCheck.setChecked(customSet);
        traceCheck.setChecked(traceSet);
        voiceCheck.setChecked(voiceSet);

        if (customSet) {
            hiraCheck.setClickable(false);
            kataCheck.setClickable(false);
            traceCheck.setClickable(false);
            voiceCheck.setClickable(false);
            hiraCheck.setAlpha(0.5f);
            kataCheck.setAlpha(0.5f);
            traceCheck.setAlpha(0.5f);
            voiceCheck.setAlpha(0.5f);
            linearLayout.setVisibility(View.VISIBLE);
        } else
            linearLayout.setVisibility(View.GONE);

        if (traceSet || voiceSet) {
            if (traceSet) {
                voiceCheck.setClickable(false);
                voiceCheck.setAlpha(0.5f);
            } else {
                traceCheck.setClickable(false);
                traceCheck.setAlpha(0.5f);
            }
            customCheck.setClickable(false);
            customCheck.setAlpha(0.5f);
        }

        String[] customButts = customNames.split(" ");
        if (!customNames.equals("")) {
            for (int x = 1; x < customButts.length; x++) { //set to 1 so that is skips the blank space
                RadioButton temp = new RadioButton(this);
                temp.setText(customButts[x]);
                temp.setId(x);
                customGroup.addView(temp);
            }
            if (!fileName.equals("")) {
                RadioButton tempo;
                for (int x = 1; x < customButts.length; x++) {
                    tempo = findViewById(x);
                    if (tempo.getText().toString().equals(fileName))
                        tempo.setChecked(true);
                }
            }
        }

        hiraCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hiraSet = isChecked;
                if (isChecked) {
                    customSet = false;
                    customCheck.setChecked(false);
                }
                linearLayout.setVisibility(View.GONE);
            }
        });

        kataCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                kataSet = isChecked;
                if (isChecked) {
                    customSet = false;
                    customCheck.setChecked(false);
                }
                linearLayout.setVisibility(View.GONE);
            }
        });

        traceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                traceSet = isChecked;
                if (isChecked) {
                    customSet = false;
                    voiceSet = false;
                    customCheck.setChecked(false);
                    voiceCheck.setChecked(false);
                    customCheck.setClickable(false);
                    voiceCheck.setClickable(false);
                    customCheck.setAlpha(0.5f);
                    voiceCheck.setAlpha(0.5f);
                } else {
                    customCheck.setClickable(true);
                    voiceCheck.setClickable(true);
                    customCheck.setAlpha(1.0f);
                    voiceCheck.setAlpha(1.0f);
                }
                linearLayout.setVisibility(View.GONE);
            }
        });

        voiceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                voiceSet = isChecked;
                if (isChecked) {
                    customSet = false;
                    traceSet = false;
                    customCheck.setChecked(false);
                    traceCheck.setChecked(false);
                    customCheck.setClickable(false);
                    traceCheck.setClickable(false);
                    customCheck.setAlpha(0.5f);
                    traceCheck.setAlpha(0.5f);
                } else {
                    customCheck.setClickable(true);
                    traceCheck.setClickable(true);
                    customCheck.setAlpha(1.0f);
                    traceCheck.setAlpha(1.0f);
                }
                linearLayout.setVisibility(View.GONE);
            }
        });

        customCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customSet = isChecked;

                if (customSet) {
                    hiraCheck.setChecked(false);
                    kataCheck.setChecked(false);
                    traceCheck.setChecked(false);
                    voiceCheck.setChecked(false);
                    hiraCheck.setClickable(false);
                    kataCheck.setClickable(false);
                    traceCheck.setClickable(false);
                    voiceCheck.setClickable(false);
                    hiraCheck.setAlpha(0.5f);
                    kataCheck.setAlpha(0.5f);
                    traceCheck.setAlpha(0.5f);
                    voiceCheck.setAlpha(0.5f);
                    hiraSet = false;
                    kataSet = false;
                    traceSet = false;
                    voiceSet = false;
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    hiraCheck.setClickable(true);
                    kataCheck.setClickable(true);
                    traceCheck.setClickable(true);
                    voiceCheck.setClickable(true);
                    hiraCheck.setAlpha(1.0f);
                    kataCheck.setAlpha(1.0f);
                    traceCheck.setAlpha(1.0f);
                    voiceCheck.setAlpha(1.0f);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

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
                result.putExtra("traceCheck", traceSet);
                result.putExtra("voiceCheck", voiceSet);
                result.putExtra("fileName", fileName);
                result.putExtra("customNames", customNames);
                setResult(SettingsScreen.RESULT_OK, result);
                finish();
            }
        });

        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder build = new AlertDialog.Builder(SettingsScreen.this);
                build.setMessage("Are you sure you want to reset all save data?")
                        .setPositiveButton("Yes", alertListener)
                        .setNegativeButton("No", alertListener).show();
            }
        });

        alertListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == dialogInterface.BUTTON_POSITIVE) {
                    saver.reset();
                    Intent result = new Intent();
                    result.putExtra("hiraCheck", true);
                    result.putExtra("kataCheck", true);
                    result.putExtra("customCheck", false);
                    result.putExtra("traceCheck", false);
                    result.putExtra("voiceCheck", false);
                    result.putExtra("fileName", "");
                    result.putExtra("customNames", "");
                    setResult(SettingsScreen.RESULT_OK, result);
                    finish();
                }
            }
        };
    }

    public void onActivityResult(int request, int result, Intent data) {
        if (request == RESULT_FOR_PICK && result == -1) {
            Uri uri = data.getData();
            loadIn(uri);
        }
    }

    private void loadIn(Uri uri) {
        try {
            InputStream file = getContentResolver().openInputStream(uri);
            BufferedReader read = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            String data;
            String fileName = getFileName(uri);
            Vector<String> contents = new Vector<>(0);
            FileValidity isValid = new FileValidity(contents, fileName);
            if (saver.doesExist(fileName)) {
                Toast.makeText(getApplicationContext(), "File name already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            while ((data = read.readLine()) != null) {
                contents.add(data);
            }
            if (isValid.checkValid()) {
                Toast.makeText(getApplicationContext(), "Successfully loaded", Toast.LENGTH_SHORT).show();
                RadioButton temp = new RadioButton(this);
                temp.setText(fileName);
                customGroup.addView(temp);
                saveCustom(contents, fileName);
            } else
                Toast.makeText(getApplicationContext(), "The file is invalid", Toast.LENGTH_SHORT).show();
            read.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "*internal screaming*", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {    //developer.android.com example code
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = Objects.requireNonNull(result).lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void saveCustom(Vector<String> contents, String fileName) {
        saver.saveCustom(contents, fileName);
        SettingsPacket temp = saver.load();
        customNames = temp.getCustomNames();
    }
}
