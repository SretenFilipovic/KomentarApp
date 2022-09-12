package com.cubes.komentarapp.ui.tools.listeners;

public interface NewsListener {

    void onNewsClicked(int newsId, int[] newsListId);

    void onShareNewsClicked(String newsUrl);

    void onCommentNewsClicked(int newsId);
}
