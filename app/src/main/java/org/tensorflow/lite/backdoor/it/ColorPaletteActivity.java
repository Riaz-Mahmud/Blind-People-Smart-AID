package org.tensorflow.lite.backdoor.it;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.Locale;

public class ColorPaletteActivity extends AppCompatActivity {

    private TextView vibrantView;
    private TextView vibrantLightView;
    private TextView vibrantDarkView;
    private TextView mutedView;
    private TextView mutedLightView;
    private TextView mutedDarkView;
    ImageView imageWallpaper, colorDisplay;
    private Bitmap bitmap;
    private TextView rgbColorTxt, hexColorTxt,colorNameTxt;
    public String rgbColor, hexColor;
    private Button openCameraBtn;

    String[] colorCodeItem, colorNameItem;
    TextToSpeech textToSpeech;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_palette);

        initViews();
        CheckAndroidVersion();

        colorCodeItem = getResources().getStringArray(R.array.colorCode);
        colorNameItem = getResources().getStringArray(R.array.colorName);


        imageWallpaper.setOnTouchListener((v, event) -> {
            imageTouch(event);
            return false;
        });

        openCameraBtn.setOnClickListener(v -> CheckAndroidVersion());
    }

    private void imageTouch(MotionEvent event) {
        try {
            final int action = event.getAction();
            final int evX = (int) event.getX();
            final int evY = (int) event.getY();

            int touchColor = getColor(imageWallpaper, evX, evY);

            int r = (touchColor >> 16) & 0xFF;
            int g = (touchColor >> 8) & 0xFF;
            int b = (touchColor >> 0) & 0xFF;

            rgbColor = String.valueOf(r) + "," + String.valueOf(g) + "," + String.valueOf(b);
            rgbColorTxt.setText("RGB:  " + rgbColor);

            hexColor = Integer.toHexString(touchColor);
            if (hexColor.length() > 2) {
                hexColor = hexColor.substring(2, hexColor.length());
                colorDisplay.setBackgroundColor(touchColor);
                hexColorTxt.setText("HEX:  #" + hexColor);

                colorNameTxt.setText("Name: Unknown");

                for (int i = 0; i < colorCodeItem.length; i++) {
                    if (colorCodeItem[i].equals(hexColor.toUpperCase())) {
                        colorNameTxt.setText("Name: " + colorNameItem[i]);
                        String colorName = colorNameItem[i];

                        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
                            if(status!=TextToSpeech.ERROR){
                                // To Choose language of speech
                                if (textToSpeech.isSpeaking()){
                                    textToSpeech.stop();
                                }
                                textToSpeech.setLanguage(Locale.US);
                                textToSpeech.speak(colorName,TextToSpeech.QUEUE_ADD,null);
                            }
                        });
                    }
                }
            }

        } catch (Exception e) {
            Log.d("ImageColor", "Error: " + e);
        }
    }

    private int getColor(ImageView image, int evX, int evY) {
        image.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = Bitmap.createBitmap(image.getDrawingCache());
        image.setDrawingCacheEnabled(true);
        return bitmap1.getPixel(evX, evY);
    }

    private void initViews() {
        vibrantView = (TextView) findViewById(R.id.vibrantView);
        vibrantLightView = (TextView) findViewById(R.id.vibrantLightView);
        vibrantDarkView = (TextView) findViewById(R.id.vibrantDarkView);
        mutedView = (TextView) findViewById(R.id.mutedView);
        mutedLightView = (TextView) findViewById(R.id.mutedLightView);
        mutedDarkView = (TextView) findViewById(R.id.mutedDarkView);
        imageWallpaper = findViewById(R.id.imageWallpaper);
        rgbColorTxt = findViewById(R.id.rgbColorTxt);
        hexColorTxt = findViewById(R.id.hexColorTxt);
        colorDisplay = findViewById(R.id.colorDisplay);
        colorNameTxt = findViewById(R.id.colorNameTxt);
        openCameraBtn = findViewById(R.id.openCameraBtn);

    }

    private void CheckAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pickImageDirect();
        } else {
            if (ContextCompat.checkSelfPermission(ColorPaletteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ColorPaletteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ColorPaletteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);

            } else {
                pickImageDirect();
            }
        }
    }

    private void pickImageDirect() {
        ImagePicker.with(ColorPaletteActivity.this)
//                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            assert data != null;
            Uri uri = data.getData();
            Log.d("ImageUrl", "ProfileImage: " + uri);

            if (uri != null && !uri.equals(Uri.EMPTY)) {
                imageWallpaper.setImageURI(uri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    paintTextBackground();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void paintTextBackground() {

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //work with the palette here
                int defaultValue = 0x000000;
                int vibrant = palette.getVibrantColor(defaultValue);
                int vibrantLight = palette.getLightVibrantColor(defaultValue);
                int vibrantDark = palette.getDarkVibrantColor(defaultValue);
                int muted = palette.getMutedColor(defaultValue);
                int mutedLight = palette.getLightMutedColor(defaultValue);
                int mutedDark = palette.getDarkMutedColor(defaultValue);

                vibrantView.setBackgroundColor(vibrant);
                vibrantLightView.setBackgroundColor(vibrantLight);
                vibrantDarkView.setBackgroundColor(vibrantDark);
                mutedView.setBackgroundColor(muted);
                mutedLightView.setBackgroundColor(mutedLight);
                mutedDarkView.setBackgroundColor(mutedDark);
            }
        });


    }
}