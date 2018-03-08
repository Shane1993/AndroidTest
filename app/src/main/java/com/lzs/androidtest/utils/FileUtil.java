package com.lzs.androidtest.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.zip.ZipInputStream;

/**
 * Created by LEE on 2018/3/7.
 */

public class FileUtil {

    public static final int KB2B = 1000;//1024;
    private static final String[] SIZE_NAME = { "B", "KB", "MB", "GB", "TB" };

    public static String decompress(byte[] compressed) throws IOException {
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        ZipInputStream gis = new ZipInputStream(is);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        return string.toString();
    }

    public static String getExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String getFilePath(final Context context, final Uri uri) {
        // DocumentProvider
        if (uri != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if ("com.android.externalStorage.documents".equalsIgnoreCase(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type) || "content".equalsIgnoreCase(uri.getScheme())) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if ("com.android.providers.downloads.documents".equalsIgnoreCase(uri.getAuthority())) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri =
                        ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if ("com.android.providers.media.documents".equalsIgnoreCase(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if (uri != null && "file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return "";
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return "";
    }

    public static boolean isImg(String path) {
        String lowerCasePath = path.toLowerCase();
        return lowerCasePath.endsWith("png") || lowerCasePath.endsWith("jpg") || lowerCasePath.endsWith(
                "jpeg") || lowerCasePath.endsWith("gif") || lowerCasePath.endsWith("bmp");
    }

    //获取指定文件大小，最后结果以合适的单位返回
    public static String getSuitableSize(File file, int digits) {
        long blockSize = 0;
        if (file.exists()) {
            try {
                if (file.isDirectory()) {
                    blockSize = getFileSizes(file);
                } else {
                    blockSize = getFileSize(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return FormetFileSize(blockSize, digits);
    }

    private static long getFileSizes(File file) throws Exception {
        long sumSize = 0;
        File[] childFile = file.listFiles();
        for (File itemFile : childFile) {
            if (itemFile.isDirectory()) {
                sumSize += getFileSizes(itemFile);
            } else if (itemFile.isFile()) {
                sumSize += getFileSize(itemFile);
            }
        }
        return sumSize;
    }

    private static long getFileSize(File file) throws Exception {
        if (file.exists()) {
            //      FileInputStream fis = null;
            //      fis = new FileInputStream(file);
            ////      size = fis.available();//单个文件最大1.9G，2^32次方，int 最大
            //      FileChannel fc  = fis.getChannel();
            //      size = fc.size();
            return file.length();
        }
        return 0;
    }

    private static String FormetFileSize(long size, int digits) {
        int i = 0;
        while (i < SIZE_NAME.length && size >= KB2B) {
            size /= KB2B;
            i++;
        }
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(digits);
        return ddf1.format(size) + SIZE_NAME[i];
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists()) return;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete(); // 删除所有文件
                } else if (file.isDirectory()) deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
        } else {
            dir.delete();// 删除目录本身
        }
    }

    /**
     * 创建缓存文件路径(优先选择sd卡)
     *
     * @param cacheDirName 缓存文件夹名称
     * @param context 上下文
     */
    public static String getFileDir(String cacheDirName, Context context) {
        String cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = getExternalCacheDir(context) + "/" + cacheDirName;
            //部分机型返回了null
            if (cacheDir == null) {
                cacheDir = getInternalCacheDir(context) + "/" + cacheDirName;
            }
        } else {
            cacheDir = getInternalCacheDir(context) + "/" + cacheDirName;
        }
        return cacheDir;
    }

    private static String getExternalCacheDir(Context context) {
        File dir = context.getExternalCacheDir();
        if (dir == null) {
            return null;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    private static String getInternalCacheDir(Context context) {
        File dir = context.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    /**
     * 删除文件.
     *
     * @return true, if successful
     */
    public static boolean deleteFile(File file) {

        try {
            if (file == null) {
                return true;
            }
            //如果是路径
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files.length == 0) {
                    return file.delete();
                }
                for (File file1 : files) {
                    deleteFile(file1);
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
