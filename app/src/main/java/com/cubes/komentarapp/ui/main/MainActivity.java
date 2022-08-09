package com.cubes.komentarapp.ui.main;

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
import com.cubes.komentarapp.databinding.ActivityMainBinding;
import com.cubes.komentarapp.ui.main.home.HomeFragment;
import com.cubes.komentarapp.ui.main.latest.LatestFragment;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.main.search.SearchFragment;
import com.cubes.komentarapp.ui.main.video.VideoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MenuAdapter();
        binding.recyclerViewMenu.setAdapter(adapter);

        loadData();

        binding.refresh.setOnClickListener(view -> loadData());
        binding.imageViewMenu.setOnClickListener(view -> binding.drawerLayout.openDrawer(binding.drawerNavigationView));
        binding.imageViewCloseMenu.setOnClickListener(view -> binding.drawerLayout.closeDrawer(binding.drawerNavigationView));

    }

    private void loadData() {
        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance(response)).commit();

                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                fullyOpenDrawer(binding.drawerNavigationView);

                binding.refresh.setVisibility(View.GONE);

                binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = HomeFragment.newInstance(response);
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

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, selectedFragment)
                            .commit();

                    return true;
                });

                adapter.setData(response);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fullyOpenDrawer(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) view.getLayoutParams();
        params.width = metrics.widthPixels;
        view.setLayoutParams(params);
    }


}