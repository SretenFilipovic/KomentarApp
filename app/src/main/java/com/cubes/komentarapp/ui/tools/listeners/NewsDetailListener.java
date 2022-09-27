package com.cubes.komentarapp.ui.tools.listeners;

public interface NewsDetailListener {

    void onAllCommentsClicked(int newsId);

    void onLeaveCommentClicked(String newsId);

    void onTagClicked(int tagId, String tagTitle);

    void onNewsClicked(int newsId, String newsTitle, int[] newsListId);

    void onShareNewsClicked(String newsUrl);

    void onCommentNewsClicked(int newsId);

    void onSaveNewsClicked(int newsId, String newsTitle);

    boolean onShowMoreClicked(int newsId);
}
