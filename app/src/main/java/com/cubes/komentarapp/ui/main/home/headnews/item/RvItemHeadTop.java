package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;

import java.util.ArrayList;


public class RvItemHeadTop implements RvItemHead{

    private ArrayList<News> topNews;
    private NewsAdapter adapter;

    public RvItemHeadTop(ArrayList<News> topNews) {
        this.topNews = topNews;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new NewsAdapter(topNews);
        adapter.setFinished(true);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {

                Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                holder.itemView.getContext().startActivity(i);
          }
        });
        binding.recyclerView.setAdapter(adapter);

    }
}
