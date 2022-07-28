package com.cubes.komentarapp.ui.detail.item;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

public class RvItemDetailSameCategoryNews implements RvItemDetail{

    private ArrayList<News> categoryNews;
    private NewsAdapter adapter;

    public RvItemDetailSameCategoryNews(ArrayList<News> categoryNews) {
        this.categoryNews = categoryNews;
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailTagsAndNewsBinding binding = (RvItemNewsDetailTagsAndNewsBinding) holder.binding;

        if (categoryNews == null || categoryNews.size()==0){
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        }
        else {
            binding.textViewTitle.setText(R.string.text_same_category_news);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            adapter = new NewsAdapter(holder.itemView.getContext(), categoryNews);
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
}
