package com.beca.soccernews.ui.favorites;

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

import com.beca.soccernews.MainActivity;
import com.beca.soccernews.databinding.FragmentFavoritesBinding;
import com.beca.soccernews.domain.News;
import com.beca.soccernews.ui.adapters.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoritesNews();

        return binding.getRoot();
    }

    private void loadFavoritesNews() {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            List<News> favoriteNews = (List<News>) activity.getDb().newsDao().loadFavoriteNews();
            binding.rvnews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvnews.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
                activity.getDb().newsDao().save(updatedNews);
                loadFavoritesNews();
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}