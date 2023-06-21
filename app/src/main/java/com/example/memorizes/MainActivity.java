package com.example.memorizes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static Activity _MainActivity;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _MainActivity = MainActivity.this;
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.d("TTsError", "지원하지 않는 언어입니다.");
                    }
                }
            }
        });

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(view->{
            tts.speak("Apple", TextToSpeech.QUEUE_ADD, null);
        });
    }


}