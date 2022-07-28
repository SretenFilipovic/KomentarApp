package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;

public interface RvItemDetail {

    int getType();
    void bind(NewsDetailAdapter.NewsDetailViewHolder holder);
}
