package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;

public class RvItemDetailWebView implements RvItemDetail {

    private NewsDetail news;

    public RvItemDetailWebView(NewsDetail news) {
        this.news = news;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_news_detail_web_view;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailWebViewBinding binding = (RvItemNewsDetailWebViewBinding) holder.binding;

        binding.webView.loadUrl(DataContainer.BASE_URL + "api/newswebview?id=" + news.id + "&version=2");
    }

}
