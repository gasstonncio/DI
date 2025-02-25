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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.adapter.GameAdapter;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.DashboardViewModel;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel; // ViewModel para manejar los datos
    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private List<Game> gameList = new ArrayList<>(); // Lista de juegos

    public DashboardFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Inicializamos el RecyclerView y su configuración
        recyclerView = view.findViewById(R.id.dashboardRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Configuramos el adaptador para la lista de juegos
        adapter = new GameAdapter(gameList, this::openDetailFragment);
        recyclerView.setAdapter(adapter);

        // Inicializamos el ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Observamos los datos de los juegos
        viewModel.getJuegosLiveData().observe(getViewLifecycleOwner(), games -> {
            if (games != null) {
                gameList.clear();
                gameList.addAll(games);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "No se encontraron juegos", Toast.LENGTH_SHORT).show();
            }
        });

        // Observamos los errores en la carga de datos
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Método para abrir el fragmento de detalles al seleccionar un juego
    private void openDetailFragment(Game game) {
        DetailFragment detailFragment = new DetailFragment();

        // Pasamos el título del juego al fragmento de detalles
        Bundle bundle = new Bundle();
        bundle.putString("GAME_TITLE", game.getTitulo());
        detailFragment.setArguments(bundle);

        // Reemplazamos el fragmento actual por el de detalles
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
