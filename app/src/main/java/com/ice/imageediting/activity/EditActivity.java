package com.ice.imageediting.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ice.imageediting.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class EditActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageUri;
    private Bitmap originalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        imageView = findViewById(R.id.imageView);
        String uriString = getIntent().getStringExtra("imageUri");
        imageUri = Uri.parse(uriString);
        imageView.setImageURI(imageUri);

        Button cropButton = findViewById(R.id.cropButton);
        cropButton.setOnClickListener(v -> cropImage());

        Button rotateButton = findViewById(R.id.rotateButton);
        rotateButton.setOnClickListener(v -> rotateImage());

        // 在这里初始化 originalBitmap
        imageView.post(() -> {
            imageView.setDrawingCacheEnabled(true);
            originalBitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);
        });

        // 亮度和对比度调整的示例
        SeekBar brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
        brightnessSeekBar.setMax(200); // 设置最大值
        brightnessSeekBar.setProgress(100); // 设置初始进度为中间位置
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustBrightness(progress - 100); // 假设 SeekBar 范围是 0-200，0 为原始亮度
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar contrastSeekBar = findViewById(R.id.contrastSeekBar);
        contrastSeekBar.setMax(200); // 设置最大值
        contrastSeekBar.setProgress(100); // 设置初始进度为中间位置
        contrastSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustContrast(progress / 100.0f); // 假设 SeekBar 范围是 0-200，1 为原始对比度
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar saturationSeekBar = findViewById(R.id.saturationSeekBar);
        saturationSeekBar.setMax(200);
        saturationSeekBar.setProgress(100);
        saturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustSaturation(progress / 100.0f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar temperatureSeekBar = findViewById(R.id.temperatureSeekBar);
        temperatureSeekBar.setMax(200);
        temperatureSeekBar.setProgress(100);
        temperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustTemperature(progress - 100);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 模糊和锐化效果的 SeekBar
        SeekBar blurSeekBar = findViewById(R.id.blurSeekBar);
        blurSeekBar.setMax(100);
        blurSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                applyBlurEffect(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar sharpnessSeekBar = findViewById(R.id.sharpnessSeekBar);
        sharpnessSeekBar.setMax(200);
        sharpnessSeekBar.setProgress(100);
        sharpnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                applySharpnessEffect(progress / 100.0f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void cropImage() {
        if (imageUri != null) {
            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));
            UCrop.of(imageUri, destinationUri)
                    .withAspectRatio(16, 9) // 设置裁剪比例
                    .withMaxResultSize(1080, 1080) // 设置最大结果尺寸
                    .start(this);
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private int currentRotation = 0; // 记录当前旋转的角度

    private void rotateImage() {
        currentRotation = (currentRotation + 90) % 360; // 每次旋转累加90度

        if (originalBitmap != null) {
            // 使用 Matrix 设置旋转角度
            Matrix matrix = new Matrix();
            matrix.postRotate(currentRotation);

            // 基于原始图像创建旋转后的位图
            Bitmap rotatedBitmap = Bitmap.createBitmap(
                    originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true
            );

            imageView.setImageBitmap(rotatedBitmap); // 将旋转后的图像显示到 ImageView
        } else {
            Toast.makeText(this, "No image to rotate", Toast.LENGTH_SHORT).show();
        }
    }





    private void adjustBrightness(int value) {
        // 调整亮度的代码
        // 这里可以使用 ColorMatrix 来调整亮度
        // 创建 ColorMatrix 对象
        ColorMatrix colorMatrix = new ColorMatrix();
        // 调整亮度
        colorMatrix.set(new float[]{
                1, 0, 0, 0, value,  // Red
                0, 1, 0, 0, value,  // Green
                0, 0, 1, 0, value,  // Blue
                0, 0, 0, 1, 0       // Alpha
        });

        // 创建 ColorMatrixColorFilter
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        // 设置 ImageView 的 ColorFilter
        imageView.setColorFilter(filter);
    }

    private void adjustContrast(float value) {
        // 调整对比度的代码
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                value, 0, 0, 0, 0, // Red
                0, value, 0, 0, 0, // Green
                0, 0, value, 0, 0, // Blue
                0, 0, 0, 1, 0      // Alpha
        });

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }

    private void adjustSaturation(float value) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(value);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    private void adjustTemperature(int value) {
        ColorMatrix colorMatrix = new ColorMatrix();
        float red = 1 + (value / 100f);
        float blue = 1 - (value / 100f);
        colorMatrix.setScale(red, 1, blue, 1);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    private void applyBlurEffect(int radius) {
        // Implement blur effect here
    }

    private void applySharpnessEffect(float value) {
        // Implement sharpness effect here
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                imageView.setImageURI(resultUri); // 显示裁剪后的图片
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            // 处理裁剪错误
        }
    }
}
