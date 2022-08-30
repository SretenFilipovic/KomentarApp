package com.cubes.komentarapp.ui.comments.item;

import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public interface RvItemComments {

    int getType();
    void bind(ViewHolder holder);

    String getCommentsId();

    void updateUpvote();

    void updateDownvote();
}
