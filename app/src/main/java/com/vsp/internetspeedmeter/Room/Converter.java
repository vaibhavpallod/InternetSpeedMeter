package com.vsp.internetspeedmeter.Room;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.room.TypeConverter;

import static com.vsp.internetspeedmeter.MainActivity.TAG;

public class Converter {

    /*
        @TypeConverter
        public static String timestamptoSql(Timestamp time){
            return time == null ? null : time.toString();//.toDate().toString();
        }

        @TypeConverter
        public static Timestamp sqtToTimestamp(String timestamp) {
    //        if (Helper.IsNullOrEmpty(timestamp))
    //            return null;
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Timestamp time = null;
            try {
                time = new Timestamp(new Date(formatter.parse(timestamp).getTime()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return time == null ? null : time;
        }
    */
    @TypeConverter
    public static String datetoSql(Date date) {
        return date == null ? null : date.toString();//.toDate().toString();
    }

    @TypeConverter
    public static Date sqlToDate(String date) {
//        if (date==null)
//            return null;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date1 = null;
        try {
            date1 =formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "sqlToDate: "+e.getMessage());
        }

        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z",Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "sqlToDate2: "+e.getMessage());
        }
        Log.e(TAG, "sqlToDate: "+date1+"  "+date2 +" original is :" +date);
        return date2;
//        return date1 == null ? null : date1;
    }

}
