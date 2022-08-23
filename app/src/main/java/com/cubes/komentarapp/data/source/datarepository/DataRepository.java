package com.cubes.komentarapp.data.source.datarepository;

import androidx.annotation.NonNull;

import com.cubes.komentarapp.data.model.api.CategoryApi;
import com.cubes.komentarapp.data.model.api.CategoryBoxApi;
import com.cubes.komentarapp.data.model.api.CommentsApi;
import com.cubes.komentarapp.data.model.api.NewsApi;
import com.cubes.komentarapp.data.model.api.TagsApi;
import com.cubes.komentarapp.data.model.api.WeatherApi;
import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.Horoscope;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.data.model.domain.Tags;
import com.cubes.komentarapp.data.model.domain.Weather;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.remote.networking.RetrofitService;
import com.cubes.komentarapp.data.source.remote.response.RequestCommentPost;
import com.cubes.komentarapp.data.source.remote.response.ResponseCategoryList;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentList;
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

                    listener.onResponse(mapNewsFromResponse(response.body().data.news));
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

                    listener.onResponse(mapNewsFromResponse(response.body().data.news));
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

                    listener.onResponse(mapNewsFromResponse(response.body().data.news));
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

                    listener.onResponse(mapNewsFromResponse(response.body().data.news));
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

                    listener.onResponse(mapNewsFromResponse(response.body().data.news));
                }
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
                    ArrayList<CategoryBox> categoryBoxList = new ArrayList<>();

                    for (CategoryBoxApi categoryBoxApi : response.body().data.category){
                        CategoryBox categoryBox = new CategoryBox();

                        categoryBox.color = categoryBoxApi.color;
                        categoryBox.id = categoryBoxApi.id;
                        categoryBox.news = mapNewsFromResponse(categoryBoxApi.news);
                        categoryBox.title = categoryBoxApi.title;

                        categoryBoxList.add(categoryBox);
                    }

                    headNews.category = categoryBoxList;
                    headNews.editorsChoice = mapNewsFromResponse(response.body().data.editors_choice);
                    headNews.latest = mapNewsFromResponse(response.body().data.latest);
                    headNews.mostCommented = mapNewsFromResponse(response.body().data.most_comented);
                    headNews.mostRead = mapNewsFromResponse(response.body().data.most_read);
                    headNews.slider = mapNewsFromResponse(response.body().data.slider);
                    headNews.top = mapNewsFromResponse(response.body().data.top);
                    headNews.videos = mapNewsFromResponse(response.body().data.videos);

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
                    horoscope.imageUrl = response.body().data.image_url;

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

                    listener.onResponse(mapWeatherFromCategory(response.body().data));
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
                    ArrayList<Tags> tags = new ArrayList<>();

                    for (TagsApi tagsApi : response.body().data.tags){
                        Tags tag = new Tags();

                        tag.id = tagsApi.id;
                        tag.title = tagsApi.title;

                        tags.add(tag);
                    }

                    detail.tags = tags;

                    detail.id = response.body().data.id;
                    detail.commentEnabled = response.body().data.comment_enabled == 1;
                    detail.commentsCount = response.body().data.comments_count;
                    detail.relatedNews = mapNewsFromResponse(response.body().data.related_news);
                    detail.topComments = mapCommentFromResponse(response.body().data.comments_top_n);
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

                listener.onResponse(mapCommentFromResponse(response.body().data));
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


                    listener.onResponse(mapCommentFromResponse(request.body().data));
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

                    listener.onResponse(mapCommentFromResponse(request.body().data));
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

    private ArrayList<News> mapNewsFromResponse(ArrayList<NewsApi> newsFromResponse) {

        ArrayList<News> newsList = new ArrayList<>();

        for (NewsApi newsItemApi : newsFromResponse) {

            News news = new News();

            news.id = newsItemApi.id;
            news.image = newsItemApi.image;
            news.title = newsItemApi.title;
            news.createdAt = newsItemApi.created_at;
            news.url = newsItemApi.url;

            news.category = mapCategoryFromResponse(newsItemApi.category);

            newsList.add(news);

        }

        return newsList;
    }

    private Category mapCategoryFromResponse(CategoryApi categoryApi) {

        Category category = new Category();

        category.id = categoryApi.id;
        category.type = categoryApi.type;
        category.name = categoryApi.name;
        category.color = categoryApi.color;

        ArrayList<Category> subcategories = new ArrayList<>();

        if (categoryApi.subcategories != null && !categoryApi.subcategories.isEmpty()) {

            for (CategoryApi subcategoryApi : categoryApi.subcategories) {

                Category subcategory = new Category();

                subcategory.id = subcategoryApi.id;
                subcategory.type = subcategoryApi.type;
                subcategory.name = subcategoryApi.name;
                subcategory.color = subcategoryApi.color;

                subcategories.add(subcategory);
            }
        }

        category.subcategories = subcategories;

        return category;
    }

    private Weather mapWeatherFromCategory(WeatherApi weatherApi){

        Weather weather = new Weather();

        weather.humidity = weatherApi.humidity;
        weather.iconUrl = weatherApi.icon_url;
        weather.id = weatherApi.id;
        weather.name = weatherApi.name;
        weather.pressure = weatherApi.pressure;
        weather.tempMax = weatherApi.temp_max;
        weather.tempMin = weatherApi.temp_min;
        weather.wind = weatherApi.wind;
        weather.temp = weatherApi.temp;

        if (weatherApi.day_0 != null){
            weather.day1 = mapWeatherFromCategory(weatherApi.day_0);
        }
        if (weatherApi.day_1 != null){
            weather.day2 = mapWeatherFromCategory(weatherApi.day_1);
        }
        if (weatherApi.day_2 != null){
            weather.day3 = mapWeatherFromCategory(weatherApi.day_2);
        }
        if (weatherApi.day_3 != null){
            weather.day4 = mapWeatherFromCategory(weatherApi.day_3);
        }
        if (weatherApi.day_4 != null){
            weather.day5 = mapWeatherFromCategory(weatherApi.day_4);
        }
        if (weatherApi.day_5 != null){
            weather.day6 = mapWeatherFromCategory(weatherApi.day_5);
        }
        if (weatherApi.day_6 != null){
            weather.day7 = mapWeatherFromCategory(weatherApi.day_6);
        }
        return weather;
    }

    private ArrayList<Comments> mapCommentFromResponse(ArrayList<CommentsApi> commentsFromResponse) {

        ArrayList<Comments> comments = new ArrayList<>();

        for (CommentsApi commentApi : commentsFromResponse) {

            Comments comment = new Comments();

            comment.negativeVotes = commentApi.negative_votes;
            comment.positiveVotes = commentApi.positive_votes;
            comment.createdAt = commentApi.created_at;
            comment.newsId = commentApi.news;
            comment.name = commentApi.name;
            comment.parentCommentId = commentApi.parent_comment;
            comment.id = commentApi.id;
            comment.content = commentApi.content;

            if (commentApi.children != null && !commentApi.children.isEmpty()) {
                comment.children = mapCommentFromResponse(commentApi.children);
            }

            comments.add(comment);
        }

        return comments;
    }
}
