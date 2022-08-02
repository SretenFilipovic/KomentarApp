package com.cubes.komentarapp.ui.main.menu;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.ui.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private ArrayList<Category> list = new ArrayList<>();

    public MenuAdapter() {
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        if (viewType == 0) {
            binding = RvItemMenuCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else if (viewType == 1) {
            binding = RvItemMenuItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            binding = RvItemMenuSocialNetworkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        return new MenuHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position == 0) {

            RvItemMenuCategoryBinding binding = (RvItemMenuCategoryBinding) holder.binding;

            binding.imageViewExpandSubcategoryList.setVisibility(View.GONE);
            binding.textViewCategory.setText(R.string.text_naslovna);

            binding.textViewCategory.setOnClickListener(view -> {
                DrawerLayout drawer = ((MainActivity) holder.itemView.getContext()).findViewById(R.id.drawerLayout);
                BottomNavigationView bottomNavigationView = ((MainActivity) holder.itemView.getContext()).findViewById(R.id.bottomNavigationView);
                drawer.closeDrawer(((MainActivity) holder.itemView.getContext()).findViewById(R.id.drawerNavigationView));
                bottomNavigationView.setSelectedItemId(R.id.home);
            });
        } else if (position == list.size() + 1) {
            RvItemMenuItemsBinding binding = (RvItemMenuItemsBinding) holder.binding;

            binding.textViewContact.setOnClickListener(view -> openWebBrowser(holder.itemView.getContext(), "https://komentar.rs"));
            binding.textViewTermsAndConditions.setOnClickListener(view -> openWebBrowser(holder.itemView.getContext(), "https://komentar.rs"));
            binding.textViewPushNotification.setOnClickListener(view -> openWebBrowser(holder.itemView.getContext(), "https://komentar.rs"));
            binding.textViewMarketing.setOnClickListener(view -> openWebBrowser(holder.itemView.getContext(), "https://komentar.rs"));
            binding.textViewCurrency.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), CurrencyActivity.class);
                holder.itemView.getContext().startActivity(i);
            });
            binding.textViewHoroscope.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), HoroscopeActivity.class);
                holder.itemView.getContext().startActivity(i);
            });
            binding.textViewWeather.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), WeatherActivity.class);
                holder.itemView.getContext().startActivity(i);
            });

        } else if (position == list.size() + 2) {
            RvItemMenuSocialNetworkBinding binding = (RvItemMenuSocialNetworkBinding) holder.binding;

            binding.imageViewFacebook.setOnClickListener(view -> shareOnSocialNetworks(holder.itemView.getContext(), "com.facebook.katana"));
            binding.imageViewInstagram.setOnClickListener(view -> shareOnSocialNetworks(holder.itemView.getContext(), "com.instagram.android"));
            binding.imageViewTwitter.setOnClickListener(view -> shareOnSocialNetworks(holder.itemView.getContext(), "com.twitter.android"));
            binding.imageViewViber.setOnClickListener(view -> shareOnSocialNetworks(holder.itemView.getContext(), "com.viber.voip"));
            binding.imageViewWhatsapp.setOnClickListener(view -> shareOnSocialNetworks(holder.itemView.getContext(), "com.whatsapp"));
        } else {
            Category category = list.get(position - 1);

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
            binding.recyclerView.setAdapter(new MenuSubcategoryAdapter(category.subcategories));
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size() + 2) {
            return 2;
        } else if (position == list.size() + 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private void shareOnSocialNetworks(Context context, String networkUrl) {
        try {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, DataContainer.BASE_URL);
            i.setType("text/plain");
            i.setPackage(networkUrl);
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Nemate instaliranu neophodnu aplikaciju.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openWebBrowser(Context context, String link) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Nemate instaliranu neophodnu aplikaciju.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setData(ArrayList<Category> categoryList) {
        list = categoryList;
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
