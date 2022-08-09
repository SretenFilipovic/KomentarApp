package com.cubes.komentarapp.ui.main.home.headnews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.CategoryNews;
import com.cubes.komentarapp.data.model.NewsList;
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

public class HeadNewsAdapter extends RecyclerView.Adapter<HeadNewsAdapter.HeadNewsViewHolder> {

    private ArrayList<RvItemHead> items = new ArrayList<>();

    public HeadNewsAdapter() {
    }

    @NonNull
    @Override
    public HeadNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
            case 4:
                binding = RvItemHeadSliderBinding.inflate(inflater, parent, false);
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


    public void setData(NewsList response) {

        items.add(new RvItemHeadSlider(response.slider));
        items.add(new RvItemHeadTop(response.top));
        items.add(new RvItemHeadMostRead(response.latest, response.most_read, response.most_comented));
        items.add(new RvItemHeadCategory(response.category, "Sport"));
        items.add(new RvItemHeadEditorsChoiceSlider(response.editors_choice));
        items.add(new RvItemHeadVideo(response.videos));

        for (CategoryNews category : response.category) {
            if (!category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategory(response.category, category.title));
            }
        }

        notifyDataSetChanged();
    }

    public class HeadNewsViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public HeadNewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
