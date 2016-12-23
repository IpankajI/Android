package com.example.you.moviereview;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by you on 12/22/2016.
 */

public class ImageObject {
    Bitmap bitmap;
    String title;
    ImageObject(Bitmap bitmap,String title){
        this.bitmap=bitmap;
        this.title=title;
        Log.i("ImageObject","Constructor");
    }
}