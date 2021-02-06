package com.vsp.internetspeedmeter.BroadcastReciever;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.vsp.internetspeedmeter.Model.Speed;
import com.vsp.internetspeedmeter.NotificationService;
import com.vsp.internetspeedmeter.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import static com.vsp.internetspeedmeter.MainActivity.TAG;

public class InternetService extends Service {
//    float todaydata = 0;
//    String mDownloadSpeedWithDecimals, mUploadSpeedWithDecimals, mTotalSpeedWithDecimals, mTotalMobileData, mDUnits, mUUnits, mTUnits = "B/s", mMTUnits = "MB";

    private boolean mNotificationCreated = false;
    WorkRequest workRequest;
    private Speed speed;
    private Icon icon;
    public NotificationService notificationService;
    long dPreBytes = 0, dPostBytes = 0, uPreBytes = 0, uPostBytes = 0, mTotoalBytes = 0;
    final private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        notificationService = new NotificationService(this);
        speed = new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);
        mTotoalBytes = 0;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            dPreBytes = dPostBytes;
            uPreBytes = uPostBytes;

            dPostBytes = TrafficStats.getMobileRxBytes();
            uPostBytes = TrafficStats.getMobileTxBytes();
//            if(uPostBytes)
            speed = new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);
            mTotoalBytes += speed.getTotalMData();

            startForeground(1, notificationService.updateNotification(speed, mTotoalBytes).build());

            handler.postDelayed(runnable, 1000);
        }
    };


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        mTotoalBytes+=Long.valueOf(intent.getStringExtra("prevMData"));
//        notificationService.createNotification();
        Log.e(TAG, "onStartCommand: ");
        startForeground(1, notificationService.updateNotification(speed, mTotoalBytes).build());
        restartNotifying();
        initialiseWorker();

        return START_REDELIVER_INTENT;
    }

    private void initialiseWorker() {
        Timer timer = new Timer();

        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 18);
        date.set(Calendar.MINUTE, 07);
        date.set(Calendar.SECOND, 30);
        date.set(Calendar.MILLISECOND, 0);
        Log.e(TAG, "initialiseWorker: " + "Initialised Worker");

        Gson gson = new Gson();
//        String s = gson.toJson(notificationService);
        String s =String.valueOf(notificationService);
        Data data = new Data.Builder().putString("noticontext", s).build();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(ResetWork.class, 15, TimeUnit.MINUTES)
//                        .setInputData(data)
//                        .build();
//                displayNotification(notificationService.myDate,"working");
                Log.e(TAG, "ResetWork: doing work");


//                WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);


              /*  WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(workRequest.getId())
                        .observe(this, workInfo -> {
                            if (workInfo.getState() != null &&
                                    workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                Log.e(TAG, "getLifecycle: workinfo called");

                            }
                        });*/
            }
        }, date.getTime(),1000);

    }
    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());


    }

    private void restartNotifying() {
        Log.e(TAG, "restarted");

        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}




/*
*   long upspeed = uPostBytes - uPreBytes;
        long totalSpeed = 0;


        if (upspeed >= 1000000000) {
            mUploadSpeedWithDecimals = String.valueOf(df.format((float) upspeed / (float) 1000000000));
            mUUnits = " GB/s";
        } else if (upspeed >= 1000000) {
            mUploadSpeedWithDecimals = String.valueOf(df.format((float) upspeed / (float) 1000000));
            mUUnits = " MB/s";

        } else if (upspeed >= 1000) {
            mUploadSpeedWithDecimals = String.valueOf((int) (upspeed / 1000));
            mUUnits = " KB/s";
        } else {
            mUploadSpeedWithDecimals = String.valueOf((int) upspeed);
            mUUnits = " B/s";
        }

        totalSpeed = downspeed + upspeed;
        if (totalSpeed >= 1000000000) {
            mTotalSpeedWithDecimals = String.valueOf(df.format((float) totalSpeed / (float) 1000000000));
            mTUnits = " GB/s";

        } else if (upspeed >= 1000000) {
            mTotalSpeedWithDecimals = String.valueOf(df.format((float) totalSpeed / (float) 1000000));
            mTUnits = " MB/s";

        } else if (upspeed >= 1000) {
            mTotalSpeedWithDecimals = String.valueOf((int) (totalSpeed / 1000));
            mTUnits = " KB/s";

        } else {
            mTotalSpeedWithDecimals = "0";
            mTUnits = " KB/s";
        }

        todaydata+=totalSpeed;

        if (todaydata >= 1000000000) {
            mTotalMobileData = String.valueOf(df.format((float) todaydata / (float) 1000000000));
            mMTUnits = " GB/s";

        } else if (todaydata >= 1000000) {
            mTotalMobileData = String.valueOf(df.format((float) todaydata / (float) 1000000));
            mMTUnits = " MB/s";

        } else if (todaydata >= 1000) {
            mTotalMobileData = String.valueOf((int) (todaydata / 1000));
            mMTUnits = " KB/s";

        }

*
* */


//    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
//    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            Log.e(TAG, " U " + upspeed + " D " + downspeed);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
//            downspeed = nc.getLinkDownstreamBandwidthKbps();
//            upspeed = nc.getLinkUpstreamBandwidthKbps();
////            displaynotification(upspeed, downspeed);
//        }


//                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                        .setContentTitle("Down: " + mDownloadSpeedWithDecimals + " " + mDUnits + "   Up: " + mUploadSpeedWithDecimals + " " + mUUnits)
//                        .setContentText("")
//                        .setSmallIcon(R.drawable.ic_baseline_speed_24)
//                        .setContentIntent(pendingIntent)
//                        .setNotificationSilent()
//                        .setShowWhen(false)
//                        .build();
//
//                NotificationCompat.Builder builder =
//                        new NotificationCompat.Builder(this)
//                                .setSmallIcon() //set icon for notification
//                                .setContentTitle("Notifications Example") //set title of notification
//                                .setContentText("This is a notification message")//this is notification message
//                                .setAutoCancel(true) // makes auto cancel of notification
//                                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification
//                Drawable d = new BitmapDrawable(getResources(),bitmap);