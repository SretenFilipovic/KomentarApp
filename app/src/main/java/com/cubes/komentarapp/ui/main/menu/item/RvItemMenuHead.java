package com.cubes.komentarapp.ui.main.menu.item;

import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RvItemMenuHead implements RvItem {

    public RvItemMenuHead() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_menu_category;
    }

    @Override
    public void bind(ViewHolder holder) {
        RvItemMenuCategoryBinding binding = (RvItemMenuCategoryBinding) holder.binding;

        binding.imageViewExpandSubcategoryList.setVisibility(View.GONE);
        binding.textViewCategory.setText(R.string.text_naslovna);

        binding.textViewCategory.setOnClickListener(view -> {
            DrawerLayout drawer = ((MainActivity) holder.itemView.getContext()).findViewById(R.id.drawerLayout);
            BottomNavigationView bottomNavigationView = ((MainActivity) holder.itemView.getContext()).findViewById(R.id.bottomNavigationView);
            drawer.closeDrawer(((MainActivity) holder.itemView.getContext()).findViewById(R.id.drawerNavigationView));
            bottomNavigationView.setSelectedItemId(R.id.home);
        });
    }
}