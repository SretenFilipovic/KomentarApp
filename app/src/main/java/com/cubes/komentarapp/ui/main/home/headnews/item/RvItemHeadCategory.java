package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.CategoryNews;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsCategoryAdapter;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemHeadCategory implements RvItemHead {

    private ArrayList<News> list;
    private CategoryNews category;
    private String categoryName;
    private final NewsListener listener;

    public RvItemHeadCategory(ArrayList<CategoryNews> fromCategoryList, String categoryName, NewsListener listener) {
        this.categoryName = categoryName;
        this.listener = listener;

        for (CategoryNews cat : fromCategoryList) {
            if (cat.title.equalsIgnoreCase(categoryName)) {
                this.category = cat;
                this.list = cat.news;
            }
        }
    }

    @Override
    public int getType() {
        if (categoryName.equalsIgnoreCase("Sport")) {
            return 3;
        } else {
            return 6;
        }
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        HeadNewsCategoryAdapter adapter = new HeadNewsCategoryAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setCategoryData(list, category);
        adapter.setNewsListener(listener);
    }


}
