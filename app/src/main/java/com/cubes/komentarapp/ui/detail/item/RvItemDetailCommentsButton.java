package com.cubes.komentarapp.ui.detail.item;

import android.widget.Toast;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsShowAllBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class RvItemDetailCommentsButton implements RvItem {

    private final News news;
    private final NewsDetailListener listener;

    public RvItemDetailCommentsButton(News news, NewsDetailListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_news_detail_comments_show_all;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailCommentsShowAllBinding binding = (RvItemNewsDetailCommentsShowAllBinding) holder.binding;


        binding.textViewButtonCount.setText(String.valueOf(news.comments_count));

        binding.textViewShowAllComments.setOnClickListener(view -> {
            if (news.comments_count == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.frameLayoutShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                listener.onAllCommentsClicked(news.id);
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


    }

}
