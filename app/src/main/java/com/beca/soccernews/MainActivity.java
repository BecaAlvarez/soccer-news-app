package com.beca.soccernews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.beca.soccernews.R;
import com.beca.soccernews.data.local.AppDatabase;
import com.beca.soccernews.databinding.ActivityMainBinding;
import com.beca.soccernews.ui.news.NewsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard).build();
        //Gerenciar td transição e td mudança dos fragmentos dentroda main activity
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        this.setupDb();
    }

    private void setupDb() {
        db = Room.databaseBuilder(this, AppDatabase.class, "soccer-news")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getDb() {
        return db;
    }
}