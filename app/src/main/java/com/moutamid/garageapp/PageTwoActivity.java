package com.moutamid.garageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.garageapp.helper.Stash;


public class PageTwoActivity extends AppCompatActivity {

    private CheckBox veilleuseAvgCheckbox;
    private CheckBox veilleuseAvdCheckbox;
    private CheckBox croisementAvgCheckbox;
    private CheckBox croisementAvdCheckbox;
    private CheckBox pleinPhareAvgCheckbox;
    private CheckBox pleinPhareAvdCheckbox;
    private CheckBox antiBrouillardAvgCheckbox;
    private CheckBox antiBrouillardAvdCheckbox;
    private CheckBox clignotantAvgCheckbox;
    private CheckBox clignotantAvdCheckbox;
    private CheckBox optiqueCasserGCheckbox;
    private CheckBox optiqueCasserDCheckbox;
    private CheckBox optiqueTerneGCheckbox;
    private CheckBox optiqueTerneDCheckbox;
    private CheckBox plaqueImmatriculationAvCheckbox;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_two);

        // Initialize checkboxes
        veilleuseAvgCheckbox = findViewById(R.id.veilleuse_avg);
        veilleuseAvdCheckbox = findViewById(R.id.veilleuse_avd);
        croisementAvgCheckbox = findViewById(R.id.croisement_avg);
        croisementAvdCheckbox = findViewById(R.id.croisement_avd);
        pleinPhareAvgCheckbox = findViewById(R.id.plein_phare_avg);
        pleinPhareAvdCheckbox = findViewById(R.id.plein_phare_avd);
        antiBrouillardAvgCheckbox = findViewById(R.id.anti_brouillard_avg);
        antiBrouillardAvdCheckbox = findViewById(R.id.anti_brouillard_avd);
        clignotantAvgCheckbox = findViewById(R.id.clignotant_avg);
        clignotantAvdCheckbox = findViewById(R.id.clignotant_avd);
        optiqueCasserGCheckbox = findViewById(R.id.optique_casser_g);
        optiqueCasserDCheckbox = findViewById(R.id.optique_casser_d);
        optiqueTerneGCheckbox = findViewById(R.id.optique_terne_g);
        optiqueTerneDCheckbox = findViewById(R.id.optique_terne_d);
        plaqueImmatriculationAvCheckbox = findViewById(R.id.plaque_immatriculation_av);

        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect checkbox states
                boolean veilleuseAvgChecked = veilleuseAvgCheckbox.isChecked();
                boolean veilleuseAvdChecked = veilleuseAvdCheckbox.isChecked();
                boolean croisementAvgChecked = croisementAvgCheckbox.isChecked();
                boolean croisementAvdChecked = croisementAvdCheckbox.isChecked();
                boolean pleinPhareAvgChecked = pleinPhareAvgCheckbox.isChecked();
                boolean pleinPhareAvdChecked = pleinPhareAvdCheckbox.isChecked();
                boolean antiBrouillardAvgChecked = antiBrouillardAvgCheckbox.isChecked();
                boolean antiBrouillardAvdChecked = antiBrouillardAvdCheckbox.isChecked();
                boolean clignotantAvgChecked = clignotantAvgCheckbox.isChecked();
                boolean clignotantAvdChecked = clignotantAvdCheckbox.isChecked();
                boolean optiqueCasserGChecked = optiqueCasserGCheckbox.isChecked();
                boolean optiqueCasserDChecked = optiqueCasserDCheckbox.isChecked();
                boolean optiqueTerneGChecked = optiqueTerneGCheckbox.isChecked();
                boolean optiqueTerneDChecked = optiqueTerneDCheckbox.isChecked();
                boolean plaqueImmatriculationAvChecked = plaqueImmatriculationAvCheckbox.isChecked();
                Intent intent = new Intent(PageTwoActivity.this, PageThreeActivity.class);
                Stash.put("veilleuseAvgChecked", veilleuseAvgChecked);
                Stash.put("veilleuseAvdChecked", veilleuseAvdChecked);
                Stash.put("croisementAvgChecked", croisementAvgChecked);
                Stash.put("croisementAvdChecked", croisementAvdChecked);
                Stash.put("pleinPhareAvgChecked", pleinPhareAvgChecked);
                Stash.put("pleinPhareAvdChecked", pleinPhareAvdChecked);
                Stash.put("antiBrouillardAvgChecked", antiBrouillardAvgChecked);
                Stash.put("antiBrouillardAvdChecked", antiBrouillardAvdChecked);
                Stash.put("clignotantAvgChecked", clignotantAvgChecked);
                Stash.put("clignotantAvdChecked", clignotantAvdChecked);
                Stash.put("optiqueCasserGChecked", optiqueCasserGChecked);
                Stash.put("optiqueCasserDChecked", optiqueCasserDChecked);
                Stash.put("optiqueTerneGChecked", optiqueTerneGChecked);
                Stash.put("optiqueTerneDChecked", optiqueTerneDChecked);
                Stash.put("plaqueImmatriculationAvChecked", plaqueImmatriculationAvChecked);
                startActivity(intent);
            }
        });
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
