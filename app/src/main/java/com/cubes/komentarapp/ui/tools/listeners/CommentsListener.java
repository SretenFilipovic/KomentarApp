package com.cubes.komentarapp.ui.tools.listeners;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;

public interface CommentsListener {

    void onReplyClicked(Comments comment);

    void upvote(Comments comment);

    void downVote(Comments comment);
}