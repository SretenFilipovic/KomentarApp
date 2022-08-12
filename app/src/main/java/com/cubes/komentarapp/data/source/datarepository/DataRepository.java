package com.cubes.komentarapp.data.source.datarepository;

import android.util.Log;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.model.Horoscope;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.model.NewsList;
import com.cubes.komentarapp.data.model.Weather;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.remote.networking.RetrofitService;
import com.cubes.komentarapp.data.source.remote.response.ResponseCategoryList;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentList;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentPost;
import com.cubes.komentarapp.data.source.remote.response.ResponseHoroscope;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsDetail;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsList;
import com.cubes.komentarapp.data.source.remote.response.ResponseWeather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {

    private static DataRepository instance;
    private RetrofitService service;

    private DataRepository() {
        callRetrofit();
    }

    public static DataRepository getInstance() {
        if (instance == null)
            instance = new DataRepository();
        return instance;
    }

    public void callRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataContainer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    public interface NewsResponseListener {
        void onResponse(NewsList response);

        void onFailure(Throwable t);
    }

    public void loadVideoData(int page, NewsResponseListener listener) {

        service.getVideoNews(page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadLatestData(int page, NewsResponseListener listener) {

        service.getLatestNews(page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadCategoryNewsData(int id, int page, NewsResponseListener listener) {

        service.getNewsForCategories(id, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadSearchData(String term, int page, NewsResponseListener listener) {

        service.getSearchNews(term, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadTagData(int id, int page, NewsResponseListener listener) {

        service.getTagNews(id, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadHeadNewsData(NewsResponseListener listener) {

        service.getHomepageNews().enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(Call<ResponseNewsList> call, Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface CategoryResponseListener {
        void onResponse(ArrayList<Category> response);

        void onFailure(Throwable t);
    }

    public void loadCategoryData(CategoryResponseListener listener) {

        service.getCategories().enqueue(new Callback<ResponseCategoryList>() {

            @Override
            public void onResponse(Call<ResponseCategoryList> call, Response<ResponseCategoryList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseCategoryList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public interface HoroscopeResponseListener {
        void onResponse(Horoscope response);

        void onFailure(Throwable t);
    }

    public void loadHoroscopeData(HoroscopeResponseListener listener) {

        service.getHoroscope().enqueue(new Callback<ResponseHoroscope>() {

            @Override
            public void onResponse(Call<ResponseHoroscope> call, Response<ResponseHoroscope> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseHoroscope> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public interface WeatherResponseListener {
        void onResponse(Weather response);

        void onFailure(Throwable t);
    }

    public void loadWeatherData(WeatherResponseListener listener) {

        service.getWeather().enqueue(new Callback<ResponseWeather>() {

            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public interface NewsDetailListener {
        void onResponse(News response);

        void onFailure(Throwable t);
    }

    public void getNewsDetails(int id, NewsDetailListener listener) {

        service.getNewsDetail(id).enqueue(new Callback<ResponseNewsDetail>() {
            @Override
            public void onResponse(Call<ResponseNewsDetail> call, Response<ResponseNewsDetail> response) {

                listener.onResponse(response.body().data);

            }

            @Override
            public void onFailure(Call<ResponseNewsDetail> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public interface CommentsResponseListener {
        void onResponse(ArrayList<Comments> response);

        void onFailure(Throwable t);
    }

    public void loadCommentsData(int id, CommentsResponseListener listener) {

        service.getComments(id).enqueue(new Callback<ResponseCommentList>() {

            @Override
            public void onResponse(Call<ResponseCommentList> call, Response<ResponseCommentList> response) {

                listener.onResponse(response.body().data);

            }

            @Override
            public void onFailure(Call<ResponseCommentList> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void upvoteComment(String id) {

        service.postUpvote(id, true).enqueue(new Callback<ResponseCommentList>() {

            @Override
            public void onResponse(Call<ResponseCommentList> call, Response<ResponseCommentList> response) {
                Log.d("UPVOTE", "Upvote success");
            }

            @Override
            public void onFailure(Call<ResponseCommentList> call, Throwable t) {
                Log.d("UPVOTE", "Upvote failure");
            }
        });
    }

    public void downvoteComment(String id) {

        service.postDownvote(id, true).enqueue(new Callback<ResponseCommentList>() {

            @Override
            public void onResponse(Call<ResponseCommentList> call, Response<ResponseCommentList> response) {
                Log.d("DOWNVOTE", "Downvote success");
            }

            @Override
            public void onFailure(Call<ResponseCommentList> call, Throwable t) {
                Log.d("DOWNVOTE", "Downvote failure");
            }
        });
    }

    public interface PostCommentListener {
        void onResponse(ArrayList<String> response);

        void onFailure(Throwable t);
    }

    public void postCommentData(ResponseCommentPost comment, PostCommentListener listener) {

        service.postComment(comment).enqueue(new Callback<ResponseCommentPost>() {

            @Override
            public void onResponse(Call<ResponseCommentPost> call, Response<ResponseCommentPost> response) {
                listener.onResponse(response.body().data);
            }

            @Override
            public void onFailure(Call<ResponseCommentPost> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

}
