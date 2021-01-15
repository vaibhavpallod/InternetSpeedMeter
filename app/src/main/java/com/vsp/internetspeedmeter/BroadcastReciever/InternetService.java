package com.vsp.internetspeedmeter.BroadcastReciever;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.vsp.internetspeedmeter.Model.Speed;
import com.vsp.internetspeedmeter.NotificationService;
import com.vsp.internetspeedmeter.Room.UsageViewModel;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class InternetService extends Service {
//    float todaydata = 0;
//    String mDownloadSpeedWithDecimals, mUploadSpeedWithDecimals, mTotalSpeedWithDecimals, mTotalMobileData, mDUnits, mUUnits, mTUnits = "B/s", mMTUnits = "MB";

    private boolean mNotificationCreated = false;

    private Speed speed;
    private Icon icon;
    NotificationService notificationService;
    long dPreBytes = 0, dPostBytes = 0, uPreBytes = 0, uPostBytes = 0;
    final private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        notificationService = new NotificationService(this);
        speed = new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            dPreBytes = dPostBytes;
            uPreBytes = uPostBytes;

            dPostBytes = TrafficStats.getMobileRxBytes();
            uPostBytes = TrafficStats.getMobileTxBytes();

            speed = new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);


            startForeground(1, notificationService.updateNotification(speed).build());

            handler.postDelayed(runnable, 1000);
        }
    };


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        notificationService.createNotification();
        startForeground(1,notificationService.updateNotification(speed).build());
        restartNotifying();


        return START_REDELIVER_INTENT;
    }

    private void restartNotifying() {
        Log.e("xxxxxxxxx","restarted");

        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





}


/* @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        checkSpeed = true;


        while (checkSpeed) {

            if (checkSpeed) {
                long dPreBytes = dPostBytes;
                long uPreBytes = uPostBytes;
                long dPostBytes = TrafficStats.getMobileRxBytes();
                long uPostBytes = TrafficStats.getMobileTxBytes();

                speed = new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);


                startForeground(1, notificationService.updateNotification(speed).build());


//                Log.e(TAG, "Down: " + mDownloadSpeedWithDecimals + " " + mDUnits + " Up " + mUploadSpeedWithDecimals + " " + mUUnits);
            }
        }
    }*/



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