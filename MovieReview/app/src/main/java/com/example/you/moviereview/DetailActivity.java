package com.example.you.moviereview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    movieData movieData;
    ProgressDialog progressDialog;
    TextView fullName;
    TextView review;
    TextView rating;
    TextView voteCount;
    TextView overViewWriting;
    TextView fullNameWriting;
    ImageView imageView;
    final  String url="http://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        movieData=intent.getParcelableExtra("movieData");

        new ImageLoader().execute(url+movieData.backdropPath);

        progressDialog=new ProgressDialog(DetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        fullName=(TextView)findViewById(R.id.fullName);
        review=(TextView)findViewById(R.id.review);
        rating=(TextView)findViewById(R.id.rating);
        overViewWriting=(TextView)findViewById(R.id.overViewWriting);
        fullNameWriting=(TextView)findViewById(R.id.fullNameWriting);
        voteCount=(TextView)findViewById(R.id.voteCount);
        imageView=(ImageView)findViewById(R.id.secondaryPoster);


    }
    class ImageLoader extends AsyncTask<String,Void,Bitmap>{
        Bitmap bitmap=null;
        HttpURLConnection conn;
        @Override
        protected Bitmap doInBackground(String... params) {
            Log.i("DetailActivity"," doInBVackground :"+params[0]);
            try {
                conn=(HttpURLConnection)new URL(params[0]).openConnection();
                if(conn.getResponseCode()==200);
                conn.connect();
                InputStream is=conn.getInputStream();
                bitmap= BitmapFactory.decodeStream(is);
                is.close();
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap res){
            progressDialog.dismiss();
            if(res==null){
                Toast.makeText(DetailActivity.this,"Network Error!",Toast.LENGTH_SHORT).show();
                return;
            }


            imageView.setImageBitmap(res);
            fullName.setText(movieData.title);
            rating.setText(movieData.voteAverage+"/10");
            voteCount.setText("From "+movieData.voteCount+" votes");
            review.setMovementMethod(new ScrollingMovementMethod());
            review.setText(movieData.review);
            overViewWriting.setText("REVIEW:");
            fullNameWriting.setText("Full Name:");
        }
    }
}
