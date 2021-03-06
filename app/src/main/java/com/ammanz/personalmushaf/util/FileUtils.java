package com.ammanz.personalmushaf.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static String ASSETSDIRECTORY;

    public static boolean checkRootDataDirectory(Context context) {
        ASSETSDIRECTORY = getAssetsDirectory(context);
        File dataDirectory = new File(ASSETSDIRECTORY);
        return dataDirectory.exists() && dataDirectory.isDirectory();
    }

    public static String getAssetsDirectory(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));

        try {
            ZipEntry zipEntry;
            int count;
            byte[] buffer = new byte[8192];
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File file = new File(targetDirectory, zipEntry.getName());
                File dir = zipEntry.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (zipEntry.isDirectory())
                    continue;
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    while ((count = zipInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                } finally {
                    fileOutputStream.close();
                }
            }
        } finally {
            zipInputStream.close();
        }
    }

    public static void validateDirectory(File directory) {
        if (!directory.exists())
            directory.mkdir();
        else if (!directory.isDirectory()){
            directory.delete();
            directory.mkdir();
        }
    }
}
