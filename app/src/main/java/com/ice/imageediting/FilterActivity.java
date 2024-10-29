package com.ice.imageediting;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ice.imageediting.adapter.FilterAdapter;

import java.util.Arrays;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView filterRecyclerView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        imageView = findViewById(R.id.imageView);
        filterRecyclerView = findViewById(R.id.filterRecyclerView);

        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        imageView.setImageURI(imageUri);

        // 配置滤镜RecyclerView适配器
        List<String> filters = Arrays.asList("黑白", "复古", "冷色调", "暖色调", "默认");
        FilterAdapter filterAdapter = new FilterAdapter(filters, this::applyFilter);
        filterRecyclerView.setAdapter(filterAdapter);

    }

    private void applyFilter(String filterName) {
        // 根据滤镜名称应用不同滤镜效果
    }
}
