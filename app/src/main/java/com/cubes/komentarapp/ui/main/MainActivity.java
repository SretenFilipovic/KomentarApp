package com.cubes.komentarapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityMainBinding;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.main.home.HomeFragment;
import com.cubes.komentarapp.ui.main.latest.LatestFragment;
import com.cubes.komentarapp.ui.main.search.SearchFragment;
import com.cubes.komentarapp.ui.main.video.VideoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                categoryList = response;

                getSupportFragmentManager().beginTransaction().replace(R.id.container,HomeFragment.newInstance(categoryList)).commit();

                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                fullyOpenDrawer(binding.drawerNavigationView);

                binding.imageViewMenu.setOnClickListener(view -> binding.drawerLayout.openDrawer(binding.drawerNavigationView));

                binding.imageViewCloseMenu.setOnClickListener(view -> binding.drawerLayout.closeDrawer(binding.drawerNavigationView));

                binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = HomeFragment.newInstance(categoryList);
                        {binding.imageViewMenu.setVisibility(View.VISIBLE);}
                        break;
                        case R.id.search:
                            selectedFragment = SearchFragment.newInstance();
                        {binding.imageViewMenu.setVisibility(View.GONE);}
                        break;
                        case R.id.latest:
                            selectedFragment = LatestFragment.newInstance();
                        {binding.imageViewMenu.setVisibility(View.GONE);}
                        break;
                        case R.id.video:
                            selectedFragment = VideoFragment.newInstance();
                        {binding.imageViewMenu.setVisibility(View.GONE);}
                        break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,selectedFragment)
                            .commit();

                    return true;
                });

                binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerViewMenu.setAdapter(new MenuAdapter(MainActivity.this, categoryList));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void fullyOpenDrawer (View view){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) view.getLayoutParams();
        params.width = metrics.widthPixels;
        view.setLayoutParams(params);
    }

}