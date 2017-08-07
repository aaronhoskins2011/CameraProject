package com.example.aaron.cameraproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    ImageView ivPhoto;
    Button takePicture;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ivPhoto = (ImageView)findViewById(R.id.imgView1);
        takePicture = (Button)findViewById(R.id.takePhoto);

    }

    public void takePhoto(View view){
        Intent camIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File picFile = null;
        try {
            picFile = createImageFile();
        } catch (IOException ex) {

        }
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
        startActivityForResult(camIntent,CAMERA_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && requestCode == RESULT_OK){
            Bitmap pic = (Bitmap)data.getExtras().get("data");
            ivPhoto.setImageBitmap(pic);

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
