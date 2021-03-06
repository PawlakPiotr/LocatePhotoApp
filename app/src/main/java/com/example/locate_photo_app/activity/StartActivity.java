package com.example.locate_photo_app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.locate_photo_app.R;
import com.example.locate_photo_app.utils.GPSLocation;
import com.example.locate_photo_app.utils.Image;
import com.example.locate_photo_app.utils.Permissions;

import java.util.List;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static String auth_provider = "com.example.locate_photo_app.provider";
    String path = Environment.getExternalStorageDirectory().toString()+"/PRM_IMAGES";

    List<Bitmap> images;
    List<String> file_names;

    Button photo, gallery, settings;
    Permissions permissions;
    Image img;
    GPSLocation gps;
    int color, txtSize;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        permissions = new Permissions();
        img = new Image();
        gps = new GPSLocation(this);

        permissions.checkPermissions(this, this);
        gps.getLocation();

        getIntentValues();

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
                        getSpecificIntent(GalleryActivity.class));
                break;
            case R.id.settings_btn:
                startActivity(
                        getSpecificIntent(SettingsActivity.class));
                break;
            default:
                break;
        }
    }

    public void takePhoto() {
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageIntent.resolveActivity(getPackageManager()) != null) {
            Uri imageUri = FileProvider.getUriForFile(
                    StartActivity.this, auth_provider, img.setImage());

            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(imageIntent,1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    if(gps.canGetLocation()) {

                        img.addLocationToImage(gps);

                        Toast.makeText(getApplicationContext(),
                                 img.getImageName() + " captured, \nLocation: - " + gps.getAddress(),
                                            Toast.LENGTH_LONG).show();

                    }
                }
        }
    }

    private void setButtonsListeners(Button[] arr) {
        for(Button btn : arr) {
            btn.setOnClickListener(this);
        }
    }

   public Intent getSpecificIntent(Class cs) {
        Intent intent = new Intent(this, cs);

        intent.putExtra("color", String.valueOf(img.getImg_color()));
        intent.putExtra("txtSize", String.valueOf(img.getImg_text_size()));

        return intent;
    }

    private void getIntentValues() {
        color = getIntent().getStringExtra("color") != null ?
                    Integer.valueOf(getIntent().getStringExtra("color")) : img.getImg_color();

        txtSize = getIntent().getStringExtra("txtSize") != null ?
                    Integer.valueOf(getIntent().getStringExtra("txtSize")) : img.getImg_text_size();

        img.setImgColor(color);
        img.setImgTextSize(txtSize);
    }

}
