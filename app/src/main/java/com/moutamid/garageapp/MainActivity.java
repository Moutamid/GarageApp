package com.moutamid.garageapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.garageapp.adapter.ImageAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imageUriList;
    private Uri photoURI;

    // ActivityResultLauncher for permission request
    private ActivityResultLauncher<String> requestPermissionLauncher;

    // ActivityResultLauncher for taking picture
    private ActivityResultLauncher<Uri> takePictureLauncher;

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
                        // Permission is granted, proceed to open camera
                        openCamera();
                    } else {
                        // Permission denied, handle accordingly
                        // For example, show a message or disable functionality
                    }
                });

        // Initialize ActivityResultLauncher for taking picture
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess && photoURI != null) {
                        // Handle the captured image URI
                        imageUriList.add(photoURI);
                        imageAdapter.notifyDataSetChanged();
                    }
                });

        btnSelectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenCamera();
            }
        });

        btnPreviewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewPdf();
            }
        });
    }

    private void checkPermissionAndOpenCamera() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, proceed to open camera
            openCamera();
        } else {
            // Permission has not been granted, request it
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.moutamid.garageapp.fileprovider",
                        photoFile);
                takePictureLauncher.launch(photoURI);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void previewPdf() {
        Intent intent = new Intent(this, PdfPreviewActivity.class);
        intent.putParcelableArrayListExtra("imageUriList", imageUriList);
        startActivity(intent);
    }
}
