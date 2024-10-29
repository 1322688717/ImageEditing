package com.ice.imageediting;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class BeautyActivity extends AppCompatActivity {

    private ImageView imageView;
    private SeekBar beautySeekBar;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);

        imageView = findViewById(R.id.imageView);
        beautySeekBar = findViewById(R.id.beautySeekBar);

        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        imageView.setImageURI(imageUri);

        beautySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                applyBeautyEffect(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void applyBeautyEffect(int level) {
        // 应用美颜效果，根据滑块进度调整美颜强度
    }
}
