package com.cubes.komentarapp.ui.main.item;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemNewsBig implements RvItemNews {

    private final News news;
    private final NewsListener listener;
    private final int[] newsIdList;

    public RvItemNewsBig(News news, ArrayList<News> newsList, NewsListener listener) {
        this.listener = listener;
        this.news = news;
        this.newsIdList = MethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big_news;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemBigNewsBinding binding = (RvItemBigNewsBinding) holder.binding;

        binding.showMore.setOnClickListener(view -> {

            FrameLayout viewGroup = holder.itemView.findViewById(R.id.popupElement);
            LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, viewGroup);
            PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

            ImageView comments = layout.findViewById(R.id.btnComment);
            comments.setOnClickListener(view1 -> {
                listener.onCommentNewsClicked(news.id);
                popupWindow.dismiss();
            });

            ImageView share = layout.findViewById(R.id.btnShare);
            share.setOnClickListener(view13 -> {
                listener.onShareNewsClicked(news.url);
                popupWindow.dismiss();
            });

            ImageView save = layout.findViewById(R.id.btnBookmark);
            save.setOnClickListener(view12 -> {
                listener.onSaveNewsClicked(news.id, news.title);
                popupWindow.dismiss();
            });

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(binding.anchor, 0, 0);

        });


        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, newsIdList));


    }
}
