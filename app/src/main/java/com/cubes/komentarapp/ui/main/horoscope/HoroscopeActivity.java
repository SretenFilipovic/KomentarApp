package com.cubes.komentarapp.ui.main.horoscope;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Horoscope;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityHoroscopeBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.squareup.picasso.Picasso;

public class HoroscopeActivity extends AppCompatActivity {

    private ActivityHoroscopeBinding binding;
    private DataRepository dataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;


        Picasso.get().load("https://cdn.pixabay.com/photo/2022/06/08/05/47/stars-7249785_960_720.jpg").into(binding.backgroundImg);

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> loadData());

        loadData();

    }

    private void loadData() {

        dataRepository.loadHoroscopeData(new DataRepository.HoroscopeResponseListener() {
            @Override
            public void onResponse(Horoscope horoscope) {

                binding.textViewTitle.setText(R.string.text_horoscope);
                binding.textViewName.setText(horoscope.name);
                binding.textViewDate.setText(horoscope.date);
                binding.textViewDescription.setText(horoscope.horoscope);
                Picasso.get().load(horoscope.imageUrl).into(binding.imageViewHoroscope);

                binding.linearLayoutAries.setVisibility(View.VISIBLE);
                binding.refresh.setVisibility(View.GONE);
                Log.d("HOROSCOPE", "Horoscope load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(HoroscopeActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                binding.linearLayoutAries.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
                Log.d("HOROSCOPE", "Horoscope load data failure");
            }
        });

    }
}