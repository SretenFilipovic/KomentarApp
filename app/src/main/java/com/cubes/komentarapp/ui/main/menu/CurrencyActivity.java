package com.cubes.komentarapp.ui.main.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.databinding.ActivityCurrencyBinding;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.cubes.komentarapp.databinding.ActivityCurrencyBinding binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(view -> finish());

        String url = "http://www.vipsistem.rs/kursna-lista.php";

        binding.webViewCurrency.loadUrl(url);
    }
}