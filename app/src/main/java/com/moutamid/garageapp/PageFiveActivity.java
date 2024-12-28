package com.moutamid.garageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.garageapp.helper.Stash;


public class PageFiveActivity extends AppCompatActivity {

    private EditText pneuAvantTailleEditText, pneuArriereTailleEditText;
    private SeekBar pneuAvantUsureSeekBar, pneuArriereUsureSeekBar;
    TextView percentage1, percentage2;
    int roundedProgress1, roundedProgress2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_five);

        percentage1 = findViewById(R.id.percentage1);
        percentage2 = findViewById(R.id.percentage2);
        pneuAvantTailleEditText = findViewById(R.id.pneu_av_taille);
        pneuArriereTailleEditText = findViewById(R.id.pneu_arr_taille);
        pneuAvantUsureSeekBar = findViewById(R.id.usure_avant);
        pneuArriereUsureSeekBar = findViewById(R.id.usure_arriere);
        pneuAvantUsureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundedProgress1 = (progress / 10) * 10;
                seekBar.setProgress(roundedProgress1);
                percentage1.setText(roundedProgress1 + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        pneuArriereUsureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundedProgress2 = (progress / 10) * 10;
                seekBar.setProgress(roundedProgress2);
                percentage2.setText(roundedProgress2 + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        Button nextButton = findViewById(R.id.next_button);
        Button backButton = findViewById(R.id.back_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    saveDataToStash();
                    startActivity(new Intent(PageFiveActivity.this, PageSixActivity.class));
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void saveDataToStash()
    {
        Stash.put("pneuAvantTaille", pneuAvantTailleEditText.getText().toString().trim());
        Stash.put("pneuArriereTaille", pneuArriereTailleEditText.getText().toString().trim());
        Stash.put("pneuAvantUsure", roundedProgress1);
        Stash.put("pneuArriereUsure", roundedProgress2);
    }
    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
