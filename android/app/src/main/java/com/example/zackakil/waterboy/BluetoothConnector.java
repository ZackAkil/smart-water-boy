package com.example.zackakil.waterboy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zackakil on 24/06/2018.
 */

public class BluetoothConnector {


    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    private BluetoothSocket soc = null;


    public BluetoothConnector(String device_name) {
        super();

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
                if(device.getName().equals(device_name)) {
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

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
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

    public void ping(){
        if (this.soc.isConnected()){

            try {
                OutputStream stream = this.soc.getOutputStream();

                stream.write('0');
                Log.e("My App", "Sent down bluetooth");

            }catch (IOException e){
                Log.e("My App", "IO exception!",e);
            }

        }else{
            Log.e("My App", "Not connected");
        }

    }

    public int readInt(){
        if (this.soc.isConnected()){

            try {
                InputStream stream = this.soc.getInputStream();
                int output = 0;
                while(stream.available() > 0) {
                    output = stream.read();
                    Log.e("My App", "read "+ String.valueOf(output));
                }
                return output;

            }catch (IOException e){
                Log.e("My App", "IO exception!",e);
            }

        }else{
            Log.e("My App", "Not connected");
        }
        return 0;
    }
}
