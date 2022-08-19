package com.cubes.komentarapp.ui.comments.item;

import android.widget.Toast;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class RvItemCommentsParent implements RvItem {

    private final Comments comment;
    private final CommentsListener listener;

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

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        binding.textViewName.setText(comment.name);
        binding.textViewContent.setText(comment.content);
        binding.textViewCreatedAt.setText(comment.created_at);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

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

                YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewUpVote);
                binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes + 1));
                binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);

            } else {
                Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewDownVote.setEnabled(false);
            binding.imageViewUpVote.setEnabled(false);
        });

        binding.imageViewDownVote.setOnClickListener(view -> {
            if (comment.commentVote == null) {
                listener.downVote(comment);

                YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewDownVote);
                binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes + 1));
                binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);

            } else {
                Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewDownVote.setEnabled(false);
            binding.imageViewUpVote.setEnabled(false);
        });

    }

}
