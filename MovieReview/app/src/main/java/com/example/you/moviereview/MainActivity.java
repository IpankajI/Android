package com.example.you.moviereview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button popularMovie;
    private Button topRatedMovie;
    private Button aboutMe;
    final String popularMovieURL="http://api.themoviedb.org/3/movie/popular?api_key=57e535585236868828540040889c5a0e";
    final String topRatedMovieURL="http://api.themoviedb.org/3/movie/top_rated?api_key=57e535585236868828540040889c5a0e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popularMovie=(Button)findViewById(R.id.popularMovie);
        topRatedMovie=(Button)findViewById(R.id.topRatedMovie);
        aboutMe=(Button)findViewById(R.id.aboutMe);
        Log.i("MainActivity","started");
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AboutMe.class);
                startActivity(intent);
            }
        });
        popularMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GridActivity.class);
                intent.putExtra("URL",popularMovieURL);
                startActivity(intent);
                Log.i("MainActivity","popular movie info");
            }
        });
        topRatedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GridActivity.class);
                intent.putExtra("URL",topRatedMovieURL);
                startActivity(intent);
                Log.i("MainActivity","top rated movie info");
            }
        });
    }
}
