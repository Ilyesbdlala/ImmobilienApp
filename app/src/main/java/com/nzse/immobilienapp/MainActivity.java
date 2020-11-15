package com.nzse.immobilienapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nzse.immobilienapp.backend.backendContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
  //@Override
  private static final int REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    public static backendContainer bcd;






  public void kundeActivity (View view){


    Intent intent = new Intent(MainActivity.this , AnzeigeKundeGallery.class);
    startActivity(intent);
  }
   public void maklerActivity (View view){

    Intent intent = new Intent(MainActivity.this , navigationMakler.class);
    startActivity(intent);
  }


  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    MainActivity.bcd = new backendContainer(this);
     verifyPermissions();

/*
      try {
          MainActivity.bcd.addAnzeige();
         MainActivity.bcd.saveFile(bcd);
      } catch (IOException e) {
          e.printStackTrace();
      }
      MainActivity.bcd.readFile();

*/
  }

    public backendContainer getBcd() {
        return bcd;
    }




    private void verifyPermissions(){
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            MainActivity.bcd.readFile();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    permissions,
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }


}
