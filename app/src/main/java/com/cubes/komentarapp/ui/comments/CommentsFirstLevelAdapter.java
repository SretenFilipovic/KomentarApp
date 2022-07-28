package com.cubes.komentarapp.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.data.model.Comments;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

// Adapter koji sluzi za prikaz komentara prvog nivoa (komentar koji se dodaje direkno na vest, a ne kao odgovor na drugi komentar)
// Setuje se u RV u CommentsActivity

public class CommentsFirstLevelAdapter extends RecyclerView.Adapter<CommentsFirstLevelAdapter.CommentsHolder> {

    private ArrayList<Comments> commentsList;
    private Context context;

    public CommentsFirstLevelAdapter(Context context, ArrayList<Comments> commentsList) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RvItemCommentBinding binding =
                    RvItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CommentsFirstLevelAdapter.CommentsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {

            Comments comment = commentsList.get(position);

            holder.bindingComment.textViewName.setText(comment.name);
            holder.bindingComment.textViewContent.setText(comment.content);
            holder.bindingComment.textViewCreatedAt.setText(comment.created_at);
            holder.bindingComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes));
            holder.bindingComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes));

            holder.bindingComment.imageViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("comment", comment);
                    holder.itemView.getContext().startActivity(i);
                }
            });
            holder.bindingComment.textViewReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), PostReplyActivity.class);
                    i.putExtra("comment", comment);
                    holder.itemView.getContext().startActivity(i);
                }
            });

        holder.bindingComment.imageViewUpVote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!comment.isVoted){
                    DataRepository.getInstance().upvoteComment(comment.id, true);

                    // Animacija cisto da bude lepse
                    YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingComment.imageViewUpVote);

                    holder.bindingComment.textViewUpVoteCount.setText(String.valueOf(comment.positive_votes +1));
                    holder.bindingComment.imageViewUpVote.setImageResource(R.drawable.ic_thumbs_up_voted);
                    comment.isVoted = true;

                    //Proba sa SharedPreferences
                    //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    //comment.isVoted = prefs.edit().putBoolean("isVoted", true).commit();
                }
                else{
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.bindingComment.imageViewDownVote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!comment.isVoted){
                    DataRepository.getInstance().downvoteComment(comment.id, true);
                    YoYo.with(Techniques.Tada).duration(1000).playOn(holder.bindingComment.imageViewDownVote);
                    holder.bindingComment.textViewDownVoteCount.setText(String.valueOf(comment.negative_votes +1));
                    holder.bindingComment.imageViewDownVote.setImageResource(R.drawable.ic_thumbs_down_voted);
                    comment.isVoted = true;

                    //Proba sa SharedPreferences
                    //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    //comment.isVoted = prefs.edit().putBoolean("isVoted", true).commit();
                }
                else{
                    Toast.makeText(holder.itemView.getContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
                }
            }
        });

            if (comment.children != null){
                holder.bindingComment.recyclerViewChildren.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                holder.bindingComment.recyclerViewChildren.setAdapter(new CommentsSecondLevelAdapter(context, comment.children));
            }

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{

        public RvItemCommentBinding bindingComment;

        public CommentsHolder(@NonNull RvItemCommentBinding binding) {
            super(binding.getRoot());
            this.bindingComment = binding;
        }
    }

}
