package com.cubes.komentarapp.ui.detail.item;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsBinding;
import com.cubes.komentarapp.ui.comments.CommentsAdapter;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class RvItemDetailComments implements RvItemDetail {

    private final ArrayList<Comments> comments;
    private News news;
    private NewsDetailListener listener;

    public RvItemDetailComments(News news, ArrayList<Comments> commentList, NewsDetailListener listener) {
        this.news = news;
        this.comments = commentList;
        this.listener = listener;
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
                listener.onAllCommentsClicked(news.id);;
            }
        });

        binding.frameLayoutShowAll.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                listener.onAllCommentsClicked(news.id);
            }
        });

        binding.textViewButtonCount.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                listener.onAllCommentsClicked(news.id);
            }
        });

        binding.buttonLeaveComment.setOnClickListener(view -> listener.onLeaveCommentClicked(String.valueOf(news.id)));

    }

}
