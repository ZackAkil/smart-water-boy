package com.example.zackakil.waterboy;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.TimerTask;

/**
 * Created by zackakil on 24/06/2018.
 */


public class WaterMonitor extends TimerTask {

    private int someVal = 99;
    private BluetoothConnector bt;

    public WaterMonitor() {

        bt = new BluetoothConnector("HC-06");

    }

    @Override
    public void run() {

        bt.ping();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("My App", "a delay didnt work i guess");
        }

        int level = bt.readInt();

        Log.e("My App", "Hello again " + String.valueOf(level));


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