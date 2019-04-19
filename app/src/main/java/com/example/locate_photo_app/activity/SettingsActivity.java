package com.example.locate_photo_app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.locate_photo_app.R;
import com.example.locate_photo_app.utils.Image;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Map<String, Integer> imgColor;
    Spinner colors;
    Image img;
    ArrayAdapter<CharSequence> adapter;
    int color, txtSize;
    Button save;
    EditText locationSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        img = new Image();
        color = Integer.valueOf(getIntent().getStringExtra("color"));
        txtSize = Integer.valueOf(getIntent().getStringExtra("txtSize"));

        img.setImgColor(color);
        img.setImgTextSize(txtSize);

        setComponents();
        setColorsMap();

        getColorFromImageToSpinner();

    }

    public void setComponents() {

        colors = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,
                        R.array.colors_arr, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colors.setAdapter(adapter);
        colors.setOnItemSelectedListener(this);

        locationSize = findViewById(R.id.location_text_size);
        locationSize.setText(String.valueOf(img.getImg_text_size()));

        save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getSpecificIntent(StartActivity.class));
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = colors.getSelectedItem().toString();
        int color = imgColor.get(text);

        img.setImgColor(color);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    private void getColorFromImageToSpinner() {
        for (int i=0; i< adapter.getCount(); i++) {
            if(imgColor.get(adapter.getItem(i).toString()) == img.getImg_color()) {
                colors.setSelection(i);
            }
        }
    }

    private void setColorsMap() {

        imgColor = new HashMap<>();

        imgColor.put("WHITE", Color.WHITE);
        imgColor.put("BLACK", Color.BLACK);
        imgColor.put("BLUE", Color.BLUE);
        imgColor.put("YELLOW", Color.YELLOW);
        imgColor.put("RED", Color.RED );
        imgColor.put("GREEN", Color.GREEN);
        imgColor.put("GREY", Color.GRAY);
        imgColor.put("MAGENTA", Color.MAGENTA);
    }

    public Intent getSpecificIntent(Class cs) {
        return new Intent(this, cs)
                .putExtra("color", String.valueOf(img.getImg_color()))
                .putExtra("txtSize", String.valueOf(img.getImg_text_size()));
    }

}
