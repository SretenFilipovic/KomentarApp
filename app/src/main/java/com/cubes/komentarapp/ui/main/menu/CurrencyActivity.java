package com.cubes.komentarapp.ui.main.menu;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.databinding.ActivityCurrencyBinding;

public class CurrencyActivity extends AppCompatActivity {

    private ActivityCurrencyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(view -> finish());

    }
}