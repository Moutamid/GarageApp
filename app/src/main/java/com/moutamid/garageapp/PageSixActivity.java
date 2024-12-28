package com.moutamid.garageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.moutamid.garageapp.helper.Stash;

public class PageSixActivity extends AppCompatActivity {

    private CheckBox bieletteDirectionGCheckBox, rotuleDirectionGCheckBox, rotuleTriangleSuspensionGCheckBox,
            amortisseurGCheckBox, ressortGCheckBox, roulementAVGCheckBox, souffletCardanAVGCheckBox,
            bieletteDirectionDCheckBox, rotuleDirectionDCheckBox, rotuleTriangleSuspensionDCheckBox,
            amortisseurDCheckBox, ressortDCheckBox, souffletCardanAVDCheckBox, roulementAVDCheckBox,
            roulementARGCheckBox, roulementARDCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_six);

        bieletteDirectionGCheckBox = findViewById(R.id.bielette_direction_g);
        rotuleDirectionGCheckBox = findViewById(R.id.rotule_direction_g);
        rotuleTriangleSuspensionGCheckBox = findViewById(R.id.rotule_suspension_g);
        amortisseurGCheckBox = findViewById(R.id.amortisseur_g);
        ressortGCheckBox = findViewById(R.id.ressort_g);
        roulementAVGCheckBox = findViewById(R.id.roulement_avg);
        souffletCardanAVGCheckBox = findViewById(R.id.soufflet_cardan_avg);

        bieletteDirectionDCheckBox = findViewById(R.id.bielette_direction_d);
        rotuleDirectionDCheckBox = findViewById(R.id.rotule_direction_d);
        rotuleTriangleSuspensionDCheckBox = findViewById(R.id.rotule_suspension_d);
        amortisseurDCheckBox = findViewById(R.id.amortisseur_d);
        ressortDCheckBox = findViewById(R.id.ressort_d);
        souffletCardanAVDCheckBox = findViewById(R.id.soufflet_cardan_avd);
        roulementAVDCheckBox = findViewById(R.id.roulement_avd);
        roulementARGCheckBox = findViewById(R.id.roulement_arg);
        roulementARDCheckBox = findViewById(R.id.roulement_ard);

        Button nextButton = findViewById(R.id.next_button);
        Button backButton = findViewById(R.id.back_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save data to Stash
                saveDataToStash();

                startActivity(new Intent(PageSixActivity.this, PageSevenActivity.class));
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

    private void saveDataToStash() {
        Stash.put("bieletteDirectionGChecked", bieletteDirectionGCheckBox.isChecked());
        Stash.put("rotuleDirectionGChecked", rotuleDirectionGCheckBox.isChecked());
        Stash.put("rotuleTriangleSuspensionGChecked", rotuleTriangleSuspensionGCheckBox.isChecked());
        Stash.put("amortisseurGChecked", amortisseurGCheckBox.isChecked());
        Stash.put("ressortGChecked", ressortGCheckBox.isChecked());
        Stash.put("roulementAVGChecked", roulementAVGCheckBox.isChecked());
        Stash.put("souffletCardanAVGChecked", souffletCardanAVGCheckBox.isChecked());

        Stash.put("bieletteDirectionDChecked", bieletteDirectionDCheckBox.isChecked());
        Stash.put("rotuleDirectionDChecked", rotuleDirectionDCheckBox.isChecked());
        Stash.put("rotuleTriangleSuspensionDChecked", rotuleTriangleSuspensionDCheckBox.isChecked());
        Stash.put("amortisseurDChecked", amortisseurDCheckBox.isChecked());
        Stash.put("ressortDChecked", ressortDCheckBox.isChecked());
        Stash.put("souffletCardanAVDChecked", souffletCardanAVDCheckBox.isChecked());
        Stash.put("roulementAVDChecked", roulementAVDCheckBox.isChecked());
        Stash.put("roulementARGChecked", roulementARGCheckBox.isChecked());
        Stash.put("roulementARDChecked", roulementARDCheckBox.isChecked());
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
