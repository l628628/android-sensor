package com.example.android_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {
    TextView textView3,textView4;
    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyrListener;
    Chronometer cm;
    float timestamp;
    final float[] rotationangle = new float[3];
    final float NS2S = 1.0f / 1000000000.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        for(int i=0;i<3;++i)
        {
            rotationangle[i] = 0;
        }
        cm = (Chronometer)findViewById(R.id.chcm);
        cm.setBase(SystemClock.elapsedRealtime());
        cm.start();
        cm.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                if(SystemClock.elapsedRealtime()-cm.getBase()>=1000)
                {
                    for(int i=0;i<3;++i)
                    {
                        rotationangle[i] = 0;
                    }
                    cm.stop();
                }
            }
        });
    }


    @Override
    protected void onResume(){
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyrListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
                    if(event.timestamp!=0){
                        //抓取陀螺仪数据
                        float axisX = event.values[0];
                        float axisY = event.values[1];
                        float axisZ = event.values[2];
                        float omegaMagnitude = (float)sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
                        textView3.setText(String.valueOf(omegaMagnitude));

                        //计算旋转过的角度
                        float dT = (event.timestamp - timestamp) * NS2S;
                        for(int i=0;i<3;++i)
                        {
                            rotationangle[i] += event.values[i] * dT;
                        }
                        float r0 = rotationangle[0];
                        float r1 = rotationangle[1];
                        float r2 = rotationangle[2];
                        float theta = (float)sqrt(r0*r0 + r1*r1 + r2*r2);
                        textView4.setText(String.valueOf(Math.toDegrees(theta)));
                    }
                    timestamp = event.timestamp;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(gyrListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
        for(int i=0;i<3;++i)
        {
            rotationangle[i] = 0;
        }

    }

    public void onClick(View v){
        for(int i=0;i<3;++i)
        {
            rotationangle[i] = 0;
        }
    }
}