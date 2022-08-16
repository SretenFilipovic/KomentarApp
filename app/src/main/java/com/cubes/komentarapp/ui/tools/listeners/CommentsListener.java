package com.cubes.komentarapp.ui.tools.listeners;

import com.cubes.komentarapp.data.model.Comments;

public interface CommentsListener {

    void onCommentsClicked(Comments comment);

    void upvote(String commentId);

    void downVote(String commentId);

}
