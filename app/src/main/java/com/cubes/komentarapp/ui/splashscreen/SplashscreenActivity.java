package com.cubes.komentarapp.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cubes.komentarapp.databinding.ActivitySplashscreenBinding;
import com.cubes.komentarapp.ui.main.MainActivity;


public class SplashscreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 500);

    }
}