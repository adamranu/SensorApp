package com.example.sensorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private SensorManager sensorManager;
    private TextView sensorAccelerometerText;
    private TextView sensorProximityText;
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText                  = findViewById(R.id.sensor_list);
        sensorAccelerometerText     = findViewById(R.id.sensor_accelerometer);
        sensorProximityText         = findViewById(R.id.sensor_proximity);
        sensorManager               = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorTx = new StringBuilder();
        for (Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+" "+sensor.getVendor()+"\n");
        }
        sensorText.setText(sensorTx.toString());

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerometer == null){
            Toast.makeText(this, "No Accelerometer", Toast.LENGTH_LONG).show();
        }
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensorProximity==null){
            Toast.makeText(this, "No Proximity", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerometer == null){
            sensorManager.registerListener(this.sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorProximity == null){
            sensorManager.registerListener(this.sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        SensorAdditionalInfo sensorEvent = null;
        int sensorType=sensorEvent.sensor.getType();
        float value = sensorEvent.value[0];
        if (sensorType==Sensor.TYPE_ACCELEROMETER){
            sensorAccelerometerText.setText(String.format("Accelerator Sensor : ", value));

        }
        if (sensorType==Sensor.TYPE_PROXIMITY){
            sensorProximityText.setText(String.format("Proximity Sensor : ", value));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}