package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public interface RvItemDetail {

    int getType();

    void bind(ViewHolder holder);

    default String getCommentsId() {
        return null;
    }

    default void updateUpvote() {
    }

    default void updateDownvote() {
    }
}
