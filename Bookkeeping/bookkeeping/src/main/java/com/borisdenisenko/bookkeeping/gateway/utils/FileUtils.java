package com.borisdenisenko.bookkeeping.gateway.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class FileUtils {

    private static final String FILE_EXT = ".txt";

    private FileUtils() {
    }

    public static File getFileForSite() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), String.valueOf(System.currentTimeMillis()) + FILE_EXT);
    }

}
