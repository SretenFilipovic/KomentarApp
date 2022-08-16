package com.cubes.komentarapp.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.ui.detail.item.RvItemDetail;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailComments;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailRelatedNews;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailSameCategoryNews;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailTags;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailWebView;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

import java.util.ArrayList;

public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.NewsDetailViewHolder> {

    private final ArrayList<RvItemDetail> items = new ArrayList<>();

    public NewsDetailAdapter() {
    }

    @NonNull
    @Override
    public NewsDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
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

    public void setData(News response, NewsDetailListener newsDetailListener) {

        this.items.add(new RvItemDetailWebView(response));
        this.items.add(new RvItemDetailTags(response.tags, newsDetailListener));
        this.items.add(new RvItemDetailComments(response, response.comments_top_n, newsDetailListener));
        this.items.add(new RvItemDetailRelatedNews(response.related_news, newsDetailListener));
        this.items.add(new RvItemDetailSameCategoryNews(response.category_news, newsDetailListener));

        notifyDataSetChanged();
    }

    public class NewsDetailViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public NewsDetailViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

