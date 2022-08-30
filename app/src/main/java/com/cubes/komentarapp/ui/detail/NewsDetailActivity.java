package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailFragment.DetailListener {

    private int newsId;
    private String newsUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewsDetailBinding binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsId = getIntent().getIntExtra("news", -1);
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
            i.putExtra(Intent.EXTRA_TEXT, newsUrl);
            i.setType("text/plain");
            startActivity(Intent.createChooser(i, null));
        });

        binding.imageViewComments.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetailActivity.this, CommentsActivity.class);
            i.putExtra("news", newsId);
            startActivity(i);
        });
    }

    @Override
    public void onDetailResponseListener(int newsId, String newsUrl) {
        this.newsId = newsId;
        this.newsUrl = newsUrl;
    }
}