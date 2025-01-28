package com.example.sgundot_di.data.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {
    private final DatabaseReference gamesRef;
    private final MutableLiveData<List<Game>> gamesLiveData;
    private final MutableLiveData<String> errorLiveData;

    public DashboardRepository() {
        gamesRef = FirebaseDatabase.getInstance().getReference("juegos");
        gamesLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        loadGames();
    }

    private void loadGames() {
        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Game> gamesList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Game game = snapshot.getValue(Game.class);
                    if (game != null) {
                        gamesList.add(game);
                    }
                }
                gamesLiveData.setValue(gamesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                errorLiveData.setValue(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<List<Game>> getGamesLiveData() {
        return gamesLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}