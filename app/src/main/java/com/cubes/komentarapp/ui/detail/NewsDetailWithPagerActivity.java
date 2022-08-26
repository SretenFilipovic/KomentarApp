package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.databinding.ActivityNewsDetailWithPagerBinding;

public class NewsDetailWithPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.cubes.komentarapp.databinding.ActivityNewsDetailWithPagerBinding binding = ActivityNewsDetailWithPagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int newsId = getIntent().getIntExtra("news", -1);
        int[] newsIdList = getIntent().getIntArrayExtra("newsIdList");

        NewsDetailViewPagerAdapter adapter = new NewsDetailViewPagerAdapter(this, newsIdList);
        binding.viewPager.setAdapter(adapter);

        for (int i = 0; i < newsIdList.length; i++) {
            if (newsId == newsIdList[i]) {
                binding.viewPager.setCurrentItem(i);
                break;
            }
        }

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.imageViewShare.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
//            i.putExtra(Intent.EXTRA_STREAM, newsUrl);
            i.setType("text/plain");
            startActivity(Intent.createChooser(i, null));
        });

        binding.imageViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // NAPRAVITI KLIK
            }
        });

    }

}