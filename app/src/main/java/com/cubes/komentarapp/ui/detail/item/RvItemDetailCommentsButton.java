package com.cubes.komentarapp.ui.detail.item;

import android.widget.Toast;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsShowAllBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class RvItemDetailCommentsButton implements RvItemDetail {

    private final NewsDetail news;
    private final NewsDetailListener listener;

    public RvItemDetailCommentsButton(NewsDetail news, NewsDetailListener listener) {
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

        String commentCount = "SVI KOMENTARI (" + news.commentsCount +")";

        binding.buttonShowAll.setText(commentCount);

        binding.buttonShowAll.setOnClickListener(view -> {
            if (news.commentsCount == 0) {
                YoYo.with(Techniques.Shake).duration(500).playOn(binding.buttonShowAll);
                Toast.makeText(holder.itemView.getContext(), "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
            } else {
                listener.onAllCommentsClicked(news.id);
            }
        });


    }


}
