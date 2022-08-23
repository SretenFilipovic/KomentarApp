package com.cubes.komentarapp.ui.main.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Horoscope;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityHoroscopeBinding;
import com.squareup.picasso.Picasso;

public class HoroscopeActivity extends AppCompatActivity {

    private ActivityHoroscopeBinding binding;
    private Horoscope horoscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(view -> finish());

        loadData();

    }

    private void loadData() {

        DataRepository.getInstance().loadHoroscopeData(new DataRepository.HoroscopeResponseListener() {
            @Override
            public void onResponse(Horoscope response) {
                horoscope = response;

                binding.textViewTitle.setText(R.string.text_horoscope);
                binding.textViewName.setText(horoscope.name);
                binding.textViewDate.setText(horoscope.date);
                binding.textViewDescription.setText(horoscope.horoscope);
                Picasso.get().load(horoscope.imageUrl).into(binding.imageViewHoroscope);

                Log.d("HOROSCOPE", "Horoscope load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(HoroscopeActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("HOROSCOPE", "Horoscope load data failure");
            }
        });

    }
}