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
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategory;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadEditorsChoiceSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadMostRead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadTop;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideo;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class HeadNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItem> items = new ArrayList<>();

    public HeadNewsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


    public void setData(NewsList response, NewsListener listener) {

        items.add(new RvItemHeadSlider(response.slider));
        items.add(new RvItemHeadTop(response.top, listener));
        items.add(new RvItemHeadMostRead(response.latest, response.most_read, response.most_comented));
        items.add(new RvItemHeadCategory(response.category, "Sport", listener));
        items.add(new RvItemHeadEditorsChoiceSlider(response.editors_choice));
        items.add(new RvItemHeadVideo(response.videos, listener));

        for (CategoryNews category : response.category) {
            if (!category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategory(response.category, category.title, listener));
            }
        }

        notifyDataSetChanged();
    }

}
