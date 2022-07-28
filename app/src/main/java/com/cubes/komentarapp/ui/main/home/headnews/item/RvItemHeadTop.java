package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.tools.NewsListener;
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
        adapter = new NewsAdapter(holder.itemView.getContext(), topNews);
        adapter.setFinished(true);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(holder.itemView.getContext(), news);
            }
        });
        binding.recyclerView.setAdapter(adapter);

    }
}
