package com.cubes.komentarapp.ui.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.databinding.ActivitySplashscreenBinding;
import com.cubes.komentarapp.ui.main.MainActivity;


@SuppressLint("CustomSplashScreen")
public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.cubes.komentarapp.databinding.ActivitySplashscreenBinding binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() == null || getIntent().getExtras().getString("url") == null) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }, 500);
        } else {
            Bundle bundle = getIntent().getExtras();
            String url = bundle.getString("url");

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            finish();
        }

    }
}