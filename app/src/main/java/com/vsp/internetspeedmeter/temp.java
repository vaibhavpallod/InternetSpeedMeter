package com.vsp.internetspeedmeter;

public class temp {
    /*
    * package com.vsp.internetspeedmeter.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
@Database(entities = Usage.class, version = 1,exportSchema = false)
public abstract class UsageDatabase extends RoomDatabase {

    private static UsageDatabase instance;

    public abstract UsageDao usageDao();

    public static synchronized UsageDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UsageDatabase.class,"usage_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            Log.e(TAG, "getInstance: added callback" );
        }
        return instance;

    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };


    public static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private UsageDao usageDao;
        private PopulateDbAsyncTask(UsageDatabase db){
            this.usageDao=db.usageDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
//            Calendar date = Calendar.getInstance();
            String date = "01/01/2009";
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date myDate = null;
            try {
                myDate = formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

            usageDao.insert(new Usage(sqlDate,"100","100","100"));
//            date = Calendar.getInstance();
//            usageDao.update(new Usage(sqlDate,"200","200","200"));
//            date = Calendar.getInstance();
//            usageDao.update(new Usage(sqlDate,"300","300","300"));
            Log.e(TAG, "doInBackground: added");
            return null;
        }
    }

}
*/


}
