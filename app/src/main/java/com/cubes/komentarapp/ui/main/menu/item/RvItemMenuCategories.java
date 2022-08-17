package com.cubes.komentarapp.ui.main.menu.item;

import android.graphics.Color;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.MainActivity;
import com.cubes.komentarapp.ui.main.menu.MenuSubcategoryAdapter;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

public class RvItemMenuCategories implements RvItem {

    private final Category category;
    private final MenuListener listener;
    private final int position;

    public RvItemMenuCategories(Category category, MenuListener listener, int position) {
        this.category = category;
        this.listener = listener;
        this.position = position + 1;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(ViewHolder holder) {
        RvItemMenuCategoryBinding binding = (RvItemMenuCategoryBinding) holder.binding;

        binding.textViewCategory.setText(category.name);
        binding.viewCategoryColor.setBackgroundColor(Color.parseColor(category.color));
        binding.viewSubcategoryColor.setBackgroundColor(Color.parseColor(category.color));

        if (category.subcategories.size() == 0) {
            binding.imageViewExpandSubcategoryList.setVisibility(View.GONE);
        } else {
            binding.imageViewExpandSubcategoryList.setOnClickListener(view -> {

                if (binding.subcategoryContainer.getVisibility() == View.GONE) {
                    binding.subcategoryContainer.setVisibility(View.VISIBLE);
                    binding.imageViewExpandSubcategoryList.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    binding.subcategoryContainer.setVisibility(View.GONE);
                    binding.imageViewExpandSubcategoryList.setImageResource(R.drawable.ic_arrow_down);
                }
            });
        }

        binding.textViewCategory.setOnClickListener(view -> {
            DrawerLayout drawer = ((MainActivity) view.getContext()).findViewById(R.id.drawerLayout);
            ViewPager2 viewPager2 = ((MainActivity) view.getContext()).findViewById(R.id.viewPagerHome);
            drawer.closeDrawer(((MainActivity) view.getContext()).findViewById(R.id.drawerNavigationView));
            viewPager2.setCurrentItem(position);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        MenuSubcategoryAdapter adapter = new MenuSubcategoryAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setData(category.subcategories, listener);
    }
}
