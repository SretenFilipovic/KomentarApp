package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
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
    private ArrayList<News> sliderList;
    private ArrayList<News> topList;
    private ArrayList<News> editorsChoiceList;
    private ArrayList<News> videosList;
    private ArrayList<News> mostReadList;
    private ArrayList<News> latestList;
    private ArrayList<News> mostCommentedList;

    public HeadNewsAdapter(Context context) {

        items = new ArrayList<>();
        this.context = context;

        loadData();

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


    public class HeadNewsViewHolder extends RecyclerView.ViewHolder{

        public ViewBinding binding;

        public HeadNewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void loadData(){

        DataRepository.getInstance().loadHeadNewsData(new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNews response) {
                sliderList = response.data.slider;
                topList = response.data.top;
                editorsChoiceList = response.data.editors_choice;
                videosList = response.data.videos;
                mostReadList = response.data.most_read;
                latestList = response.data.latest;
                mostCommentedList = response.data.most_comented;

                items.add(new RvItemHeadSlider(context, sliderList));
                items.add(new RvItemHeadTop(topList));
                items.add(new RvItemHeadMostRead(context, latestList, mostReadList, mostCommentedList));
                items.add(new RvItemHeadCategory("Sport"));
                items.add(new RvItemHeadEditorsChoiceSlider(context, editorsChoiceList));
                items.add(new RvItemHeadVideo(videosList));
                items.add(new RvItemHeadCategory("Aktuelno"));
                items.add(new RvItemHeadCategory("Politika"));
                items.add(new RvItemHeadCategory("Svet"));
                items.add(new RvItemHeadCategory("Hronika"));
                items.add(new RvItemHeadCategory("Društvo"));
                items.add(new RvItemHeadCategory("Ekonomija"));
                items.add(new RvItemHeadCategory("Stil života"));
                items.add(new RvItemHeadCategory("Kultura"));
                items.add(new RvItemHeadCategory("Zabava"));
                items.add(new RvItemHeadCategory("Srbija"));
                items.add(new RvItemHeadCategory("Beograd"));
                items.add(new RvItemHeadCategory("Region"));

                notifyDataSetChanged();

                Log.d("TAG", "Home news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("TAG", "Home news load data failure");

            }
        });
    }

}
