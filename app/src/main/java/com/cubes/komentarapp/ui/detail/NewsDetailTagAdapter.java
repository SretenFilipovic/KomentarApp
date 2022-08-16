package com.cubes.komentarapp.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.Tags;
import com.cubes.komentarapp.databinding.RvItemTagBinding;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

import java.util.ArrayList;

public class NewsDetailTagAdapter extends RecyclerView.Adapter<NewsDetailTagAdapter.TagHolder> {

    private ArrayList<Tags> tagList;
    private NewsDetailListener listener;

    public NewsDetailTagAdapter() {
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemTagBinding.inflate(inflater, parent, false);
        return new NewsDetailTagAdapter.TagHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tags tag = tagList.get(position);

        RvItemTagBinding binding = (RvItemTagBinding) holder.binding;
        binding.buttonTag.setText(tag.title);
        binding.buttonTag.setOnClickListener(view -> listener.onTagClicked(tag.id));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public void setData(ArrayList<Tags> tagList, NewsDetailListener listener) {
        this.tagList = tagList;
        this.listener = listener;
    }

    public class TagHolder extends RecyclerView.ViewHolder {
        public ViewBinding binding;

        public TagHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
