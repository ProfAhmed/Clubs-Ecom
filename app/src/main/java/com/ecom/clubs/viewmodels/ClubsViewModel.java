package com.ecom.clubs.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.ecom.clubs.data.models.ClubModel;
import com.ecom.clubs.data.models.ClubRequestModel;
import com.ecom.clubs.data.repositories.Repository;

import java.util.ArrayList;

public class ClubsViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<ArrayList<ClubModel>> clubsLiveData;
    MutableLiveData<ClubRequestModel> clubRequestMutableLiveData = new MutableLiveData<>();

    public ClubsViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository();
        clubsLiveData = Transformations.switchMap(clubRequestMutableLiveData, clubRequestModel -> {
            return repository.getClubs(clubRequestModel);
        });
    }

    public void setClubRequest(ClubRequestModel request) {
        clubRequestMutableLiveData.setValue(request);
    }

    public LiveData<ArrayList<ClubModel>> getClubs() {
        return clubsLiveData;
    }
}
