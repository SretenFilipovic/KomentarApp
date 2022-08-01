package com.cubes.komentarapp.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySplashscreenBinding;
import com.cubes.komentarapp.ui.main.MainActivity;

import java.util.ArrayList;

public class SplashscreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }
}