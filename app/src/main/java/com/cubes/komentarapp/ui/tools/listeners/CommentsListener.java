package com.cubes.komentarapp.ui.tools.listeners;

import com.cubes.komentarapp.data.model.domain.Comments;

public interface CommentsListener {

    void onReplyClicked(Comments comment);

    void upvote(Comments comment);

    void downVote(Comments comment);
}