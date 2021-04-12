package com.example.chronometer;

import android.content.Context;
import android.os.SystemClock;

public class Chronometer implements Runnable{
    public  static final long MILIS_TO_MINUTES=60000;
    public static final long MILIS_TO_HOURS=3600000;
    private long mBase;
    private Context mContext;
    private long mStartTime;

    private boolean mIsRunning;
    private boolean mWasRunning;

    public Chronometer(Context mContext) {
        this.mContext = mContext;
    }

    public void start(){
        mStartTime=SystemClock.elapsedRealtime();
        mIsRunning=true;

    }

    public void pause(){
        mWasRunning=mIsRunning;
        mIsRunning=false;
    }

    public void resume(){
        if (mWasRunning){
            mIsRunning=true;
        }
    }


    @Override
    public void run() {

        while (mIsRunning){
            long since=SystemClock.elapsedRealtime()-mStartTime;
            int seconds=(int) ((since/1000)%60);

            int minutes=(int) ((since / MILIS_TO_MINUTES)%60);
            int hours=(int) ((since/MILIS_TO_HOURS)%24);
            int milis=(int) ((since%1000));

            ((MainActivity)mContext).updateTimerText(String.format("%02d:%02d:%02d.%03d",hours,minutes,seconds,milis));
        }

    }
}
