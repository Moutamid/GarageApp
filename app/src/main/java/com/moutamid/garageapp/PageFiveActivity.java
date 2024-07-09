package com.moutamid.garageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.fxn.stash.Stash;

public class PageFiveActivity extends AppCompatActivity {

    private EditText pneuAvantTailleEditText, pneuArriereTailleEditText;
    private SeekBar pneuAvantUsureSeekBar, pneuArriereUsureSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_five);

        pneuAvantTailleEditText = findViewById(R.id.pneu_av_taille);
        pneuArriereTailleEditText = findViewById(R.id.pneu_arr_taille);
        pneuAvantUsureSeekBar = findViewById(R.id.usure_avant);
        pneuArriereUsureSeekBar = findViewById(R.id.usure_arriere);

        Button nextButton = findViewById(R.id.next_button);
        Button backButton = findViewById(R.id.back_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Save data to Stash
                    saveDataToStash();

                    // Proceed to the next screen (Page Six in this case)
                    startActivity(new Intent(PageFiveActivity.this, PageSixActivity.class));
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle going back to previous screen if needed
                finish(); // Finish current activity to go back
            }
        });
    }

    private boolean validateInputs() {
        // Retrieve input values
        String pneuAvantTaille = pneuAvantTailleEditText.getText().toString().trim();
        String pneuArriereTaille = pneuArriereTailleEditText.getText().toString().trim();
        int pneuAvantUsure = pneuAvantUsureSeekBar.getProgress();
        int pneuArriereUsure = pneuArriereUsureSeekBar.getProgress();

        // Validate input values
        if (pneuAvantTaille.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer la taille des pneus avant", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pneuArriereTaille.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer la taille des pneus arrière", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pneuAvantUsure < 0 || pneuAvantUsure > 100) {
            Toast.makeText(this, "Veuillez entrer une valeur valide pour l'usure des pneus avant (0-100)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pneuArriereUsure < 0 || pneuArriereUsure > 100) {
            Toast.makeText(this, "Veuillez entrer une valeur valide pour l'usure des pneus arrière (0-100)", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveDataToStash() {
        // Save data to Stash
        Stash.put("pneuAvantTaille", pneuAvantTailleEditText.getText().toString().trim());
        Stash.put("pneuArriereTaille", pneuArriereTailleEditText.getText().toString().trim());
        Stash.put("pneuAvantUsure", pneuAvantUsureSeekBar.getProgress());
        Stash.put("pneuArriereUsure", pneuArriereUsureSeekBar.getProgress());
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
