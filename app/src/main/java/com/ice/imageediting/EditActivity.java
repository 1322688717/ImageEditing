package com.ice.imageediting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yalantis.ucrop.UCrop;

import java.io.File;

public class EditActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageUri;

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


    private void rotateImage() {
        // 这里可以添加旋转图片的代码
        // 你可以考虑使用 Matrix 来旋转 Bitmap
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
