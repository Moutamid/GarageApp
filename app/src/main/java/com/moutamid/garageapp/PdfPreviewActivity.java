package com.moutamid.garageapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.garageapp.adapter.PreviewImageAdapter;
import com.moutamid.garageapp.helper.FileUtil;
import com.moutamid.garageapp.helper.Stash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PdfPreviewActivity extends AppCompatActivity {
    int marginTop;
    private CheckBox echappementCheckBox, fuiteHuileCheckBox, fuiteLiquideCheckBox;
    private ArrayList<Uri> imageUriList;
    private RecyclerView previewRecyclerView;
    private PreviewImageAdapter previewImageAdapter;
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
    private CheckBox optiqueCasserGCheckbox_page3;
    private CheckBox optiqueCasserDCheckbox_page3;
    private CheckBox plaqueImmatriculationArrCheckbox;
    TextView immatriculation, date_ct;
    private SeekBar summaryDisquesAvantSeekBar, summaryPlaquettesAvantSeekBar, summaryDisquesArriereSeekBar, summaryPlaquettesArriereSeekBar;
    private CheckBox summaryFlexibleGCheckBox, summaryFlexibleDCheckBox,
            summaryFlexibleArriereGCheckBox, summaryFlexibleArriereDCheckBox;
    private RadioGroup summaryDisquesArriereRadioGroup;
    private RadioButton summaryDisquesArriereOuiRadioButton, summaryDisquesArriereNonRadioButton;
    EditText pneu_av_taille, pneu_arr_taille;
    SeekBar usure_avant, usure_arriere;
    private CheckBox bieletteDirectionGCheckBox, rotuleDirectionGCheckBox, rotuleTriangleSuspensionGCheckBox,
            amortisseurGCheckBox, ressortGCheckBox, roulementAVGCheckBox, souffletCardanAVGCheckBox,
            bieletteDirectionDCheckBox, rotuleDirectionDCheckBox, rotuleTriangleSuspensionDCheckBox,
            amortisseurDCheckBox, ressortDCheckBox, souffletCardanAVDCheckBox, roulementAVDCheckBox,
            roulementARGCheckBox, roulementARDCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_preview);

        // Initialize checkboxes and TextViews
        echappementCheckBox = findViewById(R.id.echappement);
        fuiteHuileCheckBox = findViewById(R.id.fuite_huile);
        fuiteLiquideCheckBox = findViewById(R.id.fuite_liquide);
        pneu_av_taille = findViewById(R.id.pneu_av_taille);
        pneu_arr_taille = findViewById(R.id.pneu_arr_taille);
        usure_avant = findViewById(R.id.usure_avant);
        usure_arriere = findViewById(R.id.usure_arriere);

        veilleuseAvgCheckbox = findViewById(R.id.veilleuse_avg);
        immatriculation = findViewById(R.id.immatriculation);
        date_ct = findViewById(R.id.date_ct);
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
        optiqueCasserGCheckbox_page3 = findViewById(R.id.optique_casser_g_page3);
        optiqueCasserDCheckbox_page3 = findViewById(R.id.optique_casser_d_page3);
        plaqueImmatriculationArrCheckbox = findViewById(R.id.plaque_immatriculation_arr);
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

        previewRecyclerView = findViewById(R.id.preview_recycler_view);
        imageUriList = new ArrayList<>();

        loadImageUriList();
        restoreCheckboxStates();
        if (imageUriList != null) {
            previewImageAdapter = new PreviewImageAdapter(imageUriList);
            previewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            previewRecyclerView.setAdapter(previewImageAdapter);
        }
        Button generatePdfButton = findViewById(R.id.generate_pdf_button);
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();
            }
        });
        summaryDisquesAvantSeekBar = findViewById(R.id.summary_disques_avant);
        summaryPlaquettesAvantSeekBar = findViewById(R.id.summary_plaquettes_avant);
        summaryFlexibleGCheckBox = findViewById(R.id.summary_flexible_g);
        summaryFlexibleDCheckBox = findViewById(R.id.summary_flexible_d);
        summaryDisquesArriereRadioGroup = findViewById(R.id.summary_disques_arriere_radio_group);
        summaryDisquesArriereOuiRadioButton = findViewById(R.id.summary_disques_arriere_oui);
        summaryDisquesArriereNonRadioButton = findViewById(R.id.summary_disques_arriere_non);
        summaryDisquesArriereSeekBar = findViewById(R.id.summary_disques_arriere);
        summaryPlaquettesArriereSeekBar = findViewById(R.id.summary_plaquettes_arriere);
        summaryFlexibleArriereGCheckBox = findViewById(R.id.summary_flexible_arriere_g);
        summaryFlexibleArriereDCheckBox = findViewById(R.id.summary_flexible_arriere_d);

        // Load stored values using Stash
        setStoredValues();

    }

    private void setStoredValues() {

        echappementCheckBox.setChecked(Stash.getBoolean("echappementChecked"));
        fuiteHuileCheckBox.setChecked(Stash.getBoolean("fuiteHuileChecked"));
        fuiteLiquideCheckBox.setChecked(Stash.getBoolean("fuiteLiquideChecked"));

        pneu_av_taille.setText(Stash.getString("pneuAvantTaille"));
        pneu_arr_taille.setText(Stash.getString("pneuArriereTaille"));
        usure_avant.setProgress(Stash.getInt("pneuAvantUsure", 0));
        usure_arriere.setProgress(Stash.getInt("pneuArriereUsure", 0));

        int disquesAvant = Stash.getInt("disquesAvantValue", 0);
        int plaquettesAvant = Stash.getInt("plaquettesAvantValue", 0);
        boolean flexibleG = Stash.getBoolean("flexibleGChecked", false);
        boolean flexibleD = Stash.getBoolean("flexibleDChecked", false);
        boolean disquesArriereExists = Stash.getBoolean("disquesArriereOuiSelected", false);
        int disquesArriere = Stash.getInt("disquesArriereValue", 0);
        int plaquettesArriere = Stash.getInt("plaquettesArriereValue", 0);
        boolean flexibleArriereG = Stash.getBoolean("flexibleArriereGChecked", false);
        boolean flexibleArriereD = Stash.getBoolean("flexibleArriereDChecked", false);
        summaryDisquesAvantSeekBar.setProgress(disquesAvant);
        summaryPlaquettesAvantSeekBar.setProgress(plaquettesAvant);
        summaryFlexibleGCheckBox.setChecked(flexibleG);
        summaryFlexibleDCheckBox.setChecked(flexibleD);

        if (disquesArriereExists) {
            summaryDisquesArriereOuiRadioButton.setChecked(true);
        } else {
            summaryDisquesArriereNonRadioButton.setChecked(true);
        }
        summaryDisquesArriereSeekBar.setProgress(disquesArriere);
        summaryPlaquettesArriereSeekBar.setProgress(plaquettesArriere);
        summaryFlexibleArriereGCheckBox.setChecked(flexibleArriereG);
        summaryFlexibleArriereDCheckBox.setChecked(flexibleArriereD);
    }

    private void restoreCheckboxStates() {
        immatriculation.setText(Stash.getString("registrationNumber"));
        date_ct.setText(Stash.getString("technicalInspectionDate"));
        veilleuseAvgCheckbox.setChecked(Stash.getBoolean("veilleuseAvgChecked"));
        veilleuseAvdCheckbox.setChecked(Stash.getBoolean("veilleuseAvdChecked"));
        croisementAvgCheckbox.setChecked(Stash.getBoolean("croisementAvgChecked"));
        croisementAvdCheckbox.setChecked(Stash.getBoolean("croisementAvdChecked"));
        pleinPhareAvgCheckbox.setChecked(Stash.getBoolean("pleinPhareAvgChecked"));
        pleinPhareAvdCheckbox.setChecked(Stash.getBoolean("pleinPhareAvdChecked"));
        antiBrouillardAvgCheckbox.setChecked(Stash.getBoolean("antiBrouillardAvgChecked"));
        antiBrouillardAvdCheckbox.setChecked(Stash.getBoolean("antiBrouillardAvdChecked"));
        clignotantAvgCheckbox.setChecked(Stash.getBoolean("clignotantAvgChecked"));
        clignotantAvdCheckbox.setChecked(Stash.getBoolean("clignotantAvdChecked"));
        optiqueCasserGCheckbox.setChecked(Stash.getBoolean("optiqueCasserGChecked"));
        optiqueCasserDCheckbox.setChecked(Stash.getBoolean("optiqueCasserDChecked"));
        optiqueTerneGCheckbox.setChecked(Stash.getBoolean("optiqueTerneGChecked"));
        optiqueTerneDCheckbox.setChecked(Stash.getBoolean("optiqueTerneDChecked"));
        plaqueImmatriculationAvCheckbox.setChecked(Stash.getBoolean("plaqueImmatriculationAvChecked"));
        veilleuseGCheckbox.setChecked(Stash.getBoolean("veilleuseGChecked"));
        veilleuseDCheckbox.setChecked(Stash.getBoolean("veilleuseDChecked"));
        eclairagePlaqueGCheckbox.setChecked(Stash.getBoolean("eclairagePlaqueGChecked"));
        eclairagePlaqueDCheckbox.setChecked(Stash.getBoolean("eclairagePlaqueDChecked"));
        stopGCheckbox.setChecked(Stash.getBoolean("stopGChecked"));
        stopDCheckbox.setChecked(Stash.getBoolean("stopDChecked"));
        troisiemeStopCheckbox.setChecked(Stash.getBoolean("troisiemeStopChecked"));
        clignotantGCheckbox.setChecked(Stash.getBoolean("clignotantGChecked"));
        clignotantDCheckbox.setChecked(Stash.getBoolean("clignotantDChecked"));
        marcheArriereGCheckbox.setChecked(Stash.getBoolean("marcheArriereGChecked"));
        marcheArriereDCheckbox.setChecked(Stash.getBoolean("marcheArriereDChecked"));
        antiBrouillardCheckbox.setChecked(Stash.getBoolean("antiBrouillardChecked"));
        optiqueCasserGCheckbox_page3.setChecked(Stash.getBoolean("optiqueCasserGChecked"));
        optiqueCasserDCheckbox_page3.setChecked(Stash.getBoolean("optiqueCasserDChecked"));
        plaqueImmatriculationArrCheckbox.setChecked(Stash.getBoolean("plaqueImmatriculationArrChecked"));
        bieletteDirectionGCheckBox.setChecked(Stash.getBoolean("bieletteDirectionGChecked", false));
        rotuleDirectionGCheckBox.setChecked(Stash.getBoolean("rotuleDirectionGChecked", false));
        rotuleTriangleSuspensionGCheckBox.setChecked(Stash.getBoolean("rotuleTriangleSuspensionGChecked", false));
        amortisseurGCheckBox.setChecked(Stash.getBoolean("amortisseurGChecked", false));
        ressortGCheckBox.setChecked(Stash.getBoolean("ressortGChecked", false));
        roulementAVGCheckBox.setChecked(Stash.getBoolean("roulementAVGChecked", false));
        souffletCardanAVGCheckBox.setChecked(Stash.getBoolean("souffletCardanAVGChecked", false));

        bieletteDirectionDCheckBox.setChecked(Stash.getBoolean("bieletteDirectionDChecked", false));
        rotuleDirectionDCheckBox.setChecked(Stash.getBoolean("rotuleDirectionDChecked", false));
        rotuleTriangleSuspensionDCheckBox.setChecked(Stash.getBoolean("rotuleTriangleSuspensionDChecked", false));
        amortisseurDCheckBox.setChecked(Stash.getBoolean("amortisseurDChecked", false));
        ressortDCheckBox.setChecked(Stash.getBoolean("ressortDChecked", false));
        souffletCardanAVDCheckBox.setChecked(Stash.getBoolean("souffletCardanAVDChecked", false));
        roulementAVDCheckBox.setChecked(Stash.getBoolean("roulementAVDChecked", false));
        roulementARGCheckBox.setChecked(Stash.getBoolean("roulementARGChecked", false));
        roulementARDCheckBox.setChecked(Stash.getBoolean("roulementARDChecked", false));
    }

    // Method to generate PDF
    private void generatePdf() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 862, 1).create(); // A4 size: 595 x 842 points

        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Draw title
        paint.setColor(getResources().getColor(R.color.appColor));
        paint.setTextSize(27);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);

        canvas.drawText("Final Report", pageInfo.getPageWidth() / 2, 60, paint);

        // Draw immatriculation and date
        paint.setTextSize(13);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(true);

        int y = 100;
        canvas.drawText("Immatriculation: " + Stash.getString("registrationNumber") + "                                                      " + "Date CT: " + Stash.getString("technicalInspectionDate"), 50, y, paint);
        y += 25;


        paint.setTextSize(16);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(true);
        canvas.drawText("ECLAIRAGE AVANT", (pageInfo.getPageWidth() / 2) - 60, y, paint);
        y += 30;

        // Draw checkbox states

        y = drawCheckboxState(canvas, veilleuseAvgCheckbox, veilleuseAvdCheckbox, croisementAvgCheckbox,
                "Veilleuse AVG", "Veilleuse AVD", "Croisement AVG", y);
        y = drawCheckboxState(canvas, optiqueCasserDCheckbox, croisementAvdCheckbox, pleinPhareAvgCheckbox,
                "Optique D", "Croisement AVD", "Plein Phare AVG", y);
        y = drawCheckboxState(canvas, optiqueTerneGCheckbox, pleinPhareAvdCheckbox, antiBrouillardAvgCheckbox,
                "Optique Terne G", "Plein Phare AVD", "Anti-Brouillard AVG", y);
        y = drawCheckboxState(canvas, optiqueTerneDCheckbox, antiBrouillardAvdCheckbox, clignotantAvgCheckbox,
                "Optique Terne D", "Anti-Brouillard AVD", "Clignotant AVG", y);
