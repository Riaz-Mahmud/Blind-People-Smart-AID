package org.tensorflow.lite.backdoor.it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CardView currencyDetectBtn,colorSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyDetectBtn = findViewById(R.id.currencyDetectBtn);
        colorSelectBtn = findViewById(R.id.colorSelectBtn);

        currencyDetectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClassifierActivity.class)));
        colorSelectBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ColorPaletteActivity.class)));
    }
}