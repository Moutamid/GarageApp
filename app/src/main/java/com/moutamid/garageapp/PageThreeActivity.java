package com.moutamid.garageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.garageapp.helper.Stash;


public class PageThreeActivity extends AppCompatActivity {

    private CheckBox veilleuseGCheckbox;
    private CheckBox veilleuseDCheckbox;
    private CheckBox eclairagePlaqueGCheckbox;
    private CheckBox eclairagePlaqueDCheckbox;
    private CheckBox stopGCheckbox;
    private CheckBox stopDCheckbox;
    private CheckBox troisiemeStopCheckbox;
    private CheckBox clignotantGCheckbox;
    private CheckBox clignotantDCheckbox;
    private CheckBox marcheArriereGCheckbox;
    private CheckBox marcheArriereDCheckbox;
    private CheckBox antiBrouillardCheckbox;
    private CheckBox optiqueCasserGCheckbox;
    private CheckBox optiqueCasserDCheckbox;
    private CheckBox plaqueImmatriculationArrCheckbox;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_three);

        // Initialize checkboxes
        veilleuseGCheckbox = findViewById(R.id.veilleuse_g);
        veilleuseDCheckbox = findViewById(R.id.veilleuse_d);
        eclairagePlaqueGCheckbox = findViewById(R.id.eclairage_plaque_g);
        eclairagePlaqueDCheckbox = findViewById(R.id.eclairage_plaque_d);
        stopGCheckbox = findViewById(R.id.stop_g);
        stopDCheckbox = findViewById(R.id.stop_d);
        troisiemeStopCheckbox = findViewById(R.id.troisieme_stop);
        clignotantGCheckbox = findViewById(R.id.clignotant_g);
        clignotantDCheckbox = findViewById(R.id.clignotant_d);
        marcheArriereGCheckbox = findViewById(R.id.marche_arriere_g);
        marcheArriereDCheckbox = findViewById(R.id.marche_arriere_d);
        antiBrouillardCheckbox = findViewById(R.id.anti_brouillard);
        optiqueCasserGCheckbox = findViewById(R.id.optique_casser_g);
        optiqueCasserDCheckbox = findViewById(R.id.optique_casser_d);
        plaqueImmatriculationArrCheckbox = findViewById(R.id.plaque_immatriculation_arr);

        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect checkbox states
                boolean veilleuseGChecked = veilleuseGCheckbox.isChecked();
                boolean veilleuseDChecked = veilleuseDCheckbox.isChecked();
                boolean eclairagePlaqueGChecked = eclairagePlaqueGCheckbox.isChecked();
                boolean eclairagePlaqueDChecked = eclairagePlaqueDCheckbox.isChecked();
                boolean stopGChecked = stopGCheckbox.isChecked();
                boolean stopDChecked = stopDCheckbox.isChecked();
                boolean troisiemeStopChecked = troisiemeStopCheckbox.isChecked();
                boolean clignotantGChecked = clignotantGCheckbox.isChecked();
                boolean clignotantDChecked = clignotantDCheckbox.isChecked();
                boolean marcheArriereGChecked = marcheArriereGCheckbox.isChecked();
                boolean marcheArriereDChecked = marcheArriereDCheckbox.isChecked();
                boolean antiBrouillardChecked = antiBrouillardCheckbox.isChecked();
                boolean optiqueCasserGChecked = optiqueCasserGCheckbox.isChecked();
                boolean optiqueCasserDChecked = optiqueCasserDCheckbox.isChecked();
                boolean plaqueImmatriculationArrChecked = plaqueImmatriculationArrCheckbox.isChecked();
                Intent intent = new Intent(PageThreeActivity.this, PageFourActivity.class);
                Stash.put("veilleuseGChecked", veilleuseGChecked);
                Stash.put("veilleuseDChecked", veilleuseDChecked);
                Stash.put("eclairagePlaqueGChecked", eclairagePlaqueGChecked);
                Stash.put("eclairagePlaqueDChecked", eclairagePlaqueDChecked);
                Stash.put("stopGChecked", stopGChecked);
                Stash.put("stopDChecked", stopDChecked);
                Stash.put("troisiemeStopChecked", troisiemeStopChecked);
                Stash.put("clignotantGChecked", clignotantGChecked);
                Stash.put("clignotantDChecked", clignotantDChecked);
                Stash.put("marcheArriereGChecked", marcheArriereGChecked);
                Stash.put("marcheArriereDChecked", marcheArriereDChecked);
                Stash.put("antiBrouillardChecked", antiBrouillardChecked);
                Stash.put("optiqueCasserGChecked", optiqueCasserGChecked);
                Stash.put("optiqueCasserDChecked", optiqueCasserDChecked);
                Stash.put("plaqueImmatriculationArrChecked", plaqueImmatriculationArrChecked);
                startActivity(intent);
            }
        });
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
