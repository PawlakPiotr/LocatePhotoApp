package com.example.locate_photo_app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image extends AppCompatActivity {

    private static String IMG_DIR = Environment.getExternalStorageDirectory() + "/PRM_IMAGES";
    private static String EXTENSION = ".jpg", FILE_NAME = "IMG_", DIR = "PRM_IMAGES";

    String datePattern = "yyyy-MM-dd_HH:mm", imageName = null;
    File imgFile;

    int img_text_size = 50, img_color = Color.WHITE;

    public File setImage() {
        String timeStamp = new SimpleDateFormat(datePattern).format(new Date());

        String folderPath = IMG_DIR;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            File dir = new File(folderPath);
            dir.mkdirs();
        }

        imageName = FILE_NAME + timeStamp + EXTENSION;

        File imagesFolder = new File(Environment.getExternalStorageDirectory(), DIR);
        File image = new File(imagesFolder, imageName);

        imgFile = image;

        return  imgFile;
    }

    public void addLocationToImage(GPSLocation gpsLocation) {
        Bitmap bmp = makeBitmapMutable(
                BitmapFactory.decodeFile(imgFile.getAbsolutePath()));

        Canvas cnv = new Canvas(bmp);
        Paint pnt = new Paint();

        pnt.setTextSize(img_text_size);
        pnt.setColor(img_color);

        cnv.drawBitmap(bmp, 0,0, pnt);
        cnv.drawText(gpsLocation.getAddress(), 20, 85, pnt);

        try {
            bmp.compress(Bitmap.CompressFormat.JPEG,100,
                    new FileOutputStream(new File(imgFile.getAbsolutePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public File getImgFile() { return imgFile; }

    public String getImageName() {
        return imageName;
    }

    public void setImgTextSize(int size) {
        this.img_text_size = size;
    }

    public int getImg_text_size() {
        return img_text_size;
    }

    public void setImgColor(int color) {
        this.img_color = color;
    }

    public int getImg_color() {
        return img_color;
    }

    private Bitmap makeBitmapMutable(Bitmap bitmap) {
        return bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
