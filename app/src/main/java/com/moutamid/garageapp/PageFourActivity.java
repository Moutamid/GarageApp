package com.moutamid.garageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;

public class PageFourActivity extends AppCompatActivity {

    private SeekBar disquesAvantSeekBar, plaquettesAvantSeekBar,
            disquesArriereSeekBar, plaquettesArriereSeekBar;
    private CheckBox flexibleGCheckBox, flexibleDCheckBox,
            flexibleArriereGCheckBox, flexibleArriereDCheckBox;
    private RadioGroup disquesArriereRadioGroup;
    private RadioButton disquesArriereOuiRadioButton, disquesArriereNonRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_four);

        // Initialize SeekBars
        disquesAvantSeekBar = findViewById(R.id.disques_avant);
        plaquettesAvantSeekBar = findViewById(R.id.plaquettes_avant);
        disquesArriereSeekBar = findViewById(R.id.disques_arriere);
        plaquettesArriereSeekBar = findViewById(R.id.plaquettes_arriere);

        // Initialize CheckBoxes
        flexibleGCheckBox = findViewById(R.id.flexible_g);
        flexibleDCheckBox = findViewById(R.id.flexible_d);
        flexibleArriereGCheckBox = findViewById(R.id.flexible_arriere_g);
        flexibleArriereDCheckBox = findViewById(R.id.flexible_arriere_d);

        // Initialize RadioGroup and RadioButtons
        disquesArriereRadioGroup = findViewById(R.id.disques_arriere_radio_group);
        disquesArriereOuiRadioButton = findViewById(R.id.disques_arriere_oui);
        disquesArriereNonRadioButton = findViewById(R.id.disques_arriere_non);

        // Initialize Buttons
        Button nextButton = findViewById(R.id.next_button);
        Button backButton = findViewById(R.id.back_button);

        // Set ClickListener for Next Button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    saveDataToStash();
                    Intent intent = new Intent(PageFourActivity.this, PageFiveActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Set ClickListener for Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle going back to previous screen if needed
                finish(); // Finish current activity to go back
            }
        });
    }

    private boolean validateInputs() {
        // Validate SeekBar inputs
        int disquesAvantValue = disquesAvantSeekBar.getProgress();
        int plaquettesAvantValue = plaquettesAvantSeekBar.getProgress();
        int disquesArriereValue = disquesArriereSeekBar.getProgress();
        int plaquettesArriereValue = plaquettesArriereSeekBar.getProgress();

        if (disquesAvantValue < 0 || disquesAvantValue > 100) {
            Toast.makeText(this, "Veuillez entrer une valeur valide pour les disques avant (0-100)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (plaquettesAvantValue < 0 || plaquettesAvantValue > 100) {
            Toast.makeText(this, "Veuillez entrer une valeur valide pour les plaquettes avant (0-100)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate RadioGroup selection
        boolean disquesArriereSelected = disquesArriereOuiRadioButton.isChecked() || disquesArriereNonRadioButton.isChecked();
        if (!disquesArriereSelected) {
            Toast.makeText(this, "Veuillez sélectionner oui ou non pour les disques arrière", Toast.LENGTH_SHORT).show();
            return false;
        }

        // If "Oui" is selected for disques arrière, validate SeekBar input
        if (disquesArriereOuiRadioButton.isChecked()) {
            if (disquesArriereValue < 0 || disquesArriereValue > 100) {
                Toast.makeText(this, "Veuillez entrer une valeur valide pour les disques arrière (0-100)", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // Additional validation for CheckBoxes if needed

        return true;
    }

    private void saveDataToStash() {
        // Collect data from UI components and save to Stash
        Stash.put("disquesAvantValue", disquesAvantSeekBar.getProgress());
        Stash.put("plaquettesAvantValue", plaquettesAvantSeekBar.getProgress());
        Stash.put("disquesArriereValue", disquesArriereSeekBar.getProgress());
        Stash.put("plaquettesArriereValue", plaquettesArriereSeekBar.getProgress());
        Stash.put("flexibleGChecked", flexibleGCheckBox.isChecked());
        Stash.put("flexibleDChecked", flexibleDCheckBox.isChecked());
        Stash.put("flexibleArriereGChecked", flexibleArriereGCheckBox.isChecked());
        Stash.put("flexibleArriereDChecked", flexibleArriereDCheckBox.isChecked());
        Stash.put("disquesArriereOuiSelected", disquesArriereOuiRadioButton.isChecked());
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
