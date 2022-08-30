package com.cubes.komentarapp.di;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.networking.NewsRetrofit;

public class AppContainer {

    NewsRetrofit api = new NewsRetrofit();

    public DataRepository dataRepository = new DataRepository(api.getRetrofitService());

}
