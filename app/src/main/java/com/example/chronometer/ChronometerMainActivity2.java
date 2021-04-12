package com.example.chronometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChronometerMainActivity2 extends AppCompatActivity {

    private Chronometer chronometer;

    FloatingActionButton fabstart, fabreset, fablap;
    private boolean running;
    long pauseOffset;
    Handler handler;
    boolean flag = true;
    int lap = 1;

    Date calendar;
    String Time;


    TextView lap_text;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_main2);
        fabstart = findViewById(R.id.fab_start);
        fablap = findViewById(R.id.fab_lap);
        fabreset = findViewById(R.id.fab_reset);

        calendar=Calendar.getInstance().getTime();
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));

        chronometer = findViewById(R.id.chronometer_view);
        // btstart = findViewById(R.id.play_btn);


        lap_text = findViewById(R.id.lap_text);
        img = findViewById(R.id.img);

        handler = new Handler();

        chronometer.setFormat("00:%s");
        fabstart.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));

        fabstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag) {
                    fabstart.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_pause_24));

                    flag = false;
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    lap = 1;

                } else {

                    fabstart.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
                    flag = true;
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;

                }
            }
        });

        fablap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running==true) {
                    String time = chronometer.getText().toString();
                    img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_outlined_flag_24));
                    lap_text.append("" + String.valueOf(lap) + "    " + time + "      "+currentTime+"\n");
                    lap++;
                }
            }
        });

        fabreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();
                lap_text.setText("");
                lap = 1;
                fabstart.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_play_arrow_24));
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer c) {
                long elapsedMillis = SystemClock.elapsedRealtime() - c.getBase();
                if (elapsedMillis > 3600000L) {
                    c.setFormat("0%s");
                } else {
                    c.setFormat("00:%s");
                }
            }
        });


    }


}