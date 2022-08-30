package com.cubes.komentarapp.ui.main.item;

import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

public class RvItemNewsLoading implements RvItemNews {

    private LoadingNewsListener listener;
    private boolean isLoading;
    private boolean isFinished;


    public RvItemNewsLoading(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(ViewHolder holder) {

 //       RvItemLoadingBinding binding = (RvItemLoadingBinding) holder.binding;

//        if (isFinished) {
//            binding.progressBar.setVisibility(View.GONE);
//            binding.textView.setVisibility(View.GONE);
//        }
//
//        if (!isLoading & !isFinished & listener != null) {
//            isLoading = true;
            listener.loadMoreNews();
//        }

    }
//
//    public void setFinished(boolean finished) {
//        isFinished = finished;
//    }

}

