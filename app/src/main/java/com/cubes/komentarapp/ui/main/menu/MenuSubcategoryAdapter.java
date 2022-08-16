package com.cubes.komentarapp.ui.main.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.RvItemMenuSubcategoryBinding;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

import java.util.ArrayList;

public class MenuSubcategoryAdapter extends RecyclerView.Adapter<MenuSubcategoryAdapter.SubcategoryHolder> {

    private ArrayList<Category> subcategoryList;
    private MenuListener listener;

    public MenuSubcategoryAdapter() {
    }

    @NonNull
    @Override
    public SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemMenuSubcategoryBinding.inflate(inflater, parent, false);
        return new SubcategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryHolder holder, int position) {
        Category subcategory = subcategoryList.get(position);

        RvItemMenuSubcategoryBinding binding = (RvItemMenuSubcategoryBinding) holder.binding;

        binding.textViewSubcategory.setText(subcategory.name);

        binding.textViewSubcategory.setOnClickListener(view -> {
            listener.onSubcategoryClicked(subcategory);
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }

    public void setData(ArrayList<Category> subcategoryList, MenuListener listener){
        this.subcategoryList = subcategoryList;
        this.listener = listener;
    }

    public class SubcategoryHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public SubcategoryHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
