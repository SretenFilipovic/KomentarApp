package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsVideoAdapter;

import java.util.ArrayList;

public class RvItemHeadVideo implements RvItemHead{

    private ArrayList<News> topNews;
    private HeadNewsVideoAdapter adapter;

    public RvItemHeadVideo(ArrayList<News> topNews) {
        this.topNews = topNews;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new HeadNewsVideoAdapter(holder.itemView.getContext(), topNews);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
                    @Override
                    public void onResponse(News response) {
                        News newsDetails = response;

                        Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                        i.putExtra("news",newsDetails);
                        holder.itemView.getContext().startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });            }
        });
        binding.recyclerView.setAdapter(adapter);

    }
}