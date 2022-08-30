package com.cubes.komentarapp.ui.main.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.LoadingNewsListener;

public class RvItemNewsLoading implements RvItemNews {

    private final LoadingNewsListener listener;

    public RvItemNewsLoading(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(ViewHolder holder) {
            listener.loadMoreNews();
    }

}

