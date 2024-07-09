package com.moutamid.garageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.garageapp.adapter.PreviewImageAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    private SeekBar summaryDisquesAvantSeekBar, summaryPlaquettesAvantSeekBar,
            summaryDisquesArriereSeekBar, summaryPlaquettesArriereSeekBar;
    private CheckBox summaryFlexibleGCheckBox, summaryFlexibleDCheckBox,
            summaryFlexibleArriereGCheckBox, summaryFlexibleArriereDCheckBox;
    private RadioGroup summaryDisquesArriereRadioGroup;
    private RadioButton summaryDisquesArriereOuiRadioButton, summaryDisquesArriereNonRadioButton;
    EditText pneu_av_taille, pneu_arr_taille;
    SeekBar usure_avant, usure_arriere;

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

        previewRecyclerView = findViewById(R.id.preview_recycler_view);
        imageUriList = new ArrayList<>();

        loadImageUriList();
        // Restore checkbox states from Stash
        restoreCheckboxStates();

        // Initialize RecyclerView if image URI list is not empty
        if (imageUriList != null) {
            previewImageAdapter = new PreviewImageAdapter(imageUriList);
            previewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            previewRecyclerView.setAdapter(previewImageAdapter);
        }

        // Button click listener to generate PDF
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
        // Set values to UI elements
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

    // Method to restore checkbox states from Stash
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
    }

    // Method to generate PDF
    private void generatePdf() {
        // Create a new PDF document
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(695, 842, 1).create(); // A4 size: 595 x 842 points

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
        paint.setTextSize(18);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(true);

        int y = 100;
        canvas.drawText("Immatriculation: " + Stash.getString("registrationNumber"), 50, y, paint);
        y += 30;
        canvas.drawText("Date CT: " + Stash.getString("technicalInspectionDate"), 50, y, paint);
        y += 50;

        paint.setTextSize(18);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(true);
        canvas.drawText("ECLAIRAGE AVANT", (pageInfo.getPageWidth() / 2) - 60, y, paint);
        y += 50;

        // Draw checkbox states
        y = drawCheckboxState(canvas, veilleuseAvgCheckbox, "Veilleuse AVG", y);
        y = drawCheckboxState(canvas, veilleuseAvdCheckbox, "Veilleuse AVD", y);
        y = drawCheckboxState(canvas, croisementAvgCheckbox, "Croisement AVG", y);
        y = drawCheckboxState(canvas, croisementAvdCheckbox, "Croisement AVD", y);
        y = drawCheckboxState(canvas, pleinPhareAvgCheckbox, "Plein Phare AVG", y);
        y = drawCheckboxState(canvas, pleinPhareAvdCheckbox, "Plein Phare AVD", y);
        y = drawCheckboxState(canvas, antiBrouillardAvgCheckbox, "Anti-Brouillard AVG", y);
        y = drawCheckboxState(canvas, antiBrouillardAvdCheckbox, "Anti-Brouillard AVD", y);
        y = drawCheckboxState(canvas, clignotantAvgCheckbox, "Clignotant AVG", y);
        y = drawCheckboxState(canvas, clignotantAvdCheckbox, "Clignotant AVD", y);
        y = drawCheckboxState(canvas, optiqueCasserGCheckbox, "Optique Casser G", y);
        y = drawCheckboxState(canvas, optiqueCasserDCheckbox, "Optique Casser D", y);
        y = drawCheckboxState(canvas, optiqueTerneGCheckbox, "Optique Terne G", y);
        y = drawCheckboxState(canvas, optiqueTerneDCheckbox, "Optique Terne D", y);
        y = drawCheckboxState(canvas, plaqueImmatriculationAvCheckbox, "Plaque Immatriculation AV", y);
        canvas.drawText("ECLAIRAGE ARRIERE", (pageInfo.getPageWidth() / 2) - 60, y + 25, paint);

        y = drawCheckboxState(canvas, veilleuseGCheckbox, "Veilleuse G", y + 50);
        y = drawCheckboxState(canvas, veilleuseDCheckbox, "Veilleuse D", y);
        y = drawCheckboxState(canvas, eclairagePlaqueGCheckbox, "Eclairage Plaque G", y);
        y = drawCheckboxState(canvas, eclairagePlaqueDCheckbox, "Eclairage Plaque D", y);
        y = drawCheckboxState(canvas, stopGCheckbox, "Stop G", y);
        y = drawCheckboxState(canvas, stopDCheckbox, "Stop D", y);
        y = drawCheckboxState(canvas, troisiemeStopCheckbox, "Troisieme Stop", y);
        y = drawCheckboxState(canvas, clignotantGCheckbox, "Clignotant G", y);
        y = drawCheckboxState(canvas, clignotantDCheckbox, "Clignotant D", y);
        y = drawCheckboxState(canvas, marcheArriereGCheckbox, "Marche Arriere G", y);
        y = drawCheckboxState(canvas, marcheArriereDCheckbox, "Marche Arriere D", y);
        y = drawCheckboxState(canvas, antiBrouillardCheckbox, "Anti-Brouillard", y);
        y = drawCheckboxState(canvas, optiqueCasserGCheckbox_page3, "Optique Casser G ", y);
        y = drawCheckboxState(canvas, optiqueCasserDCheckbox_page3, "Optique Casser D ", y);
        document.finishPage(page);
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        y = 50;
        int pageHeight = pageInfo.getPageHeight();
        int marginTop = 60;
        int marginBottom = 50;
        y = marginTop;
        paint.setTextSize(16);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setFakeBoldText(true);
        y = drawCheckboxState(canvas, plaqueImmatriculationArrCheckbox, "Plaque Immatriculation ARR", y);
        y = drawText(canvas, "FREINAGE", (pageInfo.getPageWidth() / 2) - 60, y + 25, paint, pageHeight, marginBottom + 35, document, pageInfo);
        // Example: Display fetched values from Stash
        y = drawText(canvas, "Disques Avant: " + Stash.getInt("disquesAvantValue", 0), 50, y + 25, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Plaquettes Avant: " + Stash.getInt("plaquettesAvantValue", 0), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Flexible G: " + (Stash.getBoolean("flexibleGChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Flexible D: " + (Stash.getBoolean("flexibleDChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Disques Arriere: " + (Stash.getBoolean("disquesArriereOuiSelected", false) ? Stash.getInt("disquesArriereValue", 0) : "Non"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Plaquettes Arriere: " + Stash.getInt("plaquettesArriereValue", 0), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Flexible Arriere G: " + (Stash.getBoolean("flexibleArriereGChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Flexible Arriere D: " + (Stash.getBoolean("flexibleArriereDChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "PNEUMATIQUE", (pageInfo.getPageWidth() / 2) - 60, y + 25, paint, pageHeight, marginBottom, document, pageInfo);

        y = drawText(canvas, "Pneu av taille: " + Stash.getString("pneuAvantTaille"), 50, y + 25, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Usure avant (%): " + (Stash.getInt("pneuAvantUsure", 0)), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Pneu arr taille: " + Stash.getString("pneuArriereTaille"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Usure arrière (%): " + (Stash.getInt("pneuArriereUsure", 0)), 50, y, paint, pageHeight, 2, document, pageInfo);

        y = drawText(canvas, "SOUS CAISSE", (pageInfo.getPageWidth() / 2) - 60, y + 25, paint, pageHeight, marginBottom, document, pageInfo);

        y = drawText(canvas, "Échappement: " + (Stash.getBoolean("echappementChecked", false) ? "Non" : "Oui"), 50, y + 25, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Fuite huile: " + (Stash.getBoolean("fuiteHuileChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);
        y = drawText(canvas, "Fuite liquide de refroidissement: " + (Stash.getBoolean("fuiteLiquideChecked", false) ? "Non" : "Oui"), 50, y, paint, pageHeight, 2, document, pageInfo);

        Log.d("gat", y + "");
        // Draw images if available
        if (imageUriList != null && !imageUriList.isEmpty()) {
            y += 20;
            int maxImageHeight = 320; // Maximum height for images

            for (Uri uri : imageUriList) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Bitmap scaledBitmap = scaleBitmapToHeight(bitmap, maxImageHeight);

                    if (y + scaledBitmap.getHeight() + marginBottom > pageInfo.getPageHeight()) {
                        document.finishPage(page);
                        page = document.startPage(pageInfo);
                        canvas = page.getCanvas();
                        y = 50;
                        // Reset y position for new page
                    }

                    canvas.drawBitmap(scaledBitmap, 50, y, paint);
                    y += scaledBitmap.getHeight() + 20; // Adjust y position based on image height

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Finish the page
        document.finishPage(page);

        // Save the document
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "pdf_preview.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the document
        document.close();

        // Open generated PDF file
        Uri pdfUri = FileProvider.getUriForFile(this, "com.moutamid.garageapp.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }

    // Method to draw checkbox state
    private int drawCheckboxState(Canvas canvas, CheckBox checkBox, String label, int y) {
        Paint paint = new Paint();
        paint.setTextSize(16);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setFakeBoldText(true);
        canvas.drawText(label + " :    " + (checkBox.isChecked() ? "Non" : "Oui"), 50, y, paint);
        return y + 25; // Return new y position
    }

    // Method to scale bitmap to fit within a specified height
    private Bitmap scaleBitmapToHeight(Bitmap bitmap, int maxHeight) {
        float aspectRatio = (float) bitmap.getWidth() / bitmap.getHeight();
        int width = (int) (maxHeight * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, maxHeight, true);
    }

    private int drawText(Canvas canvas, String text, float x, float y, Paint paint, int pageHeight, int marginBottom, PdfDocument document, PdfDocument.PageInfo pageInfo) {
        // Calculate remaining space on the current page
        int remainingSpace = pageHeight - marginBottom - (int) y;

        // Check if there is enough space for the text
        if (remainingSpace < 0) {
            // Add a new page if there isn't enough space
            canvas = addNewPage(document, pageInfo, "Final Report", (int) y, paint);
            y = pageInfo.getPageHeight() / 10;
        }

        // Apply bold text
        paint.setFakeBoldText(true);

        // Draw text on the canvas
        canvas.drawText(text, x, y, paint);

        // Return the updated y position with increased spacing
        return (int) (y + 2 * paint.descent() - paint.ascent());
    }


    private Canvas addNewPage(PdfDocument document, PdfDocument.PageInfo pageInfo, String title, int marginTop, Paint paint) {
        // Create a new page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw title if provided
        if (title != null) {
            paint.setColor(getResources().getColor(R.color.appColor)); // Customize color as needed
            paint.setTextSize(22); // Customize text size as needed
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setFakeBoldText(true); // Apply bold text
            canvas.drawText(title, (pageInfo.getPageWidth() / 2) - 60f, marginTop, paint); // Draw centered title
        }

        // Return the canvas for drawing content
        return canvas;
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
}
