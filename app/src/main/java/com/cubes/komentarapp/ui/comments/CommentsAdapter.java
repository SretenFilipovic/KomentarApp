package com.cubes.komentarapp.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private ArrayList<Comments> comments = new ArrayList<>();
    private CommentsListener commentsListener;

    public CommentsAdapter() {
    }

    public CommentsAdapter(ArrayList<Comments> commentsList) {
        this.comments = commentsList;
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemCommentBinding.inflate(inflater, parent, false);
        return new CommentsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {
        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;
        Comments comment = comments.get(position);

        if (!comment.parent_comment.equals("0")) {
            setIndent(binding.rootLayout);
        }

        if (comment.commentVote != null) {
            if (comment.commentVote.vote) {
                binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
            } else {
                binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
            }
        }

        binding.textViewName.setText(comment.name);
        binding.textViewContent.setText(comment.content);
        binding.textViewCreatedAt.setText(comment.created_at);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

        binding.imageViewReply.setOnClickListener(view -> commentsListener.onCommentsClicked(comment));
        binding.textViewReply.setOnClickListener(view -> commentsListener.onCommentsClicked(comment));

        binding.imageViewUpVote.setOnClickListener(view -> {
            if (comment.commentVote == null) {
                commentsListener.upvote(comment.id);

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
                commentsListener.downVote(comment.id);

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

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setCommentListener(CommentsListener commentsListener) {
        this.commentsListener = commentsListener;
    }

    private void setIndent(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(50, 0, 0, 0);
            view.requestLayout();
        }
    }

    public void updateList(ArrayList<Comments> commentsData) {
        comments.addAll(commentsData);
        notifyDataSetChanged();
    }

    public static class CommentsHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CommentsHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
