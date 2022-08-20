package com.cubes.komentarapp.ui.main.menu;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.api.CategoryApi;
import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenu;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuCategories;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuHead;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuItems;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuSocialNetwork;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItemMenu> items = new ArrayList<>();

    public MenuAdapter() {
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.layout.rv_item_menu_items:
                binding = RvItemMenuItemsBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_menu_social_network:
                binding = RvItemMenuSocialNetworkBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemMenuCategoryBinding.inflate(inflater, parent, false);
        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }

    public void setData(ArrayList<Category> list, MenuListener listener) {
        this.items.add(new RvItemMenuHead());
        for (Category category : list) {
            this.items.add(new RvItemMenuCategories(category, listener, list.indexOf(category)));
        }
        this.items.add(new RvItemMenuItems(listener));
        this.items.add(new RvItemMenuSocialNetwork(listener));

        notifyDataSetChanged();
    }

}
