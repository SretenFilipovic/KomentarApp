package com.cubes.komentarapp.ui.tools.listeners;

public interface NewsDetailListener {

    void onAllCommentsClicked(int newsId);

    void onLeaveCommentClicked(String newsId);

    void onTagClicked(int tagId, String tagTitle);

    void onNewsClicked(int newsId, String newsTitle);

}
