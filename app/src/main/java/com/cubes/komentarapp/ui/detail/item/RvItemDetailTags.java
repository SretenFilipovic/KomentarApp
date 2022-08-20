package com.cubes.komentarapp.ui.detail.item;

import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.api.TagsApi;
import com.cubes.komentarapp.data.model.domain.Tags;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.detail.NewsDetailTagAdapter;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

public class RvItemDetailTags implements RvItemDetail {

    private final ArrayList<Tags> tags;
    private final NewsDetailListener listener;

    public RvItemDetailTags(ArrayList<Tags> tags, NewsDetailListener tagListener) {
        this.tags = tags;
        this.listener = tagListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_news_detail_tags;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailTagsBinding binding = (RvItemNewsDetailTagsBinding) holder.binding;

        if (tags == null || tags.size() == 0) {
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.textViewTitle.setText(R.string.text_tagovi);

            binding.recyclerView.setLayoutManager(new FlexboxLayoutManager(binding.getRoot().getContext()));
            NewsDetailTagAdapter adapter = new NewsDetailTagAdapter();
            binding.recyclerView.setAdapter(adapter);

            adapter.setData(tags, listener);
        }
    }


}
