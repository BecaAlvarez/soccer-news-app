package com.beca.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beca.soccernews.databinding.FragmentNewsBinding;
import com.beca.soccernews.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {


    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvnews.setLayoutManager(new LinearLayoutManager(getContext()));
        //Ação do que fazer qd o dados chegarem
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
           //Para adaptar os dados  que tá atribuido ao recicleview(news_item)
            binding.rvnews.setAdapter(new NewsAdapter(news));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}