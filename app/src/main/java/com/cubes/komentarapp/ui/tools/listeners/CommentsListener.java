package com.cubes.komentarapp.ui.tools.listeners;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;

public interface CommentsListener {

    void onCommentsClicked(Comments comment);

    void upvote(Comments comment, RvItemCommentBinding binding);

    void downVote(Comments comment, RvItemCommentBinding binding);

}
