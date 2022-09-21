package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.graphics.Color;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.databinding.RvItemHeadBigNewsTitleBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public class RvItemHeadCategoryBigNewsTitle implements RvItemHead {

    private final CategoryBox category;


    public RvItemHeadCategoryBigNewsTitle(CategoryBox category) {
        this.category = category;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_head_big_news_title;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadBigNewsTitleBinding binding = (RvItemHeadBigNewsTitleBinding) holder.binding;

        binding.textViewCategoryTitle.setText(category.title);
        binding.viewColor.setBackgroundColor(Color.parseColor(category.color));
    }
}
