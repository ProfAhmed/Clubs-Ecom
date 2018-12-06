package com.ecom.clubs.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.ecom.clubs.data.repositories.Repository;

import java.util.ArrayList;

public class CitiesViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<ArrayList<String>> citiesLiveData;
    private MutableLiveData<String> governmentId = new MutableLiveData<>();

    public CitiesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        citiesLiveData = Transformations.switchMap(governmentId, id -> {
            return repository.getCities(id);
        });
    }

    public void setGovernmentId(String id) {
        governmentId.setValue(id);
    }

    public LiveData<ArrayList<String>> getCities() {
        return citiesLiveData;
    }
}
