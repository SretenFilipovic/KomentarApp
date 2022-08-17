package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public class RvItemDetailWebView implements RvItem {

    private News news;

    public RvItemDetailWebView(News news) {
        this.news = news;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailWebViewBinding binding = (RvItemNewsDetailWebViewBinding) holder.binding;

        binding.webView.loadUrl(DataContainer.BASE_URL + "api/newswebview?id=" + news.id + "&version=2");
    }
}
