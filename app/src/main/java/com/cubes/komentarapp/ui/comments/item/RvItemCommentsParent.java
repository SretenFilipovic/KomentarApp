package com.cubes.komentarapp.ui.comments.item;

import android.widget.Toast;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class RvItemCommentsParent implements RvItemComments {

    private final Comments comment;
    private final CommentsListener listener;
    public RvItemCommentBinding binding;

    public RvItemCommentsParent(Comments comment, CommentsListener listener) {
        this.comment = comment;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_comment;
    }

    @Override
    public void bind(ViewHolder holder) {

        binding = (RvItemCommentBinding) holder.binding;

        binding.textViewName.setText(comment.name);
        binding.textViewContent.setText(comment.content);
        binding.textViewCreatedAt.setText(comment.createdAt);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positiveVotes));
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negativeVotes));

        binding.imageViewReply.setOnClickListener(view -> listener.onReplyClicked(comment));
        binding.textViewReply.setOnClickListener(view -> listener.onReplyClicked(comment));

        if (comment.commentVote != null) {
            if (comment.commentVote.vote) {
                binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
            } else {
                binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
            }
        }

        binding.imageViewUpVote.setOnClickListener(view -> {
            if (comment.commentVote == null) {
                listener.upvote(comment);
            } else {
                Toast.makeText(holder.itemView.getContext(), "Va?? glas je ve?? zabele??en", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewDownVote.setEnabled(false);
            binding.imageViewUpVote.setEnabled(false);
        });

        binding.imageViewDownVote.setOnClickListener(view -> {
            if (comment.commentVote == null) {
                listener.downVote(comment);

            } else {
                Toast.makeText(holder.itemView.getContext(), "Va?? glas je ve?? zabele??en", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewDownVote.setEnabled(false);
            binding.imageViewUpVote.setEnabled(false);
        });

    }

    @Override
    public String getCommentsId() {
        return comment.id;
    }

    @Override
    public void updateUpvote(){
        YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewUpVote);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positiveVotes + 1));
        binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
    }

    @Override
    public void updateDownvote(){
        YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewDownVote);
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negativeVotes + 1));
        binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
    }
}
