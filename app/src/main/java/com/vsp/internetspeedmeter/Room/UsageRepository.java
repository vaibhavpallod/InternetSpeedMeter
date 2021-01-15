package com.vsp.internetspeedmeter.Room;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UsageRepository {

    private UsageDao usageDao;
    private LiveData<List<Usage>> allUsage;

    public UsageRepository(Context context) {

        UsageDatabase database = UsageDatabase.getInstance(context);
        usageDao = database.usageDao();
        allUsage = usageDao.getAllUsage();


    }

    public void insert(Usage usage) {
    new InsertUsageAsyncTask(usageDao).execute(usage);
    }

    public void update(Usage usage) {
    new UpdateUsageAsyncTask(usageDao).execute(usage);

    }

    public void delete(Usage usage) {
        new DeleteUsageAsyncTask(usageDao).execute(usage);

    }

    public void deleteAllNotes() {
        new DeleteAllUsageAsyncTask(usageDao).execute();

    }

    public LiveData<List<Usage>> getAllUsage() {

    return allUsage;
    }


    private static class InsertUsageAsyncTask extends AsyncTask<Usage,Void,Void>{

        private UsageDao usageDao;
        private InsertUsageAsyncTask(UsageDao usageDao){
            this.usageDao=usageDao;

        }
        @Override
        protected Void doInBackground(Usage... usages) {
            usageDao.insert(usages[0]);
            return null;
        }
    }

    private static class UpdateUsageAsyncTask extends AsyncTask<Usage,Void,Void>{

        private UsageDao usageDao;
        private UpdateUsageAsyncTask(UsageDao usageDao){
            this.usageDao=usageDao;

        }
        @Override
        protected Void doInBackground(Usage... usages) {
            usageDao.update(usages[0]);
            return null;
        }
    }

    private static class DeleteUsageAsyncTask extends AsyncTask<Usage,Void,Void>{

        private UsageDao usageDao;
        private DeleteUsageAsyncTask(UsageDao usageDao){
            this.usageDao=usageDao;

        }
        @Override
        protected Void doInBackground(Usage... usages) {
            usageDao.delete(usages[0]);
            return null;
        }
    }

    private static class DeleteAllUsageAsyncTask extends AsyncTask<Void,Void,Void>{

        private UsageDao usageDao;
        private DeleteAllUsageAsyncTask(UsageDao usageDao){
            this.usageDao=usageDao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            usageDao.deleteAll();
            return null;
        }
    }

}
