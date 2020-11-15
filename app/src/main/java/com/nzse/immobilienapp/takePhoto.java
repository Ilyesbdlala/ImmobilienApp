package com.nzse.immobilienapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class takePhoto extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;

    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     try {
                                         dispatchTakePictureIntent();
                                     } catch (Exception e) {
                                         System.out.println("Try dispatchTakePictureIntent:" + e.getMessage());
                                     }
                                 }
                             }
        );


    }

    private void previewPicture() {
        testCache();
        Drawable d;

        //d = Drawable.createFromPath(currentPhotoPath);
        //Bitmap b = ((BitmapDrawable)d).getBitmap();
        final int THUMBNAILSIZE = 64;
        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(currentPhotoPath),
                THUMBNAILSIZE, THUMBNAILSIZE);

        //int width = 200;
        //int height = 200;
        //Bitmap bitmapResized = Bitmap.createScaledBitmap(thumbImage, width,height, false);
        //d =  new BitmapDrawable(getResources(), bitmapResized);
        d =  new BitmapDrawable(getResources(), thumbImage);

        ImageView imageView = findViewById(R.id.thumb);
        imageView.setImageDrawable(d);

        System.out.println("preview: " + currentPhotoPath);
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                System.out.println(photoFile.getAbsolutePath());
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("Exception dispatchTakePictureIntent");
            }
            // Continue only if the File was successfully created
            // directory must exist!
            if (photoFile != null) {
                // System.out.println("Photo: " + photoFile.getAbsolutePath());
                Uri photoURI = FileProvider.getUriForFile(this,
                        "photoprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == takePhoto.REQUEST_TAKE_PHOTO && resultCode == MainActivity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), R.string.FotoGespeichert, Toast.LENGTH_LONG).show();
            previewPicture();
            EditorFragment.currentImagePath = currentPhotoPath;
            Switch s =  findViewById(R.id.switchPhoto);
            s.setChecked(true);

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // Standard directory in which to place pictures that are available to
        // the user. Note that this is primarily a convention for the top-level
        // public directory, as the media scanner will find and collect pictures
        // in any directory:         ... /files/Pictures
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // ... JPEG_2019...jpg
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
        // Save file path
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    public void testCache() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println("Pfad: " + storageDir.getAbsolutePath());
        File[] dateien = storageDir.listFiles();
        for (File f : dateien)
            System.out.println("Datei:" + f.getAbsolutePath());
    }

    public void donePhoto (View view){

    this.finish();

    }
}
