package com.cubes.komentarapp.data.source.datarepository;

import androidx.annotation.NonNull;

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
import com.cubes.komentarapp.data.source.remote.response.RequestCommentPost;
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
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadLatestData(int page, NewsResponseListener listener) {

        service.getLatestNews(page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadCategoryNewsData(int id, int page, NewsResponseListener listener) {

        service.getNewsForCategories(id, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadSearchData(String term, int page, NewsResponseListener listener) {

        service.getSearchNews(term, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadTagData(int id, int page, NewsResponseListener listener) {

        service.getTagNews(id, page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loadHeadNewsData(NewsResponseListener listener) {

        service.getHomepageNews().enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
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
            public void onResponse(@NonNull Call<ResponseCategoryList> call, @NonNull Response<ResponseCategoryList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategoryList> call, @NonNull Throwable t) {
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
            public void onResponse(@NonNull Call<ResponseHoroscope> call, @NonNull Response<ResponseHoroscope> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHoroscope> call, @NonNull Throwable t) {
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
            public void onResponse(@NonNull Call<ResponseWeather> call, @NonNull Response<ResponseWeather> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWeather> call, @NonNull Throwable t) {
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
            public void onResponse(@NonNull Call<ResponseNewsDetail> call, @NonNull Response<ResponseNewsDetail> response) {

                if (response.body() != null) {
                    listener.onResponse(response.body().data);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsDetail> call, @NonNull Throwable t) {
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
            public void onResponse(@NonNull Call<ResponseCommentList> call, @NonNull Response<ResponseCommentList> response) {
                assert response.body() != null;
                listener.onResponse(response.body().data);
            }
            @Override
            public void onFailure(@NonNull Call<ResponseCommentList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface CommentsRequestListener {
        void onResponse(ArrayList<Comments> request);

        void onFailure(Throwable t);
    }

    public void upvoteComment(String id, CommentsRequestListener listener) {

        service.postUpvote(id, true).enqueue(new Callback<ResponseCommentList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseCommentList> call, @NonNull Response<ResponseCommentList> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200
                        && request.body().data != null) {
                    listener.onResponse(request.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCommentList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void downvoteComment(String id, CommentsRequestListener listener) {

        service.postDownvote(id, true).enqueue(new Callback<ResponseCommentList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseCommentList> call, @NonNull Response<ResponseCommentList> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200
                        && request.body().data != null) {
                    listener.onResponse(request.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCommentList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface PostCommentListener {
        void onResponse(ArrayList<String> request);

        void onFailure(Throwable t);
    }

    public void postCommentData(RequestCommentPost comment, PostCommentListener listener) {

        service.postComment(comment).enqueue(new Callback<RequestCommentPost>() {

            @Override
            public void onResponse(@NonNull Call<RequestCommentPost> call, @NonNull Response<RequestCommentPost> request) {
                assert request.body() != null;
                listener.onResponse(request.body().data);
            }

            @Override
            public void onFailure(@NonNull Call<RequestCommentPost> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

}
