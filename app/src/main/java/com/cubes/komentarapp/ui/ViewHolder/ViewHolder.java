package com.cubes.komentarapp.ui.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewBinding binding;

    public ViewHolder(@NonNull ViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
