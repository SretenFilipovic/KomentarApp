package com.cubes.komentarapp.ui.detail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.databinding.RvItemAdviewBinding;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsShowAllBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsTitleBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailRelatedNewsTitleBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsBinding;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.detail.item.RvItemDetail;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailAdView;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailComments;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailCommentsButton;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailCommentsTitle;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailRelatedNews;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailRelatedNewsTitle;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailTags;
import com.cubes.komentarapp.ui.detail.item.RvItemDetailWebView;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

import java.util.ArrayList;
import java.util.Objects;

public class NewsDetailAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItemDetail> items = new ArrayList<>();

    public NewsDetailAdapter() {
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.layout.rv_item_news_detail_web_view:
                binding = RvItemNewsDetailWebViewBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_news_detail_comments_title:
                binding = RvItemNewsDetailCommentsTitleBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_news_detail_comments_show_all:
                binding = RvItemNewsDetailCommentsShowAllBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_comment:
                binding = RvItemCommentBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_news_detail_tags:
                binding = RvItemNewsDetailTagsBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_news_detail_related_news_title:
                binding = RvItemNewsDetailRelatedNewsTitleBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_adview:
                binding = RvItemAdviewBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemSmallNewsBinding.inflate(inflater, parent, false);
        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    public void setData(NewsDetail response, NewsDetailListener newsDetailListener, CommentsListener commentsListener) {

        this.items.add(new RvItemDetailAdView());
        this.items.add(new RvItemDetailWebView(response));
        this.items.add(new RvItemDetailAdView());
        this.items.add(new RvItemDetailTags(response.tags, newsDetailListener));
        this.items.add(new RvItemDetailCommentsTitle(response, newsDetailListener));

        for (Comments comment : response.topComments) {
            this.items.add(new RvItemDetailComments(comment, commentsListener));
        }
        this.items.add(new RvItemDetailCommentsButton(response, newsDetailListener));

        this.items.add(new RvItemDetailAdView());

        this.items.add(new RvItemDetailRelatedNewsTitle());
        for (News news : response.relatedNews) {
            this.items.add(new RvItemDetailRelatedNews(news, response.relatedNews, newsDetailListener));
        }

        notifyDataSetChanged();
    }

    public void commentUpvoted(String commentId) {
        for (RvItemDetail item : items) {
            if (Objects.equals(item.getCommentsId(), commentId)) {
                item.updateUpvote();
                break;
            }
        }
    }

    public void commentDownvoted(String commentId) {
        for (RvItemDetail itemModel : items) {
            if (Objects.equals(itemModel.getCommentsId(), commentId)) {
                itemModel.updateDownvote();
                break;
            }
        }
    }

}

