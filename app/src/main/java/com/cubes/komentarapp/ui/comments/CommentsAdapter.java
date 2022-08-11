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
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.ui.tools.CommentsListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private final ArrayList<Comments> allComments = new ArrayList<>();
    private CommentsListener commentsListener;

    public CommentsAdapter() {
    }

    public CommentsAdapter(ArrayList<Comments> commentsList) {
        for (Comments comment : commentsList) {
            allComments.add(comment);
            addChildren(comment.children);
        }
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;
            binding = RvItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentsAdapter.CommentsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {
        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;
        Comments comment = allComments.get(position);

        if (!comment.parent_comment.equals("0")) {
            setIndent(binding.rootLayout);
        }

        binding.textViewName.setText(comment.name);
        binding.textViewContent.setText(comment.content);
        binding.textViewCreatedAt.setText(comment.created_at);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

        binding.imageViewReply.setOnClickListener(view -> {
            commentsListener.onCommentsClicked(comment);
        });
        binding.textViewReply.setOnClickListener(view -> {
            commentsListener.onCommentsClicked(comment);
        });

        binding.imageViewUpVote.setOnClickListener(view -> {
            if (!comment.isVoted) {
                DataRepository.getInstance().upvoteComment(comment.id);
                setUpvoteData(comment, binding);
            }
            else {
                Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageViewDownVote.setOnClickListener(view -> {
            if (!comment.isVoted) {
                DataRepository.getInstance().downvoteComment(comment.id);
                setDownvoteData(comment, binding);
            }
            else {
                Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void setCommentListener(CommentsListener commentsListener) {
        this.commentsListener = commentsListener;
    }

    public void setData(ArrayList<Comments> responseComments) {
        for (Comments comment : responseComments) {
            allComments.add(comment);
            addChildren(comment.children);
        }
        notifyDataSetChanged();
    }

    public void setUpvoteData(Comments comment, RvItemCommentBinding binding){
        YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewUpVote);
        binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes + 1));
        binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
        comment.isVoted = true;
    }

    public void setDownvoteData(Comments comment, RvItemCommentBinding binding){
        YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewDownVote);
        binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes + 1));
        binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
        comment.isVoted = true;
    }

    private void addChildren(ArrayList<Comments> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (Comments comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    private void setIndent(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(50, 0, 0, 0);
            view.requestLayout();
        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CommentsHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
