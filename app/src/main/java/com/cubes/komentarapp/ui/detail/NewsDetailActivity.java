package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.tools.listeners.DetailListener;

import java.lang.reflect.Field;

public class NewsDetailActivity extends AppCompatActivity implements DetailListener {

    private int newsId;
    private String newsUrl;
    private ActivityNewsDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsId = getIntent().getIntExtra("news", -1);
        int[] newsIdList = getIntent().getIntArrayExtra("newsIdList");
        NewsDetailViewPagerAdapter adapter = new NewsDetailViewPagerAdapter(this, newsIdList);
        binding.viewPager.setAdapter(adapter);

        for (int i = 0; i < newsIdList.length; i++) {
            if (newsId == newsIdList[i]) {
                binding.viewPager.setCurrentItem(i, false);
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

        reduceDragSensitivity(5);
    }

    @Override
    public void onDetailResponseListener(int newsId, String newsUrl) {
        this.newsId = newsId;
        this.newsUrl = newsUrl;
    }

    private void reduceDragSensitivity(int sensitivity) {
        try {
            Field ff = ViewPager2.class.getDeclaredField("mRecyclerView");
            ff.setAccessible(true);
            RecyclerView recyclerView = (RecyclerView) ff.get(binding.viewPager);
            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);
            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView, touchSlop * sensitivity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}