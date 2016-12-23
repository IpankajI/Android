package com.example.you.moviereview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

/**
 * Created by you on 12/22/2016.
 */

public class ImageArrayAdapter extends ArrayAdapter<ImageObject> {
    private Context context;
    private int resource;
    private ArrayList<ImageObject> arrayList=new ArrayList<>();
    public ImageArrayAdapter(Context context, int resource, ArrayList<ImageObject> arrayList) {
        super(context, resource, arrayList);
        for(int i=0;i<arrayList.size();++i)
        Log.i("ImageArrayAdapter","movie came" +arrayList.get(i).title);
        this.context=context;
        this.resource=resource;
        this.arrayList=arrayList;
    }
    public void addGridItem(ArrayList<ImageObject> arrayList){
        this.arrayList=arrayList;
        Log.i("ImageAdapter"," addGridItem");
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent){
        View view=converView;
        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.grid_customization,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(300,500));
            Log.i("ImageArrayAdapter","new  movie !");
        }
        TextView textView=(TextView)view.findViewById(R.id.title);
        ImageView imageView=(ImageView)view.findViewById(R.id.primaryImage);
        textView.setText(arrayList.get(position).title);
        imageView.setImageBitmap(arrayList.get(position).bitmap);
        return  view;
    }
}
