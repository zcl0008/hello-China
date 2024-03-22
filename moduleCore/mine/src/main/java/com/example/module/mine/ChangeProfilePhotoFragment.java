package com.example.module.mine;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.module.mine.Utils.PhotoUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName ChnageProfilePhotoFragment
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-21
 * @Version 1.0
 */

public class ChangeProfilePhotoFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    ChangeProfilePhotoListener listener;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    public View view;
    public Uri photo_uri;
    TextView take_photo;
    TextView choose_photo;
    TextView cancel;

    public ChangeProfilePhotoFragment(ChangeProfilePhotoListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_profile_photo_fragment,container,false);

        initWidget();

        return view;
    }
    public void initWidget(){
        take_photo = (TextView) view.findViewById(R.id.take_photo);
        choose_photo = (TextView) view.findViewById(R.id.choose_photo);
        cancel = (TextView) view.findViewById(R.id.cancel);

        take_photo.setOnClickListener(this);
        choose_photo.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(getContext(),"You denied the Permission!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void openAlbum() {

        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO); // 打开本地存储
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(photo_uri));
                        listener.changeProfilePhoto(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //photo.setImageURI(data.getData());//可以直接展示照片
                    //也可以使用如下方法：
                    String realPath = PhotoUtils.getRealPath(getContext(),data);
                    if (realPath != null){
                        Bitmap bitmap = BitmapFactory.decodeFile(realPath);
                        listener.changeProfilePhoto(bitmap);
                    }else {
                        Toast.makeText(getContext(),"failed to get imagee",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.take_photo){
            File outputImage = new File(getContext().getExternalCacheDir(),"output_image.jpg");
            try {
                if (outputImage.exists()){
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (Build.VERSION.SDK_INT >= 24){
                photo_uri = FileProvider.getUriForFile(getContext(),"com.example.nihaochina.fileprovider",
                        outputImage);
            }else {
                photo_uri = Uri.fromFile(outputImage);
            }

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photo_uri);
            startActivityForResult(intent,TAKE_PHOTO);
            this.dismiss();
        }
        if (view.getId() == R.id.choose_photo){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                this.dismiss();
            }else {
                openAlbum();
                this.dismiss();
            }
        }
        if (view.getId() == R.id.cancel){
            //listener.disMissFragment();
            this.dismiss();
        }
    }
}