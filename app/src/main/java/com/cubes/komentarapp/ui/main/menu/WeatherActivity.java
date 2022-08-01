package com.cubes.komentarapp.ui.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityWeatherBinding;
import com.cubes.komentarapp.data.source.remote.response.ResponseWeather;
import com.cubes.komentarapp.data.model.Weather;
import com.squareup.picasso.Picasso;

// WeatherActivity prikazuje podatke za vremensku prognozu koju dobijemo sa servera

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();

    }

    private void loadData(){

        DataRepository.getInstance().loadWeatherData(new DataRepository.WeatherResponseListener() {
            @Override
            public void onResponse(Weather response) {
                weather = response;

                binding.textViewTitle.setText("Vremenska prognoza");
                binding.textViewName.setText(weather.name);
                binding.textViewTemp.setText(weather.temp + " °C");
                binding.textViewHumidity.setText("Vlažnost vazduha: " + weather.humidity + "%");
                binding.textViewPressure.setText("Atmosferski pritisak: " + weather.pressure + " mb");
                binding.textViewWind.setText("Brzina vetra: " + weather.wind + " m/s");

                Picasso.get().load(weather.icon_url).into(binding.imageViewIcon);

                binding.textViewTempDay0.setText(weather.day_0.temp_max + " / " + weather.day_0.temp_min);
                Picasso.get().load(weather.day_0.icon_url).into(binding.imageViewIconDay0);

                binding.textViewTempDay1.setText(weather.day_1.temp_max + " / " + weather.day_1.temp_min);
                Picasso.get().load(weather.day_1.icon_url).into(binding.imageViewIconDay1);

                binding.textViewTempDay2.setText(weather.day_2.temp_max + " / " + weather.day_2.temp_min);
                Picasso.get().load(weather.day_2.icon_url).into(binding.imageViewIconDay2);

                binding.textViewTempDay3.setText(weather.day_3.temp_max + " / " + weather.day_3.temp_min);
                Picasso.get().load(weather.day_3.icon_url).into(binding.imageViewIconDay3);

                binding.textViewTempDay4.setText(weather.day_4.temp_max + " / " + weather.day_4.temp_min);
                Picasso.get().load(weather.day_4.icon_url).into(binding.imageViewIconDay4);

                binding.textViewTempDay5.setText(weather.day_5.temp_max + " / " + weather.day_5.temp_min);
                Picasso.get().load(weather.day_5.icon_url).into(binding.imageViewIconDay5);

                binding.textViewTempDay6.setText(weather.day_6.temp_max + " / " + weather.day_6.temp_min);
                Picasso.get().load(weather.day_6.icon_url).into(binding.imageViewIconDay6);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }
}