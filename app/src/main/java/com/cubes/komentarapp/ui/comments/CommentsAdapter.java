package com.cubes.komentarapp.ui.comments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemCommentChildBinding;
import com.cubes.komentarapp.databinding.RvItemCommentParentBinding;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private ArrayList<Comments> allComments = new ArrayList<>();

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

        if (viewType == 0) {
            binding = RvItemCommentParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            binding = RvItemCommentChildBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        return new CommentsAdapter.CommentsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {

        Comments comment = allComments.get(position);

        if (allComments.get(position).parent_comment.equals("0")) {
            RvItemCommentParentBinding binding = (RvItemCommentParentBinding) holder.binding;

            binding.textViewName.setText(comment.name);
            binding.textViewContent.setText(comment.content);
            binding.textViewCreatedAt.setText(comment.created_at);
            binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
            binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

            binding.imageViewReply.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("commentName", comment.news);
                holder.itemView.getContext().startActivity(i);
            });
            binding.textViewReply.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("commentName", comment.news);
                holder.itemView.getContext().startActivity(i);
            });

            binding.imageViewUpVote.setOnClickListener(view -> {
                if (!comment.isVoted) {
                    DataRepository.getInstance().upvoteComment(comment.id, true);

                    YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewUpVote);

                    binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes + 1));
                    binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                    comment.isVoted = true;

                } else {
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            });

            binding.imageViewDownVote.setOnClickListener(view -> {
                if (!comment.isVoted) {
                    DataRepository.getInstance().downvoteComment(comment.id, true);

                    YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewDownVote);

                    binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes + 1));
                    binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                    comment.isVoted = true;

                } else {
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            RvItemCommentChildBinding binding = (RvItemCommentChildBinding) holder.binding;

            binding.textViewName.setText(comment.name);
            binding.textViewContent.setText(comment.content);
            binding.textViewCreatedAt.setText(comment.created_at);
            binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
            binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

            binding.imageViewReply.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("commentName", comment.news);
                holder.itemView.getContext().startActivity(i);
            });
            binding.textViewReply.setOnClickListener(view -> {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("commentName", comment.news);
                holder.itemView.getContext().startActivity(i);
            });

            binding.imageViewUpVote.setOnClickListener(view -> {
                if (!comment.isVoted) {
                    DataRepository.getInstance().upvoteComment(comment.id, true);

                    YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewUpVote);

                    binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes + 1));
                    binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                    comment.isVoted = true;

                } else {
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            });

            binding.imageViewDownVote.setOnClickListener(view -> {
                if (!comment.isVoted) {
                    DataRepository.getInstance().downvoteComment(comment.id, true);

                    YoYo.with(Techniques.Tada).duration(1000).playOn(binding.imageViewDownVote);

                    binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes + 1));
                    binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                    comment.isVoted = true;

                } else {
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void setData(ArrayList<Comments> responseComments) {
        for (Comments comment : responseComments) {
            allComments.add(comment);
            addChildren(comment.children);
        }
        notifyDataSetChanged();
    }

    private void addChildren(ArrayList<Comments> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (Comments comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (allComments.get(position).parent_comment.equals("0")) {
            return 0;
        } else {
            return 1;
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
