package com.moutamid.garageapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.Set;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imageUriList;
    private Uri photoURI;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Uri> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Button btnSelectImages = findViewById(R.id.btn_select_images);
        recyclerView = findViewById(R.id.recycler_view);
        imageUriList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageUriList, new ImageAdapter.OnImageClickListener() {
            @Override
            public void onRemoveClick(int position) {
                imageUriList.remove(position);
                imageAdapter.notifyItemRemoved(position);
                saveImageUriList(); // Save the updated list when an image is removed
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);

        loadImageUriList(); // Load saved image URIs

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openCamera();
                    }
                });

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess && photoURI != null) {
                        // Handle the captured image URI
                        imageUriList.add(photoURI);
                        imageAdapter.notifyDataSetChanged();
                        saveImageUriList(); // Save the updated list
                    }
                });

        btnSelectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenCamera();
            }
        });
    }

    private void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.moutamid.garageapp.fileprovider",
                        photoFile);
                takePictureLauncher.launch(photoURI);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void saveImageUriList() {
        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> uriSet = new HashSet<>();
        for (Uri uri : imageUriList) {
            uriSet.add(uri.toString());
        }
        editor.putStringSet("imageUris", uriSet);
        editor.apply();
    }

    private void loadImageUriList() {
        SharedPreferences sharedPreferences = getSharedPreferences("ImagePreferences", MODE_PRIVATE);
        Set<String> uriSet = sharedPreferences.getStringSet("imageUris", new HashSet<>());
        for (String uriString : uriSet) {
            imageUriList.add(Uri.parse(uriString));
        }
        imageAdapter.notifyDataSetChanged();
    }

    public void next(View view) {
        onBackPressed();
    }
}
