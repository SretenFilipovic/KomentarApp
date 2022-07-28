package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.ui.comments.PostReplyActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

// ovaj adapter se koristi za prikaz istaknutih komentara u NewsDetail aktivitiju
// setuje se na RV u RvItemDetailComments

public class NewsDetailCommentsAdapter extends RecyclerView.Adapter<NewsDetailCommentsAdapter.CommentsHolder>{

    private ArrayList<Comments> commentsList;

    public NewsDetailCommentsAdapter(ArrayList<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemCommentBinding binding =
                RvItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {

        Comments comment = commentsList.get(position);

        holder.binding.textViewName.setText(comment.name);
        holder.binding.textViewContent.setText(comment.content);
        holder.binding.textViewCreatedAt.setText(comment.created_at);
        holder.binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
        holder.binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

        holder.binding.imageViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("comment", comment);
                holder.itemView.getContext().startActivity(i);
            }
        });
        holder.binding.textViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                i.putExtra("comment", comment);
                holder.itemView.getContext().startActivity(i);
            }
        });

        holder.binding.imageViewUpVote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!comment.isVoted){
                    DataRepository.getInstance().upvoteComment(comment.id, true);
                    YoYo.with(Techniques.Tada).duration(1000).playOn(holder.binding.imageViewUpVote);
                    holder.binding.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes +1));
                    holder.binding.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                    comment.isVoted = true;
                }
                else{
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.binding.imageViewDownVote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!comment.isVoted){
                    DataRepository.getInstance().downvoteComment(comment.id, true);
                    YoYo.with(Techniques.Tada).duration(1000).playOn(holder.binding.imageViewDownVote);
                    holder.binding.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes +1));
                    holder.binding.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                    comment.isVoted = true;
                }
                else{
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{

        public RvItemCommentBinding binding;

        public CommentsHolder(@NonNull RvItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
