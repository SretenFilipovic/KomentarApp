package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.CategoryHomePage;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsCategoryAdapter;

import java.util.ArrayList;

public class RvItemHeadCategory implements RvItemHead{

    private ArrayList<News> list;
    private HeadNewsCategoryAdapter adapter;
    private String categoryName;
    private ArrayList<CategoryHomePage> fromCategoryList;

    public RvItemHeadCategory(String categoryName) {
        fromCategoryList = new ArrayList<>();
        loadData();
        this.categoryName = categoryName;
    }

    @Override
    public int getType() {
        if (categoryName.equalsIgnoreCase("Sport")){
            return 3;
        }
        else if (categoryName.equalsIgnoreCase("Aktuelno")){
            return 6;
        }
        else if (categoryName.equalsIgnoreCase("Politika")){
            return 7;
        }
        else if (categoryName.equalsIgnoreCase("Svet")){
            return 8;
        }
        else if (categoryName.equalsIgnoreCase("Hronika")){
            return 9;
        }
        else if (categoryName.equalsIgnoreCase("Društvo")){
            return 10;
        }
        else if (categoryName.equalsIgnoreCase("Ekonomija")){
            return 11;
        }
        else if (categoryName.equalsIgnoreCase("Stil života")){
            return 12;
        }
        else if (categoryName.equalsIgnoreCase("Kultura")){
            return 13;
        }
        else if (categoryName.equalsIgnoreCase("Zabava")){
            return 14;
        }
        else if (categoryName.equalsIgnoreCase("Srbija")){
            return 15;
        }
        else if (categoryName.equalsIgnoreCase("Beograd")){
            return 16;
        }
        else if (categoryName.equalsIgnoreCase("Region")){
            return 17;
        }
        else{
            return 17;
        }
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new HeadNewsCategoryAdapter(holder.itemView.getContext(), list);

        adapter.setNewsListener(news -> DataRepository.getInstance().getNewsDetails(holder.itemView.getContext(), news));
        binding.recyclerView.setAdapter(adapter);

    }

    private void loadData(){

        DataRepository.getInstance().loadHeadNewsData(new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNews response) {
                fromCategoryList = response.data.category;

                list = getNewsFromCategoryList(categoryName);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public ArrayList<News> getNewsFromCategoryList(String category){
        ArrayList<News> allNewsList = new ArrayList<>();
        ArrayList<News> categoryList = new ArrayList<>();

        for(CategoryHomePage cat : fromCategoryList){
            allNewsList = cat.news;

            for (News news : allNewsList) {

                if(news.category.name.equalsIgnoreCase(category)){
                    categoryList.add(news);
                }
            }
        }
        return categoryList;
    }
}
