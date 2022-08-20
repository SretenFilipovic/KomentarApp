package com.cubes.komentarapp.data.source.datarepository;

import androidx.annotation.NonNull;

import com.cubes.komentarapp.data.model.api.CategoryApi;
import com.cubes.komentarapp.data.model.api.CommentsApi;
import com.cubes.komentarapp.data.model.api.HoroscopeApi;
import com.cubes.komentarapp.data.model.api.NewsApi;
import com.cubes.komentarapp.data.model.api.WeatherApi;
import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.Horoscope;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.data.model.domain.Weather;
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
        void onResponse(ArrayList<News> response);

        void onFailure(Throwable t);
    }

    public void loadVideoData(int page, NewsResponseListener listener) {

        service.getVideoNews(page).enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {

                    ArrayList<News> newsList = response.body().data.news;

                    listener.onResponse(newsList);
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

                    ArrayList<News> newsList = response.body().data.news;

                    listener.onResponse(newsList);
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

                    ArrayList<News> newsList = response.body().data.news;

                    listener.onResponse(newsList);
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
                    ArrayList<News> newsList = response.body().data.news;

                    listener.onResponse(newsList);
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

                    ArrayList<News> newsList = response.body().data.news;

                    listener.onResponse(newsList);                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface HeadNewsResponseListener {
        void onResponse(NewsList response);

        void onFailure(Throwable t);
    }

    public void loadHeadNewsData(HeadNewsResponseListener listener) {

        service.getHomepageNews().enqueue(new Callback<ResponseNewsList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {

                    NewsList headNews = new NewsList();

                    headNews.category = response.body().data.category;
                    headNews.editors_choice = response.body().data.editors_choice;
                    headNews.latest = response.body().data.latest;
                    headNews.most_comented = response.body().data.most_comented;
                    headNews.most_read = response.body().data.most_read;
                    headNews.slider = response.body().data.slider;
                    headNews.top = response.body().data.top;
                    headNews.videos = response.body().data.videos;

                    listener.onResponse(headNews);
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

                    ArrayList<Category> categories = new ArrayList<>();

                    for (CategoryApi categoryApi : response.body().data){

                        Category category = new Category();

                        category.id = categoryApi.id;
                        category.color = categoryApi.color;
                        category.name = categoryApi.name;
                        category.type = categoryApi.type;

                        category.subcategories = new ArrayList<>();

                        for (CategoryApi subcategoryApi : categoryApi.subcategories){

                            Category subcategory = new Category();

                            subcategory.type = subcategoryApi.type;
                            subcategory.name = subcategoryApi.name;
                            subcategory.id = subcategoryApi.id;
                            subcategory.color = subcategoryApi.color;

                            category.subcategories.add(subcategory);
                        }

                        categories.add(category);
                    }
                    listener.onResponse(categories);
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

                    Horoscope horoscope = new Horoscope();

                    horoscope.horoscope = response.body().data.horoscope;
                    horoscope.date = response.body().data.date;
                    horoscope.name = response.body().data.name;
                    horoscope.image_url = response.body().data.image_url;

                    listener.onResponse(horoscope);
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

                    Weather weather = new Weather();

                    weather.day_0 = response.body().data.day_0;
                    weather.day_1 = response.body().data.day_1;
                    weather.day_2 = response.body().data.day_2;
                    weather.day_3 = response.body().data.day_3;
                    weather.day_4 = response.body().data.day_4;
                    weather.day_5 = response.body().data.day_5;
                    weather.day_6 = response.body().data.day_6;
                    weather.humidity = response.body().data.humidity;
                    weather.icon_url = response.body().data.icon_url;
                    weather.id = response.body().data.id;
                    weather.name = response.body().data.name;
                    weather.pressure = response.body().data.pressure;
                    weather.temp_max = response.body().data.temp_max;
                    weather.temp_min = response.body().data.temp_min;
                    weather.wind = response.body().data.wind;

                    listener.onResponse(weather);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWeather> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public interface NewsDetailListener {
        void onResponse(NewsDetail response);

        void onFailure(Throwable t);
    }

    public void getNewsDetails(int id, NewsDetailListener listener) {

        service.getNewsDetail(id).enqueue(new Callback<ResponseNewsDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsDetail> call, @NonNull Response<ResponseNewsDetail> response) {

                if (response.body() != null) {

                    NewsDetail detail = new NewsDetail();

                    detail.id = response.body().data.id;
                    detail.comment_enabled = response.body().data.comment_enabled == 1;
                    detail.comments_count = response.body().data.comments_count;
                    detail.related_news = response.body().data.related_news;
                    detail.comments_top_n = response.body().data.comments_top_n;
                    detail.tags = response.body().data.tags;
                    detail.url = response.body().data.url;

                    listener.onResponse(detail);
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

                ArrayList<Comments> commentList = new ArrayList<>();

                for (CommentsApi commentsApi : response.body().data){

                    Comments comment = new Comments();

                    comment.name = commentsApi.name;
                    comment.parent_comment = commentsApi.parent_comment;
                    comment.id = commentsApi.id;
                    comment.content = commentsApi.content;
                    comment.children = commentsApi.children;
                    comment.created_at = commentsApi.created_at;
                    comment.negative_votes = commentsApi.negative_votes;
                    comment.positive_votes = commentsApi.positive_votes;
                    comment.news = commentsApi.news;

                    commentList.add(comment);
                }

                listener.onResponse(commentList);
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

                    ArrayList<Comments> commentList = new ArrayList<>();

                    for (CommentsApi commentsApi : request.body().data){

                        Comments comment = new Comments();

                        comment.name = commentsApi.name;
                        comment.parent_comment = commentsApi.parent_comment;
                        comment.id = commentsApi.id;
                        comment.content = commentsApi.content;
                        comment.created_at = commentsApi.created_at;
                        comment.negative_votes = commentsApi.negative_votes;
                        comment.positive_votes = commentsApi.positive_votes;
                        comment.news = commentsApi.news;

                        comment.children = commentsApi.children;

                        commentList.add(comment);
                    }

                    listener.onResponse(commentList);
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

                    ArrayList<Comments> commentList = new ArrayList<>();

                    for (CommentsApi commentsApi : request.body().data){

                        Comments comment = new Comments();

                        comment.name = commentsApi.name;
                        comment.parent_comment = commentsApi.parent_comment;
                        comment.id = commentsApi.id;
                        comment.content = commentsApi.content;
                        comment.children = commentsApi.children;
                        comment.created_at = commentsApi.created_at;
                        comment.negative_votes = commentsApi.negative_votes;
                        comment.positive_votes = commentsApi.positive_votes;
                        comment.news = commentsApi.news;

                        commentList.add(comment);
                    }

                    listener.onResponse(commentList);
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
