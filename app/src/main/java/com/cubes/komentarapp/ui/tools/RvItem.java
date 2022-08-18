package com.cubes.komentarapp.ui.tools;

import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public interface RvItem {

    int getType();
    void bind(ViewHolder holder);
}
