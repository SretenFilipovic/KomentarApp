package com.cubes.komentarapp.ui.main.menu;

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
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.databinding.RvItemMenuCategoryBinding;
import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.ui.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Category> list;

    public MenuAdapter(Context context, ArrayList<Category> categoryList) {
        list = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){
            RvItemMenuCategoryBinding binding =
                    RvItemMenuCategoryBinding.inflate(LayoutInflater.from(context), parent,false);
            return new HeaderHolder(binding);
        }
        else if (viewType == 1){
            RvItemMenuItemsBinding binding =
                    RvItemMenuItemsBinding.inflate(LayoutInflater.from(context), parent, false);
            return new ItemsHolder(binding);
        }
        else {
            RvItemMenuSocialNetworkBinding binding =
                    RvItemMenuSocialNetworkBinding.inflate(LayoutInflater.from(context), parent, false);
            return new SocialNetworkHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == 0){
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.binding.imageViewExpandSubcategoryList.setVisibility(View.GONE);
            headerHolder.binding.textViewCategory.setText(R.string.text_naslovna);

            headerHolder.binding.textViewCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawer = ((MainActivity) context).findViewById(R.id.drawerLayout);
                    BottomNavigationView bottomNavigationView = ((MainActivity) context).findViewById(R.id.bottomNavigationView);
                    drawer.closeDrawer(((MainActivity) context).findViewById(R.id.drawerNavigationView));
                    bottomNavigationView.setSelectedItemId(R.id.home);
                }
            });
        }

        else if (position == list.size() + 1){
            ItemsHolder itemsHolder = (ItemsHolder) holder;

            itemsHolder.binding.textViewContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWebBrowser("https://komentar.rs");
                }
            });
            itemsHolder.binding.textViewTermsAndConditions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWebBrowser("https://komentar.rs");
                }
            });
            itemsHolder.binding.textViewPushNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWebBrowser("https://komentar.rs");
                }
            });
            itemsHolder.binding.textViewMarketing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWebBrowser("https://komentar.rs");
                }
            });

            itemsHolder.binding.textViewCurrency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, CurrencyActivity.class);
                    context.startActivity(i);
                }
            });
            itemsHolder.binding.textViewHoroscope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, HoroscopeActivity.class);
                    context.startActivity(i);
                }
            });
            itemsHolder.binding.textViewWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, WeatherActivity.class);
                    context.startActivity(i);
                }
            });

        }
        else if (position == list.size() + 2){
            SocialNetworkHolder holderSocial = (SocialNetworkHolder) holder;

            holderSocial.binding.imageViewFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareOnSocialNetworks("com.facebook.katana");
                }
            });
            holderSocial.binding.imageViewInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareOnSocialNetworks("com.instagram.android");
                }
            });
            holderSocial.binding.imageViewTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareOnSocialNetworks("com.twitter.android");
                }
            });
            holderSocial.binding.imageViewViber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareOnSocialNetworks("com.viber.voip");
                }
            });
            holderSocial.binding.imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareOnSocialNetworks("com.whatsapp");
                }
            });
        }

        else {
            Category category = list.get(position - 1);
            HeaderHolder holderCategory = (HeaderHolder) holder;

            holderCategory.binding.textViewCategory.setText(category.name);
            holderCategory.binding.viewCategoryColor.setBackgroundColor(Color.parseColor(category.color));
            holderCategory.binding.viewSubcategoryColor.setBackgroundColor(Color.parseColor(category.color));

            if (category.subcategories.size() == 0){
                holderCategory.binding.imageViewExpandSubcategoryList.setVisibility(View.GONE);
            }
            else{
                holderCategory.binding.imageViewExpandSubcategoryList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (holderCategory.binding.subcategoryContainer.getVisibility() == View.GONE){
                            holderCategory.binding.subcategoryContainer.setVisibility(View.VISIBLE);
                            holderCategory.binding.imageViewExpandSubcategoryList.setImageResource(R.drawable.ic_arrow_up);
                        }
                        else{
                            holderCategory.binding.subcategoryContainer.setVisibility(View.GONE);
                            holderCategory.binding.imageViewExpandSubcategoryList.setImageResource(R.drawable.ic_arrow_down);
                        }
                    }
                });
            }

            holderCategory.binding.textViewCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DrawerLayout drawer = ((MainActivity) context).findViewById(R.id.drawerLayout);
                    ViewPager2 viewPager2 = ((MainActivity) context).findViewById(R.id.viewPagerHome);
                    drawer.closeDrawer(((MainActivity) context).findViewById(R.id.drawerNavigationView));
                    viewPager2.setCurrentItem(position);

                }
            });

            holderCategory.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holderCategory.binding.recyclerView.setAdapter(new MenuSubcategoryAdapter(context, category.subcategories));
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == list.size() + 2){
            return 2;
        }
        else if (position == list.size() + 1){
            return 1;
        }
        else{
            return 0;
        }
    }

    private void shareOnSocialNetworks(String networkUrl){
        try {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, DataContainer.BASE_URL);
            i.setType("text/plain");
            i.setPackage(networkUrl);
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Nemate instaliranu neophodnu aplikaciju.",  Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openWebBrowser(String link){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Nemate instaliranu neophodnu aplikaciju.",  Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public class HeaderHolder extends RecyclerView.ViewHolder{

        private RvItemMenuCategoryBinding binding;

        public HeaderHolder(RvItemMenuCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ItemsHolder extends RecyclerView.ViewHolder{

        private RvItemMenuItemsBinding binding;

        public ItemsHolder(RvItemMenuItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class SocialNetworkHolder extends RecyclerView.ViewHolder{

        private RvItemMenuSocialNetworkBinding binding;

        public SocialNetworkHolder(RvItemMenuSocialNetworkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
