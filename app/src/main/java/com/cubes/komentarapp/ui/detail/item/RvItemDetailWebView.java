package com.cubes.komentarapp.ui.detail.item;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.source.remote.networking.NewsRetrofit;
import com.cubes.komentarapp.databinding.RvItemNewsDetailWebViewBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.WebViewListener;

public class RvItemDetailWebView implements RvItemDetail {

    private final NewsDetail news;
    private final WebViewListener listener;

    public RvItemDetailWebView(NewsDetail news, WebViewListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_news_detail_web_view;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailWebViewBinding binding = (RvItemNewsDetailWebViewBinding) holder.binding;

        String url = NewsRetrofit.BASE_URL + "api/newswebview?id=" + news.id + "&version=2";

        binding.webView.loadUrl(url);

        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                listener.onWebViewLoaded();
            }
        });

    }

}
