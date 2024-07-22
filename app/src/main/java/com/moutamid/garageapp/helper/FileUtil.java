package com.moutamid.garageapp.helper;

import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    public static File getFileWithTimestamp() {
        // Get the current date and time
        Date now = new Date();
        // Format the date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timestamp = sdf.format(now);

        // Create the filename with the timestamp
        String filename = "pdf_preview_" + timestamp + ".pdf";

        // Create the file object
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);

        return file;
    }


}
