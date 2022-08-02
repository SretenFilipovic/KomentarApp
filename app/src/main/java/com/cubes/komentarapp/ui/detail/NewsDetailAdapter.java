package com.cubes.komentarapp.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.item.RvItemDetail;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailComments;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailRelatedNews;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailSameCategoryNews;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailTags;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailWebView;

import java.util.ArrayList;

public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.NewsDetailViewHolder> {

    private News news;
    private Context context;
    private ArrayList<RvItemDetail> items;

    public NewsDetailAdapter(Context context, News news) {
        this.context = context;
        this.news = news;

        this.items = new ArrayList<>();

        this.items.add(new RvItemDetailWebView(this.news));
        this.items.add(new RvItemDetailTags(this.news.tags));
        this.items.add(new RvItemDetailComments(this.news, this.news.comments_top_n));
        this.items.add(new RvItemDetailRelatedNews(this.news.related_news));
        this.items.add(new RvItemDetailSameCategoryNews(this.news.category_news));

    }

    @NonNull
    @Override
    public NewsDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 0:
                binding = RvItemNewsDetailWebViewBinding.inflate(inflater, parent, false);
                break;
            case 2:
                binding = RvItemNewsDetailCommentsBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemNewsDetailTagsAndNewsBinding.inflate(inflater, parent, false);
        }

        return new NewsDetailViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsDetailViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }

    public class NewsDetailViewHolder extends RecyclerView.ViewHolder{

        public ViewBinding binding;

        public NewsDetailViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

