package com.moutamid.garageapp.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import androidx.core.content.FileProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtil {

    public static void createPdf(Context context, View view) {
        // Path to the PDF file
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/PDF_Demo.pdf";

        // Create a Document
        Document document = new Document();

        try {
            // Initialize PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Open the document for writing
            document.open();

            // Convert view to bitmap
            Bitmap bitmap = viewToBitmap(view);

            // Create an Image instance
            Image image = Image.getInstance(bitmapToByteArray(bitmap));

            // Scale the image to fit the document
            image.scaleToFit(document.getPageSize());

            // Add the image to the document
            document.add(image);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            // Close the document
            document.close();

            // Share the PDF file
            sharePdfFile(context, filePath);
        }
    }

    private static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private static void sharePdfFile(Context context, String filePath) {
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivity(Intent.createChooser(shareIntent, "Share PDF using"));
        }
    }
}
