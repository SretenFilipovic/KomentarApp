package com.cubes.komentarapp.data.source.datarepository;

import android.content.Context;
import android.content.Intent;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.networking.RetrofitService;
import com.cubes.komentarapp.data.source.remote.response.PostComment;
import com.cubes.komentarapp.data.source.remote.response.ResponseCategory;
import com.cubes.komentarapp.data.source.remote.response.ResponseComments;
import com.cubes.komentarapp.data.source.remote.response.ResponseHoroscope;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsDetail;
import com.cubes.komentarapp.data.source.remote.response.ResponseWeather;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {

    private static DataRepository instance;
    private Retrofit retrofit;
    private RetrofitService service;

    private DataRepository() {
        callRetrofit();
    }

    public static DataRepository getInstance()
    {
        if (instance==null)
            instance = new DataRepository();
        return instance;
    }

    public void callRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(DataContainer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    // NEWS LIST LISTENER
    public interface NewsResponseListener {
        void onResponse(ResponseNews response);

        void onFailure(Throwable t);
    }

    //VIDEOS
    public void loadVideoData(int page, NewsResponseListener listener){

        service.getVideoNews(page).enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    public void loadMoreVideos(int page, NewsResponseListener listener){

        service.getVideoNews(page).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    // LATEST

    public void loadLatestData(int page, NewsResponseListener listener){

        service.getLatestNews(page).enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    public void loadMoreLatest(int page, NewsResponseListener listener){

        service.getLatestNews(page).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    // CATEGORIES AND SUBCATEGORIES

    public void loadCategoryData(int id, int page, NewsResponseListener listener){

        service.getNewsForCategories(id, page).enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    public void loadMoreCategoryNews(int id, int page, NewsResponseListener listener){

        service.getNewsForCategories(id, page).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    // SEARCH

    public void loadSearchData(String term, int page, NewsResponseListener listener){

        service.getSearchNews(term,page).enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    public void loadMoreSearchNews(String term, int page, NewsResponseListener listener){

        service.getSearchNews(term, page).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    // TAG NEWS

    public void loadTagData(int id, int page, NewsResponseListener listener){

        service.getTagNews(id,page).enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    public void loadMoreTagNews(int id, int page, NewsResponseListener listener){

        service.getTagNews(id, page).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    // HEAD NEWS

    public void loadHeadNewsData(NewsResponseListener listener){

        service.getHomepageNews().enqueue(new Callback<ResponseNews>() {

            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    // CATEGORY LISTENER
    public interface CategoryResponseListener {
        void onResponse(ResponseCategory response);

        void onFailure(Throwable t);
    }

    public void loadCategoryData(CategoryResponseListener listener){

        service.getCategories().enqueue(new Callback<ResponseCategory>() {

            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    // HOROSCOPE LISTENER
    public interface HoroscopeResponseListener {
        void onResponse(ResponseHoroscope response);

        void onFailure(Throwable t);
    }

    public void loadHoroscopeData(HoroscopeResponseListener listener){

        service.getHoroscope().enqueue(new Callback<ResponseHoroscope>() {

            @Override
            public void onResponse(Call<ResponseHoroscope> call, Response<ResponseHoroscope> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseHoroscope> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    // WEATHER LISTENER
    public interface WeatherResponseListener {
        void onResponse(ResponseWeather response);

        void onFailure(Throwable t);
    }

    public void loadWeatherData(WeatherResponseListener listener){

        service.getWeather().enqueue(new Callback<ResponseWeather>() {

            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    // NEWS DETAILS
    public void getNewsDetails(Context context, News news){

        service.getNewsDetail(news.id).enqueue(new Callback<ResponseNewsDetail>() {
            @Override
            public void onResponse(Call<ResponseNewsDetail> call, Response<ResponseNewsDetail> response) {
                News newsDetails = response.body().data;

                DataRepository.getInstance().loadCommentsData(news.id, new CommentsResponseListener() {
                    @Override
                    public void onResponse(ResponseComments response) {
                        DataContainer.commentList = response.data;

                        Intent i = new Intent(context, NewsDetailActivity.class);
                        i.putExtra("news",newsDetails);
                        context.startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<ResponseNewsDetail> call, Throwable t) {

            }
        });
    }

    // COMMENTS LISTENER
    public interface CommentsResponseListener {
        void onResponse(ResponseComments response);

        void onFailure(Throwable t);
    }

    // LOAD COMMENTS
    public void loadCommentsData(int id, CommentsResponseListener listener){

        service.getComments(id).enqueue(new Callback<ResponseComments>() {

            @Override
            public void onResponse(Call<ResponseComments> call, Response<ResponseComments> response) {
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResponseComments> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

    // UPVOTE COMMENT
    public void upvoteComment(String id, boolean vote){

        service.postUpvote(id, vote).enqueue(new Callback<ResponseComments>() {

            @Override
            public void onResponse(Call<ResponseComments> call, Response<ResponseComments> response) {
            }
            @Override
            public void onFailure(Call<ResponseComments> call, Throwable t) {
            }
        });
    }
    // DOWNVOTE COMMENT
    public void downvoteComment(String id, boolean vote){

        service.postDownvote(id, vote).enqueue(new Callback<ResponseComments>() {

            @Override
            public void onResponse(Call<ResponseComments> call, Response<ResponseComments> response) {
            }
            @Override
            public void onFailure(Call<ResponseComments> call, Throwable t) {
            }
        });
    }

    // POST COMMENT LISTENER
    public interface PostCommentListener {
        void onResponse(PostComment response);

        void onFailure(Throwable t);
    }

    // POST COMMENT
    public void postCommentData(PostComment comment, PostCommentListener listener){

        service.postComment(comment).enqueue(new Callback<PostComment>() {

            @Override
            public void onResponse(Call<PostComment> call, Response<PostComment> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostComment> call, Throwable t) {
                listener.onFailure(t);            }
        });
    }

}
