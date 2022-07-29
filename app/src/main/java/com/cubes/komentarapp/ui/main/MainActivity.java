package com.cubes.komentarapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.databinding.ActivityMainBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.main.home.HomeFragment;
import com.cubes.komentarapp.ui.main.latest.LatestFragment;
import com.cubes.komentarapp.ui.main.search.SearchFragment;
import com.cubes.komentarapp.ui.main.video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// MainActivity je glavni aktiviti u aplikaciji i u njega su neposredno smestena 4 fragmenta (Home, Latest, Video i Search)
// Posredno, u MainActivity-ju se nalaze i fragmenti za Naslovne vesti i vesti po kategorijama (ViewPager) koji su smesteni u HomeFragment

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<News> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.container,HomeFragment.newInstance(DataContainer.categoryList)).commit();

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        fullyOpenDrawer(binding.drawerNavigationView);

        binding.imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(binding.drawerNavigationView);
            }
        });

        binding.imageViewCloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.closeDrawer(binding.drawerNavigationView);
            }
        });

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance(DataContainer.categoryList);
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
            }
        });

        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerViewMenu.setAdapter(new MenuAdapter(MainActivity.this, DataContainer.categoryList));
    }

    // metoda kojom se drawerLayout otvara preko celog ekrana (preuzeto sa Stackoverflow)
    private void fullyOpenDrawer (View view){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) view.getLayoutParams();
        params.width = metrics.widthPixels;
        view.setLayoutParams(params);
    }

}