//        y = drawCheckboxState(canvas, clignotantAvdCheckbox, optiqueCasserGCheckbox,
//                "                          Clignotant AVD", "Optique G", y);
        canvas.drawText("ECLAIRAGE ARRIERE", (pageInfo.getPageWidth() / 2) - 60, y + 20, paint);

        y = drawMultipleCheckboxStates(canvas, 120);
        if (plaqueImmatriculationArrCheckbox.isChecked()) {
            y = drawText(canvas, "Plaque Immatriculation ARR :  " + "X", 50, y, paint);
        } else {
            y = drawText(canvas, "Plaque Immatriculation ARR :  " + "\u2713", 50, y, paint);
        }

        canvas.drawText("PNEUMATIQUE", (pageInfo.getPageWidth() / 2) - 60, y + 5, paint);

        y = drawText(canvas, "Pneu av taille: " + Stash.getString("pneuAvantTaille") + "                                                                  " +
                "Usure avant (%): " + Stash.getInt("pneuAvantUsure", 0), 50, y + 25, paint);
        y = drawText(canvas, "Pneu arr taille: " + Stash.getString("pneuArriereTaille") + "                                                                  " +
                "Usure arrière (%): " + Stash.getInt("pneuArriereUsure", 0), 50, y, paint);
        canvas.drawText("SOUS CAISSE", (pageInfo.getPageWidth() / 2) - 60, y + 5, paint);

        y = drawText(canvas, "Échappement: " + (Stash.getBoolean("echappementChecked", false) ? "X" : "\u2713") + "                                                                  " +
                "Fuite huile: " + (Stash.getBoolean("fuiteHuileChecked", false) ? "X" : "\u2713"), 50, y + 25, paint);
        y = drawText(canvas, "Fuite liquide de refroidissement: " + (Stash.getBoolean("fuiteLiquideChecked", false) ? "X" : "\u2713"), 50, y, paint);
        canvas.drawText("FREINAGE", (pageInfo.getPageWidth() / 2) - 60, y + 5, paint);

        y = drawText(canvas, "Disques Avant: " + Stash.getInt("disquesAvantValue", 0) + "                   " +
                "Plaquettes Avant: " + Stash.getInt("plaquettesAvantValue", 0) + "                  " +
                "Flexible G: " + (Stash.getBoolean("flexibleGChecked", false) ? "X" : "\u2713"), 50, y + 35, paint);
        y = drawText(canvas, "Flexible D: " + (Stash.getBoolean("flexibleDChecked", false) ? "X" : "\u2713") + "                          " +
                "Disques Arriere: " + (Stash.getBoolean("disquesArriereOuiSelected", false) ? Stash.getInt("disquesArriereValue", 0) : "X") + "                     " +
                "Plaquettes Arriere: " + Stash.getInt("plaquettesArriereValue", 0), 50, y, paint);
        y = drawText(canvas, "Flexible Arriere G: " + (Stash.getBoolean("flexibleArriereGChecked", false) ? "X" : "\u2713") + "                                                                 " +
                "Flexible Arriere D: " + (Stash.getBoolean("flexibleArriereDChecked", false) ? "X" : "\u2713"), 50, y, paint);

        canvas.drawText("DIRECTION / SUSPENSION / TRANSMISSION", (pageInfo.getPageWidth() / 2) - 130, y + 25, paint);
        paint.setTextSize(13);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(false);

        y = drawThreeTexts(canvas,
                "Bielette Direction G: " + (Stash.getBoolean("bieletteDirectionGChecked", false) ? "X" : "\u2713"),
                "Rotule Direction G: " + (Stash.getBoolean("rotuleDirectionGChecked", false) ? "X" : "\u2713"),
                "Amortisseur G: " + (Stash.getBoolean("amortisseurGChecked", false) ? "X" : "\u2713"),
                50, y + 65, paint);

        y = drawThreeTexts(canvas,
                "Ressort G: " + (Stash.getBoolean("ressortGChecked", false) ? "X" : "\u2713"),
                "Roulement AVG: " + (Stash.getBoolean("roulementAVGChecked", false) ? "X" : "\u2713"),
                "Soufflet Cardan AVG: " + (Stash.getBoolean("souffletCardanAVGChecked", false) ? "X" : "\u2713"),
                50, y, paint);

        y = drawThreeTexts(canvas,
                "Bielette Direction D: " + (Stash.getBoolean("bieletteDirectionDChecked", false) ? "X" : "\u2713"),
                "Rotule Direction D: " + (Stash.getBoolean("rotuleDirectionDChecked", false) ? "X" : "\u2713"),
                "Rotule Triangle Suspension D: " + (Stash.getBoolean("rotuleTriangleSuspensionDChecked", false) ? "X" : "\u2713"),
                50, y, paint);

        y = drawThreeTexts(canvas,
                "Amortisseur D: " + (Stash.getBoolean("amortisseurDChecked", false) ? "X" : "\u2713"),
                "Ressort D: " + (Stash.getBoolean("ressortDChecked", false) ? "X" : "\u2713"),
                "Soufflet Cardan AVD: " + (Stash.getBoolean("souffletCardanAVDChecked", false) ? "X" : "\u2713"),
                50, y, paint);

        y = drawThreeTexts(canvas,
                "Roulement AVD: " + (Stash.getBoolean("roulementAVDChecked", false) ? "X" : "\u2713"),
                "Roulement ARG: " + (Stash.getBoolean("roulementARGChecked", false) ? "X" : "\u2713"),
                "Roulement ARD: " + (Stash.getBoolean("roulementARDChecked", false) ? "X" : "\u2713"),
                50, y, paint);


        Log.d("gat", y + "");
        y = 60;
        int x = 50;
        int column = 0;
        Log.d("asdas", ((pageInfo.getPageWidth() / 2) + 90) + " margin");
        document.finishPage(page);
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint.setTextSize(16);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setFakeBoldText(true);

        canvas.drawText("Images", (pageInfo.getPageWidth() / 2) - 60, y + 35, paint);

