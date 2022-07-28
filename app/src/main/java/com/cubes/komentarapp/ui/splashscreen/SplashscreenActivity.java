package com.cubes.komentarapp.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySplashscreenBinding;
import com.cubes.komentarapp.data.source.remote.response.ResponseCategory;
import com.cubes.komentarapp.ui.main.MainActivity;


// SplashscreenActivity sluzi kao pozdravni ekran i on traje sve dok se ne popune liste za Naslovne vesti i lista za kategorije u meniju

public class SplashscreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // U Splashscreen aktivitiju pravimo poziv za kategorije koje ce da popune listu u meniju i poziv za vesti iz Naslovnu stranicu

        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ResponseCategory response) {
                DataContainer.categoryList = response.data;

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });



    }
}