package com.cubes.komentarapp.ui.main;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.networking.NewsRetrofit;
import com.cubes.komentarapp.databinding.ActivityMainBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.main.home.HomeFragment;
import com.cubes.komentarapp.ui.main.latest.LatestFragment;
import com.cubes.komentarapp.ui.main.menu.CurrencyActivity;
import com.cubes.komentarapp.ui.main.menu.HoroscopeActivity;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.main.menu.WeatherActivity;
import com.cubes.komentarapp.ui.main.search.SearchFragment;
import com.cubes.komentarapp.ui.main.video.VideoFragment;
import com.cubes.komentarapp.ui.mynews.MyNewsActivity;
import com.cubes.komentarapp.ui.subcategory.SubcategoryActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MenuAdapter adapter;
    private DataRepository dataRepository;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit();

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        fullyOpenDrawer(binding.drawerNavigationView);

        binding.buttonNo.setOnClickListener(view -> binding.closeAppDialog.setVisibility(View.GONE));
        binding.buttonYes.setOnClickListener(view -> finish());

        binding.imageViewCloseMenu.setOnClickListener(view -> binding.drawerLayout.closeDrawer(binding.drawerNavigationView));

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adViewSticky.loadAd(adRequest);
        binding.adViewSticky.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.adViewSticky.setVisibility(View.VISIBLE);
                binding.closeAd.setVisibility(View.VISIBLE);
            }
        });
        binding.closeAd.setOnClickListener(view -> {
            binding.adViewSticky.setVisibility(View.GONE);
            binding.closeAd.setVisibility(View.GONE);
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = HomeFragment.newInstance();
                break;
                case R.id.search:
                    selectedFragment = SearchFragment.newInstance();
                break;
                case R.id.latest:
                    selectedFragment = LatestFragment.newInstance();
                break;
                case R.id.video:
                    selectedFragment = VideoFragment.newInstance();
                    break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
            }
            return true;
        });

        setRecyclerView();
        loadData();
    }

    @Override
    public void onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if (binding.closeAppDialog.getVisibility() == View.VISIBLE){
                binding.closeAppDialog.setVisibility(View.GONE);
            }
            else {
                binding.closeAppDialog.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        binding.closeAppDialog.getGlobalVisibleRect(viewRect);

        if (binding.closeAppDialog.getVisibility() == View.VISIBLE && !viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
            binding.closeAppDialog.setVisibility(View.GONE);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setRecyclerView(){
        boolean isNotificationOn = PrefConfig.isNotificationOn(this);
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MenuAdapter();
        adapter.setIsNotificationOn(isNotificationOn);
        binding.recyclerViewMenu.setAdapter(adapter);
    }

    private void loadData() {

        dataRepository.loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                adapter.setData(response, new MenuListener() {
                    @Override
                    public void onSubcategoryClicked(int categoryId, int subcategoryId) {

                        Intent i = new Intent(MainActivity.this, SubcategoryActivity.class);
                        i.putExtra("categoryId", categoryId);
                        i.putExtra("subcategoryId", subcategoryId);
                        startActivity(i);
                    }

                    @Override
                    public void onContactClicked() {
                        openWebBrowser("https://komentar.rs");
                    }

                    @Override
                    public void onTermsAndConditionsClicked() {
                        openWebBrowser("https://komentar.rs");
                    }

                    @Override
                    public void onMarketingClicked() {
                        openWebBrowser("https://komentar.rs");
                    }

                    @Override
                    public void onMyNewsClicked() {
                        Intent i = new Intent(MainActivity.this, MyNewsActivity.class);
                        startActivity(i);
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
                            i.putExtra(Intent.EXTRA_TEXT, NewsRetrofit.BASE_URL);
                            i.setType("text/plain");
                            i.setPackage(networkUrl);
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getApplicationContext(), R.string.text_no_app, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNotificationClicked(boolean isOn) {
                        PrefConfig.setNotificationStatus(MainActivity.this, isOn);
                        if (isOn){
                            FirebaseMessaging.getInstance().subscribeToTopic("main");
                        }
                        else {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("main");
                        }
                    }

                });

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openWebBrowser(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.text_no_app, Toast.LENGTH_SHORT).show();
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