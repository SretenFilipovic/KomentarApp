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

public class RvItemDetailRelatedNews implements RvItemDetail{

    private ArrayList<News> relatedNews;
    private NewsAdapter adapter;

    public RvItemDetailRelatedNews(ArrayList<News> relatedNews) {
        this.relatedNews = relatedNews;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailTagsAndNewsBinding binding = (RvItemNewsDetailTagsAndNewsBinding) holder.binding;


        if (relatedNews == null || relatedNews.size()==0){
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        }
        else {
            binding.textViewTitle.setText(R.string.text_povezane_vesti);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            adapter = new NewsAdapter(holder.itemView.getContext(), relatedNews);
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
