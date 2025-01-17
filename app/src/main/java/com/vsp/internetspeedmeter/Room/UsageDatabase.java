package com.vsp.internetspeedmeter.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.vsp.internetspeedmeter.MainActivity.TAG;

@TypeConverters({Converter.class})
@Database(entities = Usage.class, version = 1)
public abstract class UsageDatabase extends RoomDatabase {

    private static UsageDatabase instance;

    public abstract UsageDao usageDao();

    public static synchronized UsageDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UsageDatabase.class, "usage_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }


    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };


    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private UsageDao usageDao;

        private PopulateDbAsyncTask(UsageDatabase db) {
            this.usageDao = db.usageDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String myDate = df.format(c);
            Log.e(TAG, "doInBackground:Current time => " + c + " date " + myDate);
            try {
                usageDao.insert(new Usage(myDate, "0", "0", "0"));
            } catch (Exception e) {
                usageDao.update(new Usage(myDate, "0", "0", "0"));
            }
            Log.e(TAG, "doInBackground: added");
            return null;
        }
    }

}
