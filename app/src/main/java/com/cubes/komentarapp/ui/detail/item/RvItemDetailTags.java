package com.cubes.komentarapp.ui.detail.item;

import android.view.View;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.data.model.Tags;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;
import com.cubes.komentarapp.ui.detail.NewsDetailTagAdapter;

import java.util.ArrayList;

public class RvItemDetailTags implements RvItemDetail{

    private ArrayList<Tags> tags;

    public RvItemDetailTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailTagsAndNewsBinding binding = (RvItemNewsDetailTagsAndNewsBinding) holder.binding;

        if (tags == null || tags.size()==0){
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        }
        else{
            binding.textViewTitle.setText(R.string.text_tagovi);

            binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
            binding.recyclerView.setAdapter(new NewsDetailTagAdapter(tags));
        }

    }
}
