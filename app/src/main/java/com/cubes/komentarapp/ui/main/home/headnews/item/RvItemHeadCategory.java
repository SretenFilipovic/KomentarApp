package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.CategoryHomePage;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsCategoryAdapter;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemHeadCategory implements RvItemHead{

    private ArrayList<News> list;
    private CategoryHomePage category;
    private String categoryName;

    public RvItemHeadCategory(ArrayList<CategoryHomePage> fromCategoryList, String categoryName) {
        this.categoryName = categoryName;

        for(CategoryHomePage cat : fromCategoryList){
            if (cat.title.equalsIgnoreCase(categoryName)){
                this.category = cat;
                this.list = cat.news;
            }
        }
    }

    @Override
    public int getType() {
        if (categoryName.equalsIgnoreCase("Sport")){
            return 3;
        }
        else{
            return 6;
        }
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        HeadNewsCategoryAdapter adapter = new HeadNewsCategoryAdapter(holder.itemView.getContext(), list, category);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                holder.itemView.getContext().startActivity(i);
            }
        });

        binding.recyclerView.setAdapter(adapter);
    }


}
