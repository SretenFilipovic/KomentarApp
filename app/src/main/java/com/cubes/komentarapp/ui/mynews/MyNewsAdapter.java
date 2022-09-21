package com.cubes.komentarapp.ui.mynews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.RvItemSavedNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.mynews.item.RvItemMyNewsView;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.ItemTouchHelperContract;
import com.cubes.komentarapp.ui.tools.listeners.MyNewsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyNewsAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperContract {

    private final ArrayList<RvItemMyNewsView> items = new ArrayList<>();

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


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MyNews> myNews, MyNewsListener listener) {

        int[] newsIdList = MethodsClass.initMyNewsIdList(myNews);

        for (MyNews news : myNews){
            items.add(new RvItemMyNewsView(news, newsIdList, listener));
        }

        notifyDataSetChanged();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<MyNews> getNewList(){

        List<MyNews> list = new ArrayList<>();

        for (int i = 0; i < items.size(); i++){
            list.add(items.get(i).getNews());
        }
        return list;
    }

}
