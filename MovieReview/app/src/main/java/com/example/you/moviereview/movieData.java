package com.example.you.moviereview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by you on 12/21/2016.
 */

public class movieData implements Parcelable {
    String backdropPath;
    String  id;
    String posterPath;
    String review;
    String title;
    String  voteAverage;
    String voteCount;
    movieData(String backdropPath,String id,String posterPath,String review, String title,String voteAverage,String voteCount){
        this.backdropPath=backdropPath;
        this.id=id;
        this.posterPath=posterPath;
        this.review=review;
        this.title=title;
        this.voteAverage=voteAverage;
        this.voteCount=voteCount;
    }
    protected movieData(Parcel in) {
        backdropPath=in.readString();
        id=in.readString();
        posterPath=in.readString();
        review=in.readString();
        title=in.readString();
        voteAverage=in.readString();
        voteCount=in.readString();
    }

    public static final Creator<movieData> CREATOR = new Creator<movieData>() {
        @Override
        public movieData createFromParcel(Parcel in) {
            return new movieData(in);
        }

        @Override
        public movieData[] newArray(int size) {
            return new movieData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropPath);
        dest.writeString(id);
        dest.writeString(posterPath);
        dest.writeString(review);
        dest.writeString(title);
        dest.writeString(voteAverage);
        dest.writeString(voteCount);
    }
}
