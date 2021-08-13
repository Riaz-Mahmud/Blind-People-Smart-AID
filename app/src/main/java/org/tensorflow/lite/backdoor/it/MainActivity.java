package org.tensorflow.lite.backdoor.it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CardView currencyDetectBtn,colorSelectBtn,incomingObjectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyDetectBtn = findViewById(R.id.currencyDetectBtn);
        colorSelectBtn = findViewById(R.id.colorSelectBtn);
        incomingObjectBtn = findViewById(R.id.incomingObjectCard);

        currencyDetectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClassifierActivity.class)));
        colorSelectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ColorPaletteActivity.class)));
        incomingObjectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, IncomingObjectActivity.class)));
    }
}