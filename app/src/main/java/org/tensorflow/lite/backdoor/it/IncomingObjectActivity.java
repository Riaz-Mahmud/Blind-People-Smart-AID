package org.tensorflow.lite.backdoor.it;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class IncomingObjectActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    private LinearLayout linearLayout;
    private TextView textView;
    MediaPlayer alarm;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_object);

        linearLayout = findViewById(R.id.incomingObjectLayout);
        textView = findViewById(R.id.incomingObjectTxt);

        sensorManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            Toast.makeText(this, "Proximity Sensor is not Available", Toast.LENGTH_SHORT).show();
        } else {
            proximitySensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.values[0] < proximitySensor.getMaximumRange()) {

                        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
                            if (status != TextToSpeech.ERROR) {
                                // To Choose language of speech
                                if (textToSpeech.isSpeaking()) {
                                    textToSpeech.stop();
                                }
                                textToSpeech.setLanguage(Locale.US);
                                textToSpeech.speak("Incoming Object Detect", TextToSpeech.QUEUE_ADD, null);
                            }
                        });

                        if (alarm.isPlaying()) {
                            alarm.seekTo(0);
                        } else {
                            alarm.start();
                        }

                        linearLayout.setBackgroundColor(Color.RED);
                        textView.setText("Incoming Object Detect");
                    } else {
                        if (alarm.isPlaying()) {
                            alarm.pause();
                        }
                        linearLayout.setBackgroundColor(Color.GREEN);
                        textView.setText("No Object Detect");
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        }

//        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alarm.isPlaying()) {
            alarm.pause();
        }
        sensorManager.unregisterListener(proximitySensorListener);
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        alarm = MediaPlayer.create(this, R.raw.beep_beep_alarm);
        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}