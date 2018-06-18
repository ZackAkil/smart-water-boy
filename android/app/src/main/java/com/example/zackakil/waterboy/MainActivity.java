package com.example.zackakil.waterboy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.util.UUID;

import android.util.Log;

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

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    private BluetoothSocket soc = null;
    ScheduledFuture<?> scheduledFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String isOld = getIntent().getStringExtra("future");

        isOld = (isOld == null)? "yo" : isOld;

        Log.e("My App", isOld);

        if ( !isOld.equals("im old")){
            Log.e("My App", "NOTIFICATIONS AHOI!");



            createNotificationChannel();
    //        InitaliseBluetooth("HC-06");


            ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();

            scheduledFuture = scheduler.scheduleAtFixedRate(
                    new Runnable() {
                        public void run() {
                            // call service
                            Log.e("My App", "Hello again");
                        }
                    }, 0, 1, TimeUnit.SECONDS);


            Intent notificationIntent = new Intent(this, MainActivity.class);

            notificationIntent.putExtra("future", "im old");

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "water_boy")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Water Boy")
                    .setContentText("That's some high quality H2O!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(intent);



            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(42, mBuilder.build());

        }
    }

    private void createNotificationChannel() {
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
        }
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

    private  void InitaliseBluetooth(String btDeviceName){

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.e("My App", "Bluetooth not found");
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d("My App", deviceName);
                Log.d("My App", deviceHardwareAddress);
                if(device.getName().equals(btDeviceName)) {
                    Log.e("My App", "Found device");
                    try{
                        soc = createBluetoothSocket(device);
                        soc.connect();
                    } catch (IOException e) {
                        Log.e("My App", "Socket creation failed");
                    }

                }
            }

        }
    }



    public int GetWaterLevel(){

        return 0;
    }


    public BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
        } catch (Exception e) {
            Log.e("My App", "Could not create Insecure RFComm Connection", e);
        }
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }


    public void closeBluetooth() {
        try {
            this.soc.close();

        } catch (IOException e) {
            Log.e("My App", "Socket creation failed", e);
        }


    }


}