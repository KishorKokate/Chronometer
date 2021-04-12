package com.example.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btn_start, btn_lap, btn_reset;
    TextView editText_lap;
    ImageView imageView;
    private Chronometer chronometer;
    private Thread threadChrono;

    boolean wasrunning;


    int lap = 1;
    boolean running;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.img_lap);

        editText_lap = findViewById(R.id.lap_text);
        btn_start = findViewById(R.id.start_btn);
        btn_lap = findViewById(R.id.lap_btn);

        editText_lap.setEnabled(false);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });


        btn_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chronometer != null) {
                    editText_lap.append("Lap  " + String.valueOf(lap) + "    " + String.valueOf(textView.getText()) + "\n");
                    lap++;
                } else {

                    editText_lap.setText("");
                    updateTimerText("00:00:00:000");
                    btn_lap.setVisibility(View.INVISIBLE);
                    btn_start.setText("START");

                }


            }

        });


    }

    private void start() {
        if (chronometer == null) {
            chronometer = new Chronometer(context);
            threadChrono = new Thread(chronometer);
            threadChrono.start();
            running = true;
            chronometer.start();


            lap = 1;
            editText_lap.setText("");
            btn_lap.setVisibility(View.VISIBLE);
            btn_start.setText("Pause");

            btn_lap.setText("LAP");
        } else {
            if (chronometer != null) {
                chronometer.pause();
                btn_start.setText("Resume");
                threadChrono.interrupt();
                threadChrono = null;
                running = false;
                chronometer = null;


                btn_lap.setText("RESET");

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running = false;
    }

    public void updateTimerText(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(time);
            }
        });
    }
}



      /*  btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText_lap.setText("");
                updateTimerText("00:00:00:000");
                btn_reset.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.VISIBLE);
            }
        });*/