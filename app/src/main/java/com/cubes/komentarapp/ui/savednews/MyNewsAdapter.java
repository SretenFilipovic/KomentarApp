package com.cubes.komentarapp.ui.savednews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.RvItemSavedNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.comments.item.RvItemComments;
import com.cubes.komentarapp.ui.savednews.item.RvItemMyNewsView;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.MyNewsListener;

import java.util.ArrayList;
import java.util.List;

public class MyNewsAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final ArrayList<RvItemMyNewsView> items = new ArrayList<>();
    private int[] newsIdList;

    public MyNewsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemSavedNewsBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setData(List<MyNews> myNews, MyNewsListener listener) {

        newsIdList = MethodsClass.initMyNewsIdList(myNews);

        for (MyNews news : myNews){
            items.add(new RvItemMyNewsView(news, newsIdList, listener));
        }

        notifyDataSetChanged();
    }
}
