package com.cubes.komentarapp.ui.comments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentChildBinding;
import com.cubes.komentarapp.databinding.RvItemCommentParentBinding;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private ArrayList<Comments> allComments;

    public CommentsAdapter(ArrayList<Comments> commentsList) {
        this.allComments = new ArrayList<>();

        for (Comments comment : commentsList){
            allComments.add(comment);
            addChildren(comment.children);
        }
    }

    private void addChildren(ArrayList<Comments> comments) {

        if (comments != null && !comments.isEmpty()) {
            for (Comments comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){
            RvItemCommentParentBinding binding =
                    RvItemCommentParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CommentsAdapter.CommentsHolder(binding);
        }
        else{
            RvItemCommentChildBinding binding =
                    RvItemCommentChildBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CommentsAdapter.CommentsHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {

        Comments comment = allComments.get(position);

        if(allComments.get(position).parent_comment.equals("0")){

            holder.bindingParentComment.textViewName.setText(comment.name);
            holder.bindingParentComment.textViewContent.setText(comment.content);
            holder.bindingParentComment.textViewCreatedAt.setText(comment.created_at);
            holder.bindingParentComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
            holder.bindingParentComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

            holder.bindingParentComment.imageViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("commentId", comment.id);
                    i.putExtra("commentName", comment.news);
                    holder.itemView.getContext().startActivity(i);
                }
            });
            holder.bindingParentComment.textViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("commentId", comment.id);
                    i.putExtra("commentName", comment.news);
                    holder.itemView.getContext().startActivity(i);
                }
            });

            holder.bindingParentComment.imageViewUpVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!comment.isVoted){
                        DataRepository.getInstance().upvoteComment(comment.id, true);

                        YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingParentComment.imageViewUpVote);

                        holder.bindingParentComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes +1));
                        holder.bindingParentComment.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                        comment.isVoted = true;

                    }
                    else{
                        Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.bindingParentComment.imageViewDownVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!comment.isVoted){
                        DataRepository.getInstance().downvoteComment(comment.id, true);

                        YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingParentComment.imageViewDownVote);

                        holder.bindingParentComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes +1));
                        holder.bindingParentComment.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                        comment.isVoted = true;

                    }
                    else{
                        Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        else {
            holder.bindingChildComment.textViewName.setText(comment.name);
            holder.bindingChildComment.textViewContent.setText(comment.content);
            holder.bindingChildComment.textViewCreatedAt.setText(comment.created_at);
            holder.bindingChildComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
            holder.bindingChildComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

            holder.bindingChildComment.imageViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("commentId", comment.id);
                    i.putExtra("commentName", comment.news);
                    holder.itemView.getContext().startActivity(i);
                }
            });
            holder.bindingChildComment.textViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("commentId", comment.id);
                    i.putExtra("commentName", comment.news);
                    holder.itemView.getContext().startActivity(i);
                }
            });

            holder.bindingChildComment.imageViewUpVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!comment.isVoted){
                        DataRepository.getInstance().upvoteComment(comment.id, true);

                        YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingChildComment.imageViewUpVote);

                        holder.bindingChildComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes +1));
                        holder.bindingChildComment.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                        comment.isVoted = true;

                    }
                    else{
                        Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.bindingChildComment.imageViewDownVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!comment.isVoted){
                        DataRepository.getInstance().downvoteComment(comment.id, true);

                        YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingChildComment.imageViewDownVote);

                        holder.bindingChildComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes +1));
                        holder.bindingChildComment.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                        comment.isVoted = true;

                    }
                    else{
                        Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(allComments.get(position).parent_comment.equals("0")){
            return 0;
        }
        else {
            return 1;
        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{

        public RvItemCommentParentBinding bindingParentComment;
        public RvItemCommentChildBinding bindingChildComment;


        public CommentsHolder(@NonNull RvItemCommentParentBinding binding) {
            super(binding.getRoot());
            this.bindingParentComment = binding;
        }

        public CommentsHolder(@NonNull RvItemCommentChildBinding binding) {
            super(binding.getRoot());
            this.bindingChildComment = binding;
        }

    }

}
