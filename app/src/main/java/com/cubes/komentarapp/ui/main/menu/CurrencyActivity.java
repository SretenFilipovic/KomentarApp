package com.cubes.komentarapp.ui.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.ActivityCurrencyBinding;
import com.cubes.komentarapp.databinding.ActivityHoroscopeBinding;

// CurrencyActivity treba da prikazuje Kursnu listu, ali posto ova aktivnost ne postoji trenutno prikazuje ekran na kome se korisniku stavlja do znanja da je aktivnost u izradi

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