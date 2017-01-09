package com.example.you.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    private Button speakUS,speakUK;
    private TextToSpeech ttsUS,ttsUK;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(EditText)findViewById(R.id.text);
        speakUS=(Button)findViewById(R.id.speakUS);
        speakUK=(Button)findViewById(R.id.speakUK);
        ttsUS=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status){
                if(status!=TextToSpeech.ERROR){
                    ttsUS.setLanguage(Locale.US);
                }
            }
        });
        ttsUK=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    ttsUK.setLanguage(Locale.UK);
                }
            }
        });
        speakUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ttsUS.isSpeaking()) ttsUS.stop();
                if(ttsUK.isSpeaking()) ttsUK.stop();
                ttsUS.speak(text.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        speakUK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ttsUS.isSpeaking()) ttsUS.stop();
                if(ttsUK.isSpeaking()) ttsUK.stop();
                ttsUK.speak(text.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        if(ttsUS!=null){
            ttsUS.stop();
            ttsUS.shutdown();
        }
        if(ttsUK!=null){
            ttsUK.stop();
            ttsUK.shutdown();
        }
    }
}
