package com.cubes.komentarapp.data.source.remote.networking;

import com.cubes.komentarapp.data.source.remote.response.ResponseCategory;
import com.cubes.komentarapp.data.source.remote.response.ResponseComments;
import com.cubes.komentarapp.data.source.remote.response.PostComment;
import com.cubes.komentarapp.data.source.remote.response.ResponseHoroscope;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.source.remote.response.ResponseNewsDetail;
import com.cubes.komentarapp.data.source.remote.response.ResponseWeather;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("api/latest")
    Call<ResponseNews> getLatestNews(@Query("page") int page);

    @GET("api/videos")
    Call<ResponseNews> getVideoNews(@Query("page") int page);

    @GET("api/homepage")
    Call<ResponseNews> getHomepageNews();

    @GET("api/search")
    Call<ResponseNews> getSearchNews(@Query("search_parameter") String term, @Query("page") int page);

    @GET("api/category/{id}")
    Call<ResponseNews> getNewsForCategories(@Path("id") int id, @Query("page") int page);

    @GET("api/tag")
    Call<ResponseNews> getTagNews(@Query("tag") int id, @Query("page") int page);

    @GET("api/categories")
    Call<ResponseCategory> getCategories();

    @GET("api/newsdetails")
    Call<ResponseNewsDetail> getNewsDetail(@Query("id") int id);

    @GET("api/comments")
    Call<ResponseComments> getComments (@Query("id") int id);

    @POST("api/commentinsert")
    Call<PostComment> postComment(@Body PostComment comment);
    //pokusavao sam i na ovaj nacin, da umesto tela komentara prosledim fildove
    //(@Field("news") String news, @Field("reply_id") String reply_id, @Field("name") String name, @Field("email") String email, @Field("content") String content);

    @POST("api/commentvote")
    Call<ResponseComments> postUpvote (@Query("comment") String id, @Query("vote") boolean vote);

    @POST("api/commentvote")
    Call<ResponseComments> postDownvote (@Query("comment") String id, @Query("downvote") boolean vote);

    @GET("api/horoscope")
    Call<ResponseHoroscope> getHoroscope();

    @GET("api/weather")
    Call<ResponseWeather> getWeather();

}
