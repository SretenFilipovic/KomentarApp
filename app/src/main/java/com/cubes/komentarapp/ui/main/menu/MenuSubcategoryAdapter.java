package com.cubes.komentarapp.ui.main.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.RvItemMenuSubcategoryBinding;
import com.cubes.komentarapp.ui.subcategory.SubcategoryActivity;

import java.util.ArrayList;

public class MenuSubcategoryAdapter extends RecyclerView.Adapter<MenuSubcategoryAdapter.SubcategoryHolder> {

    private ArrayList<Category> subcategoryList;
    private Context context;

    public MenuSubcategoryAdapter(Context context, ArrayList<Category> subcategoryList) {
        this.subcategoryList = subcategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemMenuSubcategoryBinding binding =
                RvItemMenuSubcategoryBinding.inflate(LayoutInflater.from(context), parent,false);
        return new SubcategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryHolder holder, int position) {
        Category subcategory = subcategoryList.get(position);

        holder.binding.textViewSubcategory.setText(subcategory.name);

        holder.binding.textViewSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SubcategoryActivity.class);
                i.putExtra("categoryId", subcategory.id);
                i.putExtra("categoryName", subcategory.name);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }


    public class SubcategoryHolder extends RecyclerView.ViewHolder{

        private RvItemMenuSubcategoryBinding binding;

        public SubcategoryHolder(RvItemMenuSubcategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
