package com.beca.soccernews.data.remote;

import com.beca.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SoccerNewsApi {

    //Chamada ao json
    @GET("news.json")
    //Acesso direto ao recurso
    Call<List<News>> getNews();

}
