package com.cubes.komentarapp.ui.main.home.headnews.item;

import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;

public interface RvItemHead {

    int getType();
    void bind(HeadNewsAdapter.HeadNewsViewHolder holder);
}
