package com.moutamid.garageapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class PageOneActivity extends AppCompatActivity {

    private EditText registrationNumberEditText;
    private EditText technicalInspectionDateEditText;

    @SuppressLint("MutatingSharedPrefs")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_one);
        checkApp(PageOneActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        Set<String> uriSet = sharedPreferences.getStringSet("imageUris", new HashSet<>());
        uriSet.clear();
        registrationNumberEditText = findViewById(R.id.registration_number);
        technicalInspectionDateEditText = findViewById(R.id.technical_inspection_date);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registrationNumber = registrationNumberEditText.getText().toString();
                String technicalInspectionDate = technicalInspectionDateEditText.getText().toString();

                // Validate input and proceed to the next screen
                if (validateInput(registrationNumber, technicalInspectionDate)) {
                    Intent intent = new Intent(PageOneActivity.this, PageTwoActivity.class);
                    Stash.put("registrationNumber", registrationNumber);
                    Stash.put("technicalInspectionDate", technicalInspectionDate);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput(String registrationNumber, String technicalInspectionDate) {
        if (registrationNumber.isEmpty()) {
            registrationNumberEditText.setError("Le numéro d'immatriculation est requis");
            return false;
        }

        if (technicalInspectionDate.isEmpty()) {
            technicalInspectionDateEditText.setError("La date du contrôle technique est requise");
            return false;
        }

        // Additional validation logic if needed

        return true;
    }

    public static void checkApp(Activity activity) {
        String appName = "GarageApp";
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuilder stringBuffer = new StringBuilder();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();
            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);
                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");
                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void camera(View view) {
        startActivity(new Intent(this, ImagesActivity.class));

    }
}
