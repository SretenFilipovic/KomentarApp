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

import java.util.ArrayList;

public class RvItemDetailSameCategoryNews implements RvItemDetail {

    private final ArrayList<News> categoryNews;

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

        if (categoryNews == null || categoryNews.size() == 0) {
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.textViewTitle.setText(R.string.text_same_category_news);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            NewsAdapter adapter = new NewsAdapter(categoryNews);
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
