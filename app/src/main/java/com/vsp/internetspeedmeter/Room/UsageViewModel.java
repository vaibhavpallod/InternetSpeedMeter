package com.vsp.internetspeedmeter.Room;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UsageViewModel extends AndroidViewModel {
        private UsageRepository usageRepository;
        private LiveData<List<Usage>> allUsage;

    public UsageViewModel(@NonNull Application application) {
        super(application);
        usageRepository = new UsageRepository(application);
        allUsage= usageRepository.getAllUsage();
    }

    public void insert(Usage usage){
        usageRepository.insert(usage);
    }


    public void update(Usage usage){
        usageRepository.update(usage);
    }


    public void delete(Usage usage){
        usageRepository.delete(usage);
    }

    public void deleteAll(){
        usageRepository.deleteAllNotes();
    }

    public LiveData<List<Usage>> getAllNotes(){
        return allUsage;
    }



}
