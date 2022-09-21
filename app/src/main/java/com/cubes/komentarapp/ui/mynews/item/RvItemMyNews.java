package com.cubes.komentarapp.ui.mynews.item;

import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public interface RvItemMyNews {

    int getType();

    void bind(ViewHolder holder);

    int getNewsId();

}
