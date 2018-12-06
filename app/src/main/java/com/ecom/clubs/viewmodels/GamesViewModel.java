package com.ecom.clubs.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ecom.clubs.data.repositories.Repository;

import java.util.ArrayList;

public class GamesViewModel extends AndroidViewModel {

    Repository repository;
    LiveData<ArrayList<String>> gamesLiveData;

    public GamesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        gamesLiveData = repository.getGames();
    }

    public LiveData<ArrayList<String>> getGames() {
        return gamesLiveData;
    }
}
