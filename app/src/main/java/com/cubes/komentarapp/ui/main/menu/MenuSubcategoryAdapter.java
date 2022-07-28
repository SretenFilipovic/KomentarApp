package com.cubes.komentarapp.ui.main.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.ExpandableListItemBinding;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.ui.subcategory.SubcategoryActivity;

import java.util.ArrayList;

// Adapter za podkategorije u Meniju
// setuje se na ugnjezdeni recyclerView u kategorijama u MenuAdapter (expandable_list_group (naziv ostao iz verzije sa ELV))

public class MenuSubcategoryAdapter extends RecyclerView.Adapter<MenuSubcategoryAdapter.SubcategoryHolder> {

    private ArrayList<Category> list;
    private Context context;

    public MenuSubcategoryAdapter(Context context, ArrayList<Category> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExpandableListItemBinding binding =
                ExpandableListItemBinding.inflate(LayoutInflater.from(context), parent,false);
        return new SubcategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryHolder holder, int position) {
        Category subcategory = list.get(position);

        holder.binding.textViewSubcategory.setText(subcategory.name);

        // na klik se otvara aktiviti sa vestima iz te kategorije/podkategorije
        holder.binding.textViewSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SubcategoryActivity.class);
                i.putExtra("category", list.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SubcategoryHolder extends RecyclerView.ViewHolder{

        private ExpandableListItemBinding binding;

        public SubcategoryHolder(ExpandableListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
