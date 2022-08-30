package com.cubes.komentarapp.data.source.remote.networking;

import com.cubes.komentarapp.data.source.remote.response.RequestCommentPost;
import com.cubes.komentarapp.data.source.remote.response.ResponseCategoryList;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentList;
import com.cubes.komentarapp.data.source.remote.response.ResponseHoroscope;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsDetail;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsList;
import com.cubes.komentarapp.data.source.remote.response.ResponseWeather;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("api/latest")
    Call<ResponseNewsList> getLatestNews(@Query("page") int page);

    @GET("api/videos")
    Call<ResponseNewsList> getVideoNews(@Query("page") int page);

    @GET("api/homepage")
    Call<ResponseNewsList> getHomepageNews();

    @GET("api/search")
    Call<ResponseNewsList> getSearchNews(@Query("search_parameter") String term, @Query("page") int page);

    @GET("api/category/{id}")
    Call<ResponseNewsList> getNewsForCategories(@Path("id") int id, @Query("page") int page);

    @GET("api/tag")
    Call<ResponseNewsList> getTagNews(@Query("tag") int id, @Query("page") int page);

    @GET("api/categories")
    Call<ResponseCategoryList> getCategories();

    @GET("api/newsdetails")
    Call<ResponseNewsDetail> getNewsDetail(@Query("id") int id);

    @GET("api/comments")
    Call<ResponseCommentList> getComments(@Query("id") int id);

    @POST("api/commentinsert")
    Call<RequestCommentPost> postComment(@Body RequestCommentPost comment);
    //(@Field("news") String news, @Field("reply_id") String reply_id, @Field("name") String name, @Field("email") String email, @Field("content") String content);

    @POST("api/commentvote")
    Call<ResponseCommentList> postUpvote(@Query("comment") String id, @Query("vote") boolean vote);

    @POST("api/commentvote")
    Call<ResponseCommentList> postDownvote(@Query("comment") String id, @Query("downvote") boolean vote);

    @GET("api/horoscope")
    Call<ResponseHoroscope> getHoroscope();

    @GET("api/weather")
    Call<ResponseWeather> getWeather();

}
