package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsCategoryAdapter;

import java.util.ArrayList;

public class RvItemHeadCategory implements RvItemHead{

    private ArrayList<News> list;
    private HeadNewsCategoryAdapter adapter;
    private String categoryName;

    public RvItemHeadCategory(ArrayList<News> list, String categoryName) {
        this.list = list;
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

        adapter.setNewsListener(news ->                 DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(News response) {
                News newsDetails = response;

                Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                i.putExtra("news",newsDetails);
                holder.itemView.getContext().startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }));
        binding.recyclerView.setAdapter(adapter);

    }

}