// Draw images if available
        if (imageUriList != null && !imageUriList.isEmpty()) {
            y += 20;
            int maxImageHeight = 320;
            int columnWidth = pageInfo.getPageWidth() / 2 - 50;

            for (Uri uri : imageUriList) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Bitmap scaledBitmap = scaleBitmapToHeight(bitmap, maxImageHeight);

                    int marginBottom = 50;
                    if (y + scaledBitmap.getHeight() + marginBottom > pageInfo.getPageHeight()) {
                        document.finishPage(page);
                        page = document.startPage(pageInfo);
                        canvas = page.getCanvas();
                        y = 50;
                        column = 0;
                        x = 50;
                    }

                    canvas.drawBitmap(scaledBitmap, x, y, paint);

                    if (column == 0) {
                        x = columnWidth + 50;
                        column = 1;
                    } else {
                        x = 50;
                        y += scaledBitmap.getHeight() + 20;
                        column = 0;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        document.finishPage(page);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action")
                .setItems(new String[]{"Open File", "Share File"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openFile(document);
                                break;
                            case 1:
                                shareFile(document);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private int drawText(Canvas canvas, String text, float x, float y, Paint paint) {
        paint = new Paint();
        paint.setTextSize(13);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(false);

        float currentX = x;
        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (currentChar == 'X' || currentChar == '\u2713') {
                // First draw any accumulated text in black
                if (currentText.length() > 0) {
                    paint.setColor(Color.BLACK);
                    canvas.drawText(currentText.toString(), currentX, y, paint);
                    currentX += paint.measureText(currentText.toString());
                    currentText.setLength(0);
                }

                // Draw the symbol with appropriate color and swapped symbol
                if (currentChar == 'X') {
                    paint.setColor(Color.RED);
                    canvas.drawText("X", currentX, y, paint);
                } else { // '\u2713'
                    paint.setColor(Color.GREEN);
                    canvas.drawText("\u2713", currentX, y, paint);
                }
                currentX += paint.measureText("X"); // Use consistent width for both symbols
            } else {
                currentText.append(currentChar);
            }
        }

        if (currentText.length() > 0) {

            paint.setColor(Color.BLACK);

            canvas.drawText(currentText.toString(), currentX, y, paint);

        }
        return (int) (y + 20);
    }

    private int drawThreeTexts(Canvas canvas, String text1, String text2, String text3, float x, float y, Paint paint) {
        float columnSpacing = 150;

        drawTextWithColoredSymbols(canvas, text1, x, y, paint);
        drawTextWithColoredSymbols(canvas, text2, x + columnSpacing, y, paint);
        drawTextWithColoredSymbols(canvas, text3, x + 2 * columnSpacing, y, paint);

        return (int) (y + paint.getTextSize() + 5);
    }

    private void drawTextWithColoredSymbols(Canvas canvas, String text, float x, float y, Paint paint) {
        String[] parts = text.split("(?=X|\\u2713)");
        float currentX = x;
        for (String part : parts) {
            if (part.startsWith("X")) {
                paint.setColor(Color.GREEN);
                canvas.drawText("\u2713" + part.substring(1), currentX, y, paint);
            } else if (part.startsWith("\u2713")) {
                paint.setColor(Color.RED);
                canvas.drawText("X" + part.substring(1), currentX, y, paint);
            } else {
                paint.setColor(Color.BLACK);
                canvas.drawText(part, currentX, y, paint);
            }
            currentX += paint.measureText(part);
        }
    }

    private int drawCheckboxState(Canvas canvas, CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3,
                                  String label1, String label2, String label3, int y) {
        Paint paint = new Paint();
        paint.setTextSize(13);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(false);
        float columnSpacing = 180;

        // Helper function to draw a single checkbox state
        Consumer<Integer> drawColumn = (column) -> {
            String label = column == 0 ? label1 : (column == 1 ? label2 : label3);
            CheckBox checkbox = column == 0 ? checkBox1 : (column == 1 ? checkBox2 : checkBox3);

            // Skip if checkbox or label is null or empty
            if (checkbox == null || label.isEmpty()) {
                return;
            }

            float xPos = 50 + (column * columnSpacing);

            // Draw label in black
            paint.setColor(Color.BLACK);
            String labelText = label + " : ";
            canvas.drawText(labelText, xPos, y, paint);

            // Draw symbol with color and swapped position
            float symbolX = xPos + paint.measureText(labelText);
            if (checkbox.isChecked()) {
                paint.setColor(Color.RED);
                canvas.drawText("X", symbolX, y, paint); // Draw X in red when checked
            } else {
                paint.setColor(Color.GREEN);
                canvas.drawText("\u2713", symbolX, y, paint); // Draw checkmark in green when unchecked
            }
        };

        // Draw each column
        drawColumn.accept(0);
        drawColumn.accept(1);
        if (!label3.isEmpty() && checkBox3 != null) {
            drawColumn.accept(2);
        }

        return y + 20;
    }

    private void shareFile(PdfDocument document) {
        File file = FileUtil.getFileWithTimestamp();
        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        Set<String> uriSet = sharedPreferences.getStringSet("imageUris", new HashSet<>());
        uriSet.clear();
        Uri pdfUri = FileProvider.getUriForFile(this, "com.moutamid.garageapp.fileprovider", file);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share PDF using"));
    }

    private void openFile(PdfDocument document) {
        File file = FileUtil.getFileWithTimestamp();
        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();

        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        Set<String> uriSet = sharedPreferences.getStringSet("imageUris", new HashSet<>());
        uriSet.clear();

        Uri pdfUri = FileProvider.getUriForFile(this, "com.moutamid.garageapp.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private Bitmap scaleBitmapToHeight(Bitmap bitmap, int maxHeight) {
        float aspectRatio = (float) bitmap.getWidth() / bitmap.getHeight();
        int width = (int) (maxHeight * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, maxHeight, true);
    }

    private void loadImageUriList() {
        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        Set<String> uriSet = sharedPreferences.getStringSet("imageUris", new HashSet<>());
        for (String uriString : uriSet) {
            imageUriList.add(Uri.parse(uriString));
        }
    }

    public void home(View view) {
        startActivity(new Intent(this, PageOneActivity.class));
        finishAffinity();
    }

    private int drawMultipleCheckboxStates(Canvas canvas, int y) {
        // Define the checkboxes and labels as arrays for iteration
        CheckBox[][] checkboxes = {
                {veilleuseGCheckbox, veilleuseDCheckbox},
                {eclairagePlaqueGCheckbox, eclairagePlaqueDCheckbox},
                {stopGCheckbox, stopDCheckbox},
                {troisiemeStopCheckbox, clignotantGCheckbox},
                {clignotantDCheckbox, marcheArriereGCheckbox},
                {marcheArriereDCheckbox, antiBrouillardCheckbox},
                {optiqueCasserGCheckbox_page3, optiqueCasserDCheckbox_page3}
        };

        String[][] labels = {
                {"Veilleuse G", "Veilleuse D"},
                {"Eclairage Plaque G", "Eclairage Plaque D"},
                {"Stop G", "Stop D"},
                {"Troisieme Stop", "Clignotant G"},
                {"Clignotant D", "Marche Arriere G"},
                {"Marche Arriere D", "Anti-Brouillard"},
                {"Optique G", "Optique D"}
        };

        // Iterate over the checkboxes and labels arrays to draw each state
        for (int i = 0; i < checkboxes.length; i++) {
            y = drawCheckboxState1(canvas,
                    checkboxes[i][0], checkboxes[i][1],
                    labels[i][0], labels[i][1],
                    y + (i == 0 ? 155 : 0)); // Add spacing only after the first element
        }

        return y;
    }

    private int drawCheckboxState1(Canvas canvas, CheckBox checkBox1, CheckBox checkBox2,
                                  String label1, String label2, int y) {
        Paint paint = new Paint();
        paint.setTextSize(13);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(false);
        float columnSpacing = 180;

        // Helper function to draw a single checkbox state
        BiConsumer<CheckBox, String> drawColumn = (checkbox, label) -> {
            if (checkbox == null || label.isEmpty()) {
                return; // Skip if checkbox or label is null/empty
            }

            float xPos = 50 + (checkbox == checkBox2 ? columnSpacing : 0); // Adjust position for the second column

            // Draw label in black
            paint.setColor(Color.BLACK);
            String labelText = label + " : ";
            canvas.drawText(labelText, xPos, y, paint);

            // Draw symbol with color and swapped position
            float symbolX = xPos + paint.measureText(labelText);
            if (checkbox.isChecked()) {
                paint.setColor(Color.RED);
                canvas.drawText("X", symbolX, y, paint); // Draw X

            } else {
                paint.setColor(Color.GREEN);
                canvas.drawText("\u2713", symbolX, y, paint); // Draw checkmark
                }
        };

        // Draw both columns
        drawColumn.accept(checkBox1, label1);
        drawColumn.accept(checkBox2, label2);

        return y + 20; // Return updated y position
    }
}