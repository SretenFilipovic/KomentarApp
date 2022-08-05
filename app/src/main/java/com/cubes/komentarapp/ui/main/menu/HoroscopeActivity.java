package com.cubes.komentarapp.ui.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityHoroscopeBinding;
import com.cubes.komentarapp.data.model.Horoscope;
import com.squareup.picasso.Picasso;

public class HoroscopeActivity extends AppCompatActivity {

    private ActivityHoroscopeBinding binding;
    private Horoscope horoscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
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

        DataRepository.getInstance().loadHoroscopeData(new DataRepository.HoroscopeResponseListener() {
            @Override
            public void onResponse(Horoscope response) {
                horoscope = response;

                binding.textViewTitle.setText("Horoskop");
                binding.textViewName.setText(horoscope.name);
                binding.textViewDate.setText(horoscope.date);
                binding.textViewDescription.setText(horoscope.horoscope);
                Picasso.get().load(horoscope.image_url).into(binding.imageViewHoroscope);

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