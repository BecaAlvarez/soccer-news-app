package com.beca.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beca.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;


public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<News>>news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        //TODO Remover Mock de Noticias
        List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária tem desfalque importante","\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\""));
        news.add(new News("Ferrinha joga sabado ","\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\""));
        news.add(new News("Copa do Mundo Feminina está terminando","\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\""));

        this.news.setValue(news);
    }
    //Retornar a lista de noticias
    public LiveData<List<News>> getNews() {
        return news;
    }
}