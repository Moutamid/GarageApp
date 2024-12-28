package com.moutamid.garageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.moutamid.garageapp.helper.Stash;


public class PageSevenActivity extends AppCompatActivity {

    private CheckBox echappementCheckBox, fuiteHuileCheckBox, fuiteLiquideCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_seven);

        echappementCheckBox = findViewById(R.id.echappement);
        fuiteHuileCheckBox = findViewById(R.id.fuite_huile);
        fuiteLiquideCheckBox = findViewById(R.id.fuite_liquide);

        Button saveButton = findViewById(R.id.next_button);
        Button backButton = findViewById(R.id.back_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToStash();
                startActivity(new Intent(PageSevenActivity.this, MainActivity.class));
}
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void saveDataToStash() {
        Stash.put("echappementChecked", echappementCheckBox.isChecked());
        Stash.put("fuiteHuileChecked", fuiteHuileCheckBox.isChecked());
        Stash.put("fuiteLiquideChecked", fuiteLiquideCheckBox.isChecked());
    }
}
