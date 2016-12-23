package com.example.you.moviereview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GridActivity extends AppCompatActivity {
    private GridView gridView;
    private ProgressDialog pd;
    private String url;
    static int numberOfMovie=0;
    static int len=0;
    boolean flag;
    int pBarDisable=0;
    final String posterPath="http://image.tmdb.org/t/p/w185";
    ArrayList<movieData> movieDatas;
    ArrayList<ImageObject> imageObjects;
    ImageArrayAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Intent intent=getIntent();
        url=intent.getStringExtra("URL");
        new LoadJSON().execute(url);
        gridView=(GridView)findViewById(R.id.gridView);
        movieDatas=new ArrayList<>();
        imageObjects=new ArrayList<>();
        gridAdapter=new ImageArrayAdapter(GridActivity.this,R.layout.activity_grid,imageObjects);
        gridView.setAdapter(gridAdapter);
        Log.i("GridActivity","started width"+String.valueOf(gridView.getWidth()));




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!flag) return;
                Intent intent=new Intent(GridActivity.this,DetailActivity.class);
                intent.putExtra("movieData",movieDatas.get(position));
                startActivity(intent);
            }
        });
    }
    class LoadJSON extends AsyncTask<String,Void,JSONObject>{
        @Override
        protected void onPreExecute(){
            pd=new ProgressDialog(GridActivity.this);
            pd.setIndeterminate(true);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Loading...");
            pd.setCancelable(true);
            pd.show();
            flag=false;
            Log.i("GridActivity","started");
        }
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject=null;
            HttpURLConnection conn=null;
            Log.i("GridActivity","doInBackGround JSON");
            try {
                Log.i("GridActivity","JSON Http");
                conn=(HttpURLConnection)new URL(params[0]).openConnection();
                Log.i("JSON respose code::",String.valueOf(conn.getResponseCode()));
                if(conn.getResponseCode()==200)
                conn.connect();
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                br.close();
                conn.disconnect();
                jsonObject= new JSONObject(sb.toString());

            } catch (MalformedURLException e) {
                Toast.makeText(GridActivity.this,"More than 5 seconds",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
        @Override
        protected void onPostExecute(JSONObject res){

            if(res==null){
                Toast.makeText(GridActivity.this,"Network Error!",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray jsonArray=res.getJSONArray("results");
                numberOfMovie=jsonArray.length();
                for(int i=0;i<numberOfMovie;i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    new LoadImages().execute(new movieData(
                            jsonObject.getString("backdrop_path"),
                            jsonObject.getString("id"),
                            jsonObject.getString("poster_path"),
                            jsonObject.getString("overview"),
                            jsonObject.getString("title"),
                            jsonObject.getString("vote_average"),
                            jsonObject.getString("vote_count")
                    ));
                    Log.i("GridActivity",posterPath+jsonObject.getString("poster_path"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class LoadImages extends AsyncTask<movieData, Void, Bitmap> {
        movieData data;
        @Override
        protected Bitmap doInBackground(movieData... params) {
            data=params[0];
            Bitmap bitmap=null;
            HttpURLConnection conn;
            try {
                conn=(HttpURLConnection)new URL(posterPath+data.posterPath).openConnection();
                Log.i("Respose Code",String.valueOf(conn.getResponseCode()));
                if(conn.getResponseCode()==200){
                    conn.connect();
                    InputStream is=conn.getInputStream();
                    bitmap= BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap res){
            if(!flag){
                //pd.setCancelable(false);
                pd.dismiss();
            }
            flag=true;
            if(res!=null){
                movieDatas.add(data);
                imageObjects.add(new ImageObject(res,data.title));
                len=imageObjects.size();
                gridAdapter.addGridItem(imageObjects);
                Log.i("GridActivity",data.title+" image  executed, Numbers: "+String.valueOf(len-1));
            }

        }
    }
}
    //movieData(String backdropPath,String id,String posterPath,String review, String title,String voteAverage,String voteCount)