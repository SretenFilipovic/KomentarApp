package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.CategoryHomePage;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategory;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadEditorsChoiceSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadMostRead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadTop;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideo;

import java.util.ArrayList;

// HeadNewsAdapter sluzi za prikaz Naslovne stranice
// setuje se na RV u HomePageHeadNewsFragment-u

public class HeadNewsAdapter extends RecyclerView.Adapter<HeadNewsAdapter.HeadNewsViewHolder> {

    private Context context;
    private ArrayList<RvItemHead> items;
    private ArrayList<CategoryHomePage> fromCategoryList;

    public HeadNewsAdapter(Context context, ArrayList<News> sliderList, ArrayList<News> topList,
                           ArrayList<News> editorsChoiceList, ArrayList<News> videosList,
                           ArrayList<News> mostReadList, ArrayList<News> latestList, ArrayList<News> mostCommentedList, ArrayList<CategoryHomePage> fromCategoryList) {

        items = new ArrayList<>();
        this.context = context;
        this.fromCategoryList = fromCategoryList;

        items.add(new RvItemHeadSlider(context, sliderList));
        items.add(new RvItemHeadTop(topList));
        items.add(new RvItemHeadMostRead(context, latestList, mostReadList, mostCommentedList));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Sport"), "Sport"));
        items.add(new RvItemHeadEditorsChoiceSlider(context, editorsChoiceList));
        items.add(new RvItemHeadVideo(videosList));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Aktuelno"), "Aktuelno"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Politika"), "Politika"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Svet"), "Svet"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Hronika"), "Hronika"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Društvo"), "Društvo"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Ekonomija"), "Ekonomija"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Stil života"), "Stil života"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Kultura"), "Kultura"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Zabava"), "Zabava"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Srbija"), "Srbija"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Beograd"), "Beograd"));
        items.add(new RvItemHeadCategory(getNewsFromCategoryList("Region"), "Region"));

    }

    @NonNull
    @Override
    public HeadNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 0:
            case 4:
                binding = RvItemHeadSliderBinding.inflate(inflater,parent,false);
                break;
            case 2:
                binding = RvItemHeadMostReadBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemHeadTopBinding.inflate(inflater, parent, false);
        }

        return new HeadNewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNewsViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
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

    public class HeadNewsViewHolder extends RecyclerView.ViewHolder{

        public ViewBinding binding;

        public HeadNewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
