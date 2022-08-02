package com.cubes.komentarapp.ui.detail.item;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.comments.CommentsAdapter;
import com.cubes.komentarapp.ui.comments.PostCommentActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;
import com.cubes.komentarapp.ui.tools.CommentsListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class RvItemDetailComments implements RvItemDetail {

    private final ArrayList<Comments> comments;
    private News news;

    public RvItemDetailComments(News news, ArrayList<Comments> commentList) {
        this.news = news;
        this.comments = commentList;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailCommentsBinding binding = (RvItemNewsDetailCommentsBinding) holder.binding;

        binding.textViewCommentCount.setText("(" + news.comments_count + ")");
        binding.textViewButtonCount.setText(String.valueOf(news.comments_count));

        if (comments == null || comments.size() == 0) {
            binding.textViewNoComments.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            binding.recyclerView.setAdapter(new CommentsAdapter(comments));
        }

        binding.textViewShowAllComments.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(holder.itemView.getContext(), CommentsActivity.class);
                i.putExtra("news", news.id);
                holder.itemView.getContext().startActivity(i);
            }
        });

        binding.frameLayoutShowAll.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(holder.itemView.getContext(), CommentsActivity.class);
                i.putExtra("news", news.id);
                holder.itemView.getContext().startActivity(i);
            }
        });

        binding.textViewButtonCount.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(holder.itemView.getContext(), CommentsActivity.class);
                i.putExtra("news", news.id);
                holder.itemView.getContext().startActivity(i);
            }
        });

        binding.buttonLeaveComment.setOnClickListener(view -> {
            Intent i = new Intent(holder.itemView.getContext(), PostCommentActivity.class);
            i.putExtra("newsId", String.valueOf(news.id));
            holder.itemView.getContext().startActivity(i);
        });

    }

}
