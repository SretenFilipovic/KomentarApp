package com.cubes.komentarapp.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.databinding.ActivityMainBinding;
import com.cubes.komentarapp.ui.main.home.HomeFragment;
import com.cubes.komentarapp.ui.main.latest.LatestFragment;
import com.cubes.komentarapp.ui.main.menu.CurrencyActivity;
import com.cubes.komentarapp.ui.main.menu.HoroscopeActivity;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.main.menu.WeatherActivity;
import com.cubes.komentarapp.ui.main.search.SearchFragment;
import com.cubes.komentarapp.ui.main.video.VideoFragment;
import com.cubes.komentarapp.ui.subcategory.SubcategoryActivity;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit();

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        fullyOpenDrawer(binding.drawerNavigationView);

        binding.refresh.setVisibility(View.GONE);

        binding.refresh.setOnClickListener(view -> loadData());
        binding.imageViewMenu.setOnClickListener(view -> binding.drawerLayout.openDrawer(binding.drawerNavigationView));
        binding.imageViewCloseMenu.setOnClickListener(view -> binding.drawerLayout.closeDrawer(binding.drawerNavigationView));

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = HomeFragment.newInstance();
                {
                    binding.imageViewMenu.setVisibility(View.VISIBLE);
                }
                break;
                case R.id.search:
                    selectedFragment = SearchFragment.newInstance();
                {
                    binding.imageViewMenu.setVisibility(View.GONE);
                }
                break;
                case R.id.latest:
                    selectedFragment = LatestFragment.newInstance();
                {
                    binding.imageViewMenu.setVisibility(View.GONE);
                }
                break;
                case R.id.video:
                    selectedFragment = VideoFragment.newInstance();
                {
                    binding.imageViewMenu.setVisibility(View.GONE);
                }
                break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
            }
            return true;
        });

        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MenuAdapter();
        binding.recyclerViewMenu.setAdapter(adapter);

        loadData();
    }

    private void loadData() {

        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                adapter.setData(response, new MenuListener() {
                    @Override
                    public void onSubcategoryClicked(Category category) {
                        Intent i = new Intent(MainActivity.this, SubcategoryActivity.class);
                        i.putExtra("categoryId", category.id);
                        i.putExtra("categoryName", category.name);
                        startActivity(i);
                    }

                    @Override
                    public void onItemClicked() {
                        openWebBrowser("https://komentar.rs");
                    }

                    @Override
                    public void onHoroscopeClicked() {
                        Intent i = new Intent(MainActivity.this, HoroscopeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCurrencyClicked() {
                        Intent i = new Intent(MainActivity.this, CurrencyActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onWeatherClicked() {
                        Intent i = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onShareClicked(String networkUrl) {
                        try {
                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_SEND);
                            i.putExtra(Intent.EXTRA_TEXT, DataContainer.BASE_URL);
                            i.setType("text/plain");
                            i.setPackage(networkUrl);
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Nemate instaliranu neophodnu aplikaciju.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openWebBrowser(String link) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Nemate instaliranu neophodnu aplikaciju.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void fullyOpenDrawer(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) view.getLayoutParams();
        params.width = metrics.widthPixels;
        view.setLayoutParams(params);
    }

}