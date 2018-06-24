package com.example.zackakil.waterboy;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.TimerTask;

/**
 * Created by zackakil on 24/06/2018.
 */


public class WaterMonitor extends TimerTask {

    private int someVal = 99;

    public WaterMonitor() {

    }

    @Override
    public void run() {
        someVal++;
        Log.e("My App", "Hello again " + String.valueOf(someVal));
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public long scheduledExecutionTime() {
        return super.scheduledExecutionTime();
    }

}