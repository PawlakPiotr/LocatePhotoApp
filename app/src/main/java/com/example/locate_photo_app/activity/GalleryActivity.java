package com.example.locate_photo_app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.locate_photo_app.R;
import com.example.locate_photo_app.adapter.ItemData;
import com.example.locate_photo_app.adapter.GalleryItemAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    String path = Environment.getExternalStorageDirectory().toString()+"/PRM_IMAGES";

    List<Bitmap> images;
    List<String> file_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setRecyclerView(getFilesFromStorage());


    }

    private ItemData[] getFilesFromStorage() {
        File directory = new File(path);

        images = new ArrayList<>();
        file_names = new ArrayList<>();

        File[] files = directory.listFiles();
        ItemData[] items = new ItemData[files.length];

        for (int i = 0; i < files.length; i++) {
            items[i] = new ItemData(
                    files[i].getName(),
                    BitmapFactory.decodeFile(files[i].getAbsolutePath())
            );
        }

        return items;
    }

    private void setRecyclerView(ItemData[] items) {

        RecyclerView recyclerView = findViewById(R.id.gallery);

        GalleryItemAdapter adapter = new GalleryItemAdapter(items);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }

}
