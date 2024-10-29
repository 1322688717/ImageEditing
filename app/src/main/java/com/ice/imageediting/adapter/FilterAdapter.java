package com.ice.imageediting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ice.imageediting.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private final List<String> filters;
    private final OnFilterClickListener onFilterClickListener;

    public interface OnFilterClickListener {
        void onFilterClick(String filterName);
    }

    public FilterAdapter(List<String> filters, OnFilterClickListener listener) {
        this.filters = filters;
        this.onFilterClickListener = listener;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        String filterName = filters.get(position);
        holder.filterNameTextView.setText(filterName);

        // 设置滤镜预览图标
        holder.filterPreviewImageView.setImageResource(getFilterPreviewImage(filterName));

        holder.itemView.setOnClickListener(v -> onFilterClickListener.onFilterClick(filterName));
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    private int getFilterPreviewImage(String filterName) {
        // 根据 filterName 返回相应的预览图片资源 ID
        switch (filterName) {
            case "黑白":
                return R.drawable.logo;
            case "复古":
                return R.drawable.logo;
            case "冷色调":
                return R.drawable.logo;
            case "暖色调":
                return R.drawable.logo;
            default:
                return R.drawable.logo;
        }
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView filterPreviewImageView;
        TextView filterNameTextView;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filterPreviewImageView = itemView.findViewById(R.id.filterPreviewImageView);
            filterNameTextView = itemView.findViewById(R.id.filterNameTextView);
        }
    }
}
