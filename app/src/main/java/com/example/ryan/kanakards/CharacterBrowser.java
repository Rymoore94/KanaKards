package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.List;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class CharacterBrowser extends AppCompatActivity {
    TableLayout tableLayout;
    NewCardMethods characterMethods;
    List<Characters> pool;
    String toLoad;
    boolean isCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_character_browser);

        Intent intent = getIntent();
        toLoad = intent.getStringExtra("toLoad");
        isCustom = intent.getBooleanExtra("isCustom", false);
        characterMethods = new NewCardMethods(getApplicationContext());
        characterMethods.fillPool(toLoad);
        pool = characterMethods.getPool();

        tableLayout = findViewById(R.id.tableLayout);

        View.OnClickListener massListener = new View.OnClickListener(){
            public void onClick(View v){
                Characters chosen = pool.get(v.getId());
                Intent inspectIntent = new Intent(getApplicationContext(), InspectCharacter.class);
                inspectIntent.putExtra("isCustom", isCustom);
                inspectIntent.putExtra("symbol", chosen.getSymbol());
                inspectIntent.putExtra("roma", chosen.getRoma());

                startActivity(inspectIntent);
            }
        };

        int size = pool.size();
        int numButt = size;
        int numRow = numButt/4;
        if(numButt%4 != 0)
            numRow += numButt%4;

        for(int x = 0; x < numRow; x++){
            TableRow tempRow = new TableRow(this);
            tableLayout.addView(tempRow);
            tempRow.setGravity(CENTER_HORIZONTAL);
            for(int y = 0; y < 4; y++){
                if(numButt == 0)
                    break;
                Button temp = new Button(this);
                temp.setId(size-numButt);
                temp.setText(pool.get(size-numButt).getSymbol());
                temp.setTextSize(24f);
                temp.setOnClickListener(massListener);
                tempRow.addView(temp);
                numButt--;
            }
        }
    }
}
