package com.example.zackakil.waterboy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by zackakil on 24/06/2018.
 */


public class WaterMonitor extends TimerTask {

    private int someVal = 99;
    private BluetoothConnector bt;
    private NotificationManagerCompat nm;
    private NotificationCompat.Builder nb;

    public WaterMonitor(NotificationManagerCompat nm, NotificationCompat.Builder nb) {

        this.nm = nm;
        this.nb = nb;
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

        if(level < 150){
            Log.e("My App", "try to send notification");
            nb.setContentText("Water is low!");
            nm.notify(new Random().nextInt(), nb.build());
        }

        Log.e("My App", "Hello again " + String.valueOf(level));

    }

    private void sendNotification(){


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