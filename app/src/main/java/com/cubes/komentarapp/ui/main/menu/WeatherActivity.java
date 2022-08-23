package com.cubes.komentarapp.ui.main.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Weather;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityWeatherBinding;
import com.squareup.picasso.Picasso;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(view -> finish());

        loadData();
    }

    private void loadData() {

        DataRepository.getInstance().loadWeatherData(new DataRepository.WeatherResponseListener() {
            @Override
            public void onResponse(Weather response) {
                weather = response;

                binding.textViewTitle.setText(R.string.text_weather_forcast);
                binding.textViewName.setText(weather.name);
                binding.textViewTemp.setText(weather.temp + " °C");
                binding.textViewHumidity.setText("Vlažnost vazduha: " + weather.humidity + "%");
                binding.textViewPressure.setText("Atmosferski pritisak: " + weather.pressure + " mb");
                binding.textViewWind.setText("Brzina vetra: " + weather.wind + " m/s");

                Picasso.get().load(weather.iconUrl).into(binding.imageViewIcon);

                binding.textViewTempDay0.setText(weather.day1.tempMax + " / " + weather.day1.tempMin);
                Picasso.get().load(weather.day1.iconUrl).into(binding.imageViewIconDay0);

                binding.textViewTempDay1.setText(weather.day2.tempMax + " / " + weather.day2.tempMin);
                Picasso.get().load(weather.day2.iconUrl).into(binding.imageViewIconDay1);

                binding.textViewTempDay2.setText(weather.day3.tempMax + " / " + weather.day3.tempMin);
                Picasso.get().load(weather.day3.iconUrl).into(binding.imageViewIconDay2);

                binding.textViewTempDay3.setText(weather.day4.tempMax + " / " + weather.day4.tempMin);
                Picasso.get().load(weather.day4.iconUrl).into(binding.imageViewIconDay3);

                binding.textViewTempDay4.setText(weather.day5.tempMax + " / " + weather.day5.tempMin);
                Picasso.get().load(weather.day5.iconUrl).into(binding.imageViewIconDay4);

                binding.textViewTempDay5.setText(weather.day6.tempMax + " / " + weather.day6.tempMin);
                Picasso.get().load(weather.day6.iconUrl).into(binding.imageViewIconDay5);

                binding.textViewTempDay6.setText(weather.day7.tempMax + " / " + weather.day7.tempMin);
                Picasso.get().load(weather.day7.iconUrl).into(binding.imageViewIconDay6);

                Log.d("WEATHER", "Weather load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(WeatherActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("WEATHER", "Weather load data failure");
            }
        });


    }
}