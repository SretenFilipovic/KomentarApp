package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemBigNewsHomepageBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemHeadCategoryBigNews implements RvItemHead {

    private final News news;
    private final CategoryBox category;
    private final NewsListener listener;
    private final int[] newsIdList;


    public RvItemHeadCategoryBigNews(News news, ArrayList<News> categoryNews, NewsListener listener, CategoryBox category) {
        this.listener = listener;
        this.news = news;
        this.category = category;
        this.newsIdList = MethodsClass.initNewsIdList(categoryNews);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big_news_homepage;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemBigNewsHomepageBinding binding = (RvItemBigNewsHomepageBinding) holder.binding;

        binding.showMore.setOnClickListener(view -> {

            FrameLayout viewGroup = holder.itemView.findViewById(R.id.popupElement);
            LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, viewGroup);
            PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

            ImageView comments = layout.findViewById(R.id.btnComment);
            comments.setOnClickListener(view1 -> listener.onCommentNewsClicked(news.id));

            ImageView share = layout.findViewById(R.id.btnShare);
            share.setOnClickListener(view1 -> listener.onShareNewsClicked(news.url));

            ImageView save = layout.findViewById(R.id.btnBookmark);
            save.setOnClickListener(view12 -> {
                listener.onSaveNewsClicked(news.id, news.title);
                Toast.makeText(holder.itemView.getContext(), "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
            });

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(view, -300, 0);

        });


        binding.textViewCategoryTitle.setText(category.title);
        binding.viewColor.setBackgroundColor(Color.parseColor(category.color));
        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);


        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, newsIdList));


    }
}
