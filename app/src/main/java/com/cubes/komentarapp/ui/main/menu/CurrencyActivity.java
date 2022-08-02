package com.cubes.komentarapp.ui.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.databinding.ActivityCurrencyBinding;

public class CurrencyActivity extends AppCompatActivity {

    private ActivityCurrencyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}