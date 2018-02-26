package com.lzs.androidtest;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzs.androidtest.dagger.DaggerActivity;
import com.lzs.androidtest.utils.PathResolver;

/**
 * Created by LEE on 2018/2/26.
 */

public class FilePickerActivity extends Activity implements View.OnClickListener{


    private static final String TAG = "MainActivity";

    public static final int GET_CONTENT = 9990;

    Button btnPickFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        btnPickFile = (Button) findViewById(R.id.btn_pick);

        btnPickFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pick:
                pickFile();
                break;
        }
    }

    public void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        Intent fileIntent = Intent.createChooser(intent,"choose files");
        startActivityForResult(fileIntent, GET_CONTENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == GET_CONTENT && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();

            if(clipData != null) {
                // multiple data
                int length = clipData.getItemCount();
                for(int i = 0; i < length; ++i) {
                    l("----------------------Data--------------------");
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    l("uri ----->" + uri);
                    l("uri ----->" + Uri.decode(uri.toString()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        String path = "file://" + PathResolver.getRealPathFromURI(this.getApplicationContext(), uri);
                        l("path ---->" + path);
                        l("fileName ----->" + PathResolver.getFileName(path));
                    }
                }
            }else {
                l("----------------------Data--------------------");
                Uri uri = data.getData();
                l("uri ----->" + uri.toString());
                l("uri ----->" + Uri.decode(uri.toString()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    String path = "file://" + PathResolver.getRealPathFromURI(this.getApplicationContext(), uri);
                    l("path ---->" + path);
                    l("fileName ----->" + PathResolver.getFileName(path));
                }

            }

        }

    }

    // -------------------------

    public String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {

                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public void l(Object obj) {
        Log.d(TAG, obj.toString());
    }

}
