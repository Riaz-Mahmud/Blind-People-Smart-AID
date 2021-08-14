package org.tensorflow.lite.backdoor.it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import org.tensorflow.lite.backdoor.it.ObjectDetect.DetectorActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView currencyDetectBtn = findViewById(R.id.currencyDetectBtn);
        CardView colorSelectBtn = findViewById(R.id.colorSelectBtn);
        CardView incomingObjectBtn = findViewById(R.id.incomingObjectCard);
        CardView objectDetectBtn = findViewById(R.id.objectDetectBtn);

        currencyDetectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClassifierActivity.class)));
        colorSelectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ColorPaletteActivity.class)));
        incomingObjectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, IncomingObjectActivity.class)));
        objectDetectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DetectorActivity.class)));
    }
}