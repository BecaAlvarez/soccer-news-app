package com.beca.soccernews.ui.news;

//Pag para negocio relacionado ao consumo de API. Encapsula as regras e como fazer as chamadas
//"Avisa a tela" sobre atualizações de dados ou error


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.beca.soccernews.data.remote.SoccerNewsApi;
import com.beca.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>>news = new MutableLiveData<>();
    private final SoccerNewsApi api;



    public NewsViewModel() {
        //Instanciar: Configurações do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://becaalvarez.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SoccerNewsApi.class);

        //chamada do metodo
        this.findNews();
    }
    //Consumir a API e precisa de um callback para os estados de sucesso ou não
    public void findNews() {
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    // Informa a ui q a infor voltou da API e pode ser utilizada para atualizar o recycle view
                   news.setValue(response.body());
                }
                else{
                    //TODO pensar em tratamento de erros
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                //TODO pensar em tratamento de erros
            }
        });
    }

    //Retornar a lista de noticias
    public LiveData<List<News>> getNews() {
        return this.news;
    }

    private class AppDatabase {
    }
}