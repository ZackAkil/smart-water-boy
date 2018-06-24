package com.example.zackakil.waterboy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.util.UUID;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;







public class MainActivity extends AppCompatActivity {


    ScheduledFuture<?> scheduledFuture = null;
    private int notificationThresh = 10;
    private ImageView bottleImageView;
    private BluetoothConnector bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottleImageView = findViewById(R.id.bottleImageView);

        String isOld = getIntent().getStringExtra("future");

        isOld = (isOld == null)? "yo" : isOld;

        Log.e("My App", isOld);

        if ( !isOld.equals("im old")){
            Log.e("My App", "NOTIFICATIONS AHOI!");



            NotificationChannel channel =  createNotificationChannel();
    //        InitaliseBluetooth("HC-06");


            ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();

            WaterMonitor wm = new WaterMonitor();
            scheduledFuture = scheduler.scheduleAtFixedRate(wm, 0, 5, TimeUnit.SECONDS);



            Intent notificationIntent = new Intent(this, MainActivity.class);

            notificationIntent.putExtra("future", "im old");

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "water_boy")
                    .setSmallIcon(R.drawable.ic_stat_opacity)
                    .setContentTitle("Water Boy")
                    .setContentText("That's some high quality H2O!")
                    .setChannelId(channel.getId())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(intent);



            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(42, mBuilder.build());


//            bt = new BluetoothConnector("HC-06");

        }
    }




    public void btnPress(View v){
//        notificationThresh++;
//        Log.e("My App", "btn press");
        bt.ping();
    }

    public void btnPress2(View v){
//        Log.e("My App", "btn press");
        bt.ping();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int level = bt.readInt();
                Log.e("My App", "Hello again " + String.valueOf(level));

                if(level < 60){
                    bottleImageView.setImageResource(R.drawable.low);
                }else if (level < 160){
                    bottleImageView.setImageResource(R.drawable.mid);
                }else{
                    bottleImageView.setImageResource(R.drawable.high);
                }

            }
        }, 100);



    }







    private NotificationChannel createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("boobs", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            return channel;
        }
        return null;
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        Log.e("My App", "GOODBYE");
        super.onStop();
//        closeBluetooth();

    }

    @Override
    protected void onDestroy() {
        // call the superclass method first
        Log.e("My App", "GOODBYE FOREVER");
        super.onDestroy();
//        closeBluetooth();

    }





}