package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.DetailListener;
import com.wajahatkarim3.easyflipviewpager.CardFlipPageTransformer2;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity implements DetailListener {

    private int newsId;
    private String newsUrl;
    private String newsTitle;
    private boolean isSaved;
    private ActivityNewsDetailBinding binding;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsId = getIntent().getIntExtra("news", -1);
        int[] newsIdList = getIntent().getIntArrayExtra("newsIdList");

        NewsDetailViewPagerAdapter adapter = new NewsDetailViewPagerAdapter(this, newsIdList);
        binding.viewPager.setAdapter(adapter);

        CardFlipPageTransformer2 bookFlipPageTransformer = new CardFlipPageTransformer2();
        bookFlipPageTransformer.setScalable(false);
        binding.viewPager.setPageTransformer(bookFlipPageTransformer);

        //binding.viewPager.setOffscreenPageLimit(1);

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

        if (isSaved) {
            binding.imageViewSave.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            binding.imageViewSave.setImageResource(R.drawable.ic_bookmark);
        }

        binding.imageViewSave.setOnClickListener(view -> {
            MyNews myNews = new MyNews(newsId, newsTitle);

            if (PrefConfig.readMyNewsListFromPref(NewsDetailActivity.this) != null){
                myNewsList = (ArrayList<MyNews>) PrefConfig.readMyNewsListFromPref(NewsDetailActivity.this);

                for (int i = 0; i<myNewsList.size(); i++){
                    if (myNews.id == myNewsList.get(i).id){
                        myNewsList.remove(myNewsList.get(i));
                        PrefConfig.writeMyNewsListInPref(NewsDetailActivity.this, myNewsList);
                        binding.imageViewSave.setImageResource(R.drawable.ic_bookmark);
                        Toast.makeText(NewsDetailActivity.this, "Uspešno ste izbacili vest iz liste.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            myNewsList.add(myNews);
            PrefConfig.writeMyNewsListInPref(NewsDetailActivity.this, myNewsList);
            binding.imageViewSave.setImageResource(R.drawable.ic_bookmark_filled);
            Toast.makeText(NewsDetailActivity.this, "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
        });

        binding.swipeLeft.setVisibility(View.VISIBLE);
        binding.swipeRight.setVisibility(View.VISIBLE);
        MethodsClass.animationSwipe(binding.swipeLeft, 0, -30);
        MethodsClass.animationSwipe(binding.swipeRight, 0, 30);

        reduceDragSensitivity(4);
    }


    @Override
    public void onDetailResponseListener(int newsId, String newsUrl, String newsTitle, boolean isSaved) {
        this.newsId = newsId;
        this.newsUrl = newsUrl;
        this.newsTitle = newsTitle;
        this.isSaved = isSaved;

        if (isSaved) {
            binding.imageViewSave.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            binding.imageViewSave.setImageResource(R.drawable.ic_bookmark);
        }
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