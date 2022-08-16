package com.cubes.komentarapp.ui.main.menu;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenu;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuCategories;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuHead;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuItems;
import com.cubes.komentarapp.ui.main.menu.item.RvItemMenuSocialNetwork;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private final ArrayList<RvItemMenu> items = new ArrayList<>();

    public MenuAdapter() {
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 2:
                binding = RvItemMenuItemsBinding.inflate(inflater, parent, false);
                break;
            case 3:
                binding = RvItemMenuSocialNetworkBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemMenuCategoryBinding.inflate(inflater, parent, false);
        }
        return new MenuHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, @SuppressLint("RecyclerView") int position) {
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

    public class MenuHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public MenuHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
