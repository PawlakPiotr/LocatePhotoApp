package com.example.locate_photo_app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locate_photo_app.R;
import com.example.locate_photo_app.utils.Image;
import com.example.locate_photo_app.utils.Permissions;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static String auth_provider = "com.example.locate_photo_app.provider";

    Button photo, gallery, settings;
    Permissions permissions;
    Image img;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        permissions = new Permissions();
        img = new Image();
        permissions.checkPermissions(this, this);
        setComponents();
    }

    private void setComponents() {
        photo = findViewById(R.id.photo_btn);
        gallery = findViewById(R.id.gallery_btn);
        settings = findViewById(R.id.settings_btn);

        setButtonsListeners(new Button[]{photo, gallery, settings});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.photo_btn:
                takePhoto();
                break;
            case R.id.gallery_btn:
                startActivity(
                        getSpecificIntent(StartActivity.class)); // TODO gallery activity
                break;
            case R.id.settings_btn:
                startActivity(
                        getSpecificIntent(SettingsActivity.class)); // TODO settings activity
                break;
            default:
                break;
        }
    }

    public void takePhoto() {

        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Uri imageUri = FileProvider.getUriForFile(
                StartActivity.this, auth_provider, img.setImage());

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(imageIntent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("###### PIC CAPTURED #####");
                }
        }
    }

    private void setButtonsListeners(Button[] arr) {
        for(Button btn : arr) {
            btn.setOnClickListener(this);
        }
    }

   public Intent getSpecificIntent(Class cs) {
        return new Intent(this, cs);
    }
}
