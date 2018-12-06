package com.ecom.clubs.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ecom.clubs.data.repositories.Repository;

import java.util.ArrayList;

public class GovernmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<ArrayList<String>> governmentLiveData;

    public GovernmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        governmentLiveData = repository.getGovernments();
    }

    public LiveData<ArrayList<String>> getGovernments() {
        return governmentLiveData;
    }
}
