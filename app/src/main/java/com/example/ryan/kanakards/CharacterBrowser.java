package com.example.ryan.kanakards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class CharacterBrowser extends AppCompatActivity {
    private List<Characters> pool;
    private boolean isCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_character_browser);

        Intent intent = getIntent();
        String toLoad = intent.getStringExtra("toLoad");
        isCustom = intent.getBooleanExtra("isCustom", false);
        NewCardMethods characterMethods = new NewCardMethods(getApplicationContext());
        characterMethods.fillPool(toLoad);
        pool = characterMethods.getPool();

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        View.OnClickListener massListener = v -> {
            Characters chosen = pool.get(v.getId());
            Intent inspectIntent = new Intent(getApplicationContext(), InspectCharacter.class);
            inspectIntent.putExtra("isCustom", isCustom);
            inspectIntent.putExtra("symbol", chosen.getSymbol());
            inspectIntent.putExtra("roma", chosen.getRoma());

            startActivity(inspectIntent);
        };

        int size = pool.size();
        int numButt = size;
        int numRow = numButt / 4;
        if (numButt % 4 != 0)
            numRow += numButt % 4;

        for (int x = 0; x < numRow; x++) {
            TableRow tempRow = new TableRow(this);
            tableLayout.addView(tempRow);
            tempRow.setGravity(CENTER_HORIZONTAL);
            for (int y = 0; y < 4; y++) {
                if (numButt == 0)
                    break;
                Button temp = new Button(this);
                temp.setId(size - numButt);
                temp.setText(pool.get(size - numButt).getSymbol());
                temp.setTextSize(24f);
                temp.setOnClickListener(massListener);
                tempRow.addView(temp);
                numButt--;
            }
        }
    }
}
