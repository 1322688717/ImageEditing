package com.ice.imageediting.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ice.imageediting.R;

public class TextStickerActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText textInput;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sticker);

        imageView = findViewById(R.id.imageView);
        textInput = findViewById(R.id.textInput);

        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        imageView.setImageURI(imageUri);

        textInput.setOnEditorActionListener((v, actionId, event) -> {
            String text = textInput.getText().toString();
            addTextSticker(text);
            return true;
        });
    }

    private void addTextSticker(String text) {
        // 在图片上添加文字贴图
    }
}
