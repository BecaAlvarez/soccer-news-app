package com.beca.soccernews.ui.news;

//Pagina da News
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.beca.soccernews.MainActivity;
import com.beca.soccernews.databinding.FragmentNewsBinding;
import com.beca.soccernews.ui.adapters.NewsAdapter;



public class NewsFragment extends Fragment {


    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //Seta o layout, de forma Linear,pegando o contexto
        binding.rvnews.setLayoutManager(new LinearLayoutManager(getContext()));
        //Ação do que fazer qd o dados chegarem. Recebe a lista
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
           //Para adaptar os dados  que tá atribuido ao recicleview(news_item)
            binding.rvnews.setAdapter(new NewsAdapter(news, updatedNews ->{
                //o user realizou a ação de clicar no botão favoritar
                MainActivity activity = (MainActivity) getActivity();
                if(activity != null){
                    activity.getDb().newsDao().save(updatedNews);
                }
            }));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}