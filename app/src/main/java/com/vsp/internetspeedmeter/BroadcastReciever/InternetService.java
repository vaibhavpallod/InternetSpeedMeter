package com.vsp.internetspeedmeter.BroadcastReciever;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.TrafficStats;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.vsp.internetspeedmeter.MainActivity;
import com.vsp.internetspeedmeter.Model.OnCompleteListener;
import com.vsp.internetspeedmeter.Model.Speed;
import com.vsp.internetspeedmeter.R;

import androidx.annotation.Nullable;

import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_ID;
import static com.vsp.internetspeedmeter.MainActivity.TAG;

public class InternetService extends IntentService {
    private boolean checkSpeed = false;
//    float todaydata = 0;
//    String mDownloadSpeedWithDecimals, mUploadSpeedWithDecimals, mTotalSpeedWithDecimals, mTotalMobileData, mDUnits, mUUnits, mTUnits = "B/s", mMTUnits = "MB";


    private Speed speed;
    private  Icon icon;

    public InternetService() {
        super(IntentService.class.getName());
    }
   @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        checkSpeed = true;




        while (checkSpeed) {

            if (checkSpeed) {
                long dPreBytes = TrafficStats.getMobileRxBytes();
                long uPreBytes = TrafficStats.getMobileTxBytes();

                long dPostBytes = TrafficStats.getMobileRxBytes();
                long uPostBytes = TrafficStats.getMobileTxBytes();

                speed.getSpeed(new OnCompleteListener<String>() {
                    @Override
                    public void OnComplete(@Nullable String mDownloadSpeedWithDecimals, @Nullable String mDUnits, @Nullable String mUploadSpeedWithDecimals, @Nullable String mUUnits, @Nullable String mTotalMobileData, @Nullable String mMTUnits) {
                        speed= new Speed(dPreBytes, dPostBytes, uPreBytes, uPostBytes);


//                        startForeground(1, mBuilder.build());


                    }
                });


//                Log.e(TAG, "Down: " + mDownloadSpeedWithDecimals + " " + mDUnits + " Up " + mUploadSpeedWithDecimals + " " + mUUnits);
            }
        }
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