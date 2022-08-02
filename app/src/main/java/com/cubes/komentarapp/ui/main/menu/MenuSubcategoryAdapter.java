package com.cubes.komentarapp.ui.main.menu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.RvItemMenuSubcategoryBinding;
import com.cubes.komentarapp.ui.subcategory.SubcategoryActivity;

import java.util.ArrayList;

public class MenuSubcategoryAdapter extends RecyclerView.Adapter<MenuSubcategoryAdapter.SubcategoryHolder> {

    private final ArrayList<Category> subcategoryList;

    public MenuSubcategoryAdapter(ArrayList<Category> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    @NonNull
    @Override
    public SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding =
                RvItemMenuSubcategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubcategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryHolder holder, int position) {
        Category subcategory = subcategoryList.get(position);

        RvItemMenuSubcategoryBinding binding = (RvItemMenuSubcategoryBinding) holder.binding;

        binding.textViewSubcategory.setText(subcategory.name);

        binding.textViewSubcategory.setOnClickListener(view -> {
            Intent i = new Intent(holder.itemView.getContext(), SubcategoryActivity.class);
            i.putExtra("categoryId", subcategory.id);
            i.putExtra("categoryName", subcategory.name);
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }


    public class SubcategoryHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public SubcategoryHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
