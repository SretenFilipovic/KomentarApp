package com.cubes.komentarapp.ui.detail.item;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemDetailRelatedNews implements RvItemDetail {

    private ArrayList<News> relatedNews;

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

        if (relatedNews == null || relatedNews.size() == 0) {
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.textViewTitle.setText(R.string.text_povezane_vesti);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            NewsAdapter adapter = new NewsAdapter(relatedNews);
            adapter.setFinished(true);

            adapter.setNewsListener(news -> {
                Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                i.putExtra("news", news.id);
                holder.itemView.getContext().startActivity(i);

            });
            binding.recyclerView.setAdapter(adapter);
        }

    }
}
