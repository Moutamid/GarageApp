package com.moutamid.garageapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.garageapp.adapter.ImageAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imageUriList;

    // ActivityResultLauncher for permission request
    private ActivityResultLauncher<String> requestPermissionLauncher;

    // ActivityResultLauncher for opening document
    private ActivityResultLauncher<String[]> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSelectImages = findViewById(R.id.btn_select_images);
        Button btnPreviewPdf = findViewById(R.id.btn_preview_pdf);
        recyclerView = findViewById(R.id.recycler_view);
        imageUriList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageUriList, new ImageAdapter.OnImageClickListener() {
            @Override
            public void onRemoveClick(int position) {
                imageUriList.remove(position);
                imageAdapter.notifyItemRemoved(position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);

        // Initialize ActivityResultLauncher for permission request
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Permission is granted, proceed to open document
                        pickImages();
                    } else {
                        // Permission denied, handle accordingly
                        // For example, show a message or disable functionality
                    }
                });

        // Initialize ActivityResultLauncher for opening document
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        // Handle the selected document URI
                        imageUriList.add(uri);
                        imageAdapter.notifyDataSetChanged();
                    }
                });

        btnSelectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenDocument();
            }
        });

        btnPreviewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewPdf();
            }
        });
    }

    private void checkPermissionAndOpenDocument() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, proceed to open document
            pickImages();
        } else {
            // Permission has not been granted, request it
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        getContentLauncher.launch(new String[]{"image/*"});
    }

    private void previewPdf() {
        Intent intent = new Intent(this, PdfPreviewActivity.class);
        intent.putParcelableArrayListExtra("imageUriList", imageUriList);
        startActivity(intent);
    }
}
