package com.example.sgundot_di.data.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.adapter.GameAdapter;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.FavoritesViewModel;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> favoriteGamesList = new ArrayList<>();

    public FavoritesFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        gameAdapter = new GameAdapter(favoriteGamesList, this::openDetailFragment);
        recyclerView.setAdapter(gameAdapter);

        viewModel.getFavoriteGamesLiveData().observe(getViewLifecycleOwner(), games -> {
            favoriteGamesList.clear();
            if (games != null && !games.isEmpty()) {
                favoriteGamesList.addAll(games);
                gameAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "No tienes juegos en favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void openDetailFragment(Game game) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("GAME_TITLE", game.getTitulo());
        detailFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
