package com.example.module.mine.Utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * @ClassName PhotoUtils
 * @Description 将照片的虚拟路径转换成真实路径
 * @Author JK_Wei
 * @Date 2024-03-21
 * @Version 1.0
 */

public class PhotoUtils {
    public static String getRealPath(Context context, Intent data){
        if (Build.VERSION.SDK_INT >= 19){
            return handleImageOkKitKat(context,data);
        }else {
            return handleImageBeforeKitKat(context,data);
        }
    }
    
    @TargetApi(19)
    public static String handleImageOkKitKat(Context context,Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context,uri)){
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docID.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath = getImagePath(context,contentUri,null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(context,uri,null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }
    
    public static String handleImageBeforeKitKat(Context context,Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(context,uri,null);
        return imagePath;
    }
    
    @SuppressLint("Range")
    public static String getImagePath(Context context,Uri uri, String selection){
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}