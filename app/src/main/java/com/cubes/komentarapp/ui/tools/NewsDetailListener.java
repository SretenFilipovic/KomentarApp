package com.cubes.komentarapp.ui.tools;

public interface NewsDetailListener {

    void onAllCommentsClicked(int newsId);

    void onLeaveCommentClicked(String newsId);

    void onTagClicked(int tagId);

    void onNewsClicked(int newsId);

}
