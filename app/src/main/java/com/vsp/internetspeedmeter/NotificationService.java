package com.vsp.internetspeedmeter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import com.vsp.internetspeedmeter.Model.OnCompleteListener;
import com.vsp.internetspeedmeter.Model.Speed;
import com.vsp.internetspeedmeter.Room.Usage;
import com.vsp.internetspeedmeter.Room.UsageRepository;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;

import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_DESC;
import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_ID;
import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_NAME;
import static com.vsp.internetspeedmeter.MainActivity.TAG;

public class NotificationService {
    private Notification.Builder mBuilder;
    private NotificationManager mNotifyMgr;
    private PendingIntent pendingIntent;
    Context context;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint, unitsPaint;
    Icon icon;
    private UsageRepository usageRepository;
    Date c;
    SimpleDateFormat df;
    public String myDate;
    public int i = 0;

    public NotificationService(Context context) {
        this.context = context;
        createNotification();
        usageRepository = new UsageRepository(context);
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        myDate = df.format(c);
    }

    public void createNotification() {
        createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            mBuilder = new Notification.Builder(context);
        }
        setupIcon();
        Intent notifiIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context,
                100, notifiIntent, 0
        );
        icon = getIcon("0", "MB");
        mBuilder.setSmallIcon(icon);//Icon.createWithBitmap(speed.createBitmapFromString(mTotalMobileData, mMTUnits))
        mBuilder.setContentTitle("Down: " + 0 + " " + "MB" + "   Up: " + 0 + " " + "MB");
        mBuilder.setContentText("Mobile: " + 0 + " " + "MB");
        mBuilder.setVisibility(Notification.VISIBILITY_SECRET);
        mBuilder.setOngoing(true);
        mBuilder.setShowWhen(false);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setOnlyAlertOnce(true);

//        mBuilder.silent

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setBadgeIconType(0);
        }
        mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1, mBuilder.build());

    }

    public Notification.Builder updateNotification(Speed speed, long mTotalBytes) {

        speed.getSpeed(new OnCompleteListener<String>() {
            @Override
            public void OnComplete(@Nullable String mDownloadSpeedWithDecimals, @Nullable String mDUnits, @Nullable String mUploadSpeedWithDecimals, @Nullable String mUUnits, @Nullable String mTotalMobileData, @Nullable String mMTUnits) {
                DecimalFormat df1 = new DecimalFormat("#.00");
                icon = getIcon(mTotalMobileData, mMTUnits);
                if (mTotalBytes >= 1000000000) {
                    mTotalMobileData = String.valueOf(df1.format((float) mTotalBytes / (float) 1000000000));
                    mMTUnits = " GB ";

                } else if (mTotalBytes >= 1000000) {
                    mTotalMobileData = String.valueOf(df1.format((float) mTotalBytes / (float) 1000000));
                    mMTUnits = " MB";

                } else if (mTotalBytes >= 1000) {
                    mTotalMobileData = String.valueOf((int) (mTotalBytes / 1000));
                    mMTUnits = " KB";

                }

                mBuilder.setSmallIcon(icon);//Icon.createWithBitmap(speed.createBitmapFromString(mTotalMobileData, mMTUnits))
                mBuilder.setContentTitle("Down: " + mDownloadSpeedWithDecimals + " " + mDUnits + "   Up: " + mUploadSpeedWithDecimals + " " + mUUnits);
                mBuilder.setContentText("Mobile: " + mTotalMobileData + " " + mMTUnits);
                usageRepository.update(new Usage(myDate, mTotalMobileData + " " + mMTUnits, "300", "300"));
//                Log.e(TAG, "onChanged: "+mTotalMobileData+ " " + mMTUnits);
            }
        });


        return mBuilder;
    }

    public void updateDate() {
        c.setTime(c.getTime() + 86400000 * i);
        Log.e(TAG, "updateDate: i value " + i);
        i++;
//        c = Calendar.getInstance().setTimeInMillis();

        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        myDate = df.format(c);
        usageRepository.insert(new Usage(myDate, "0", "0", "0"));

    }

    public void setupIcon() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(65);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        unitsPaint = new Paint();
        unitsPaint.setAntiAlias(true);
        unitsPaint.setTextSize(40); // size is in pixels
        unitsPaint.setTextAlign(Paint.Align.CENTER);
        unitsPaint.setTypeface(Typeface.DEFAULT_BOLD);

     /*   Rect textBounds = new Rect();
        paint.getTextBounds("0", 0, 2, textBounds);

        Rect unitsTextBounds = new Rect();
        unitsPaint.getTextBounds("MB", 0, 3, unitsTextBounds);*/

//        int width = (textBounds.width() > unitsTextBounds.width()) ? textBounds.width() : unitsTextBounds.width();

        bitmap = Bitmap.createBitmap(96, 96,
                Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);

    }

    Icon getIcon(String speed, String units) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawText(speed, 48, 52, paint);
        canvas.drawText(units, 48, 95, unitsPaint);

        return Icon.createWithBitmap(bitmap);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}
