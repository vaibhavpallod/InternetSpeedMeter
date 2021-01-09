package com.vsp.internetspeedmeter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Build;

import com.vsp.internetspeedmeter.Model.Speed;

import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_DESC;
import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_ID;
import static com.vsp.internetspeedmeter.MainActivity.CHANNEL_NAME;

public class NotificationService {
    private  Notification.Builder mBuilder;
    private  NotificationManager mNotifyMgr;
    private PendingIntent pendingIntent;
    Context context;
    Bitmap bitmap;

    public NotificationService(Context context) {
        this.context = context;
        createNotification();
    }

    public void createNotification(){
        createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            mBuilder = new Notification.Builder(context);
        }
        Intent notifiIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context,
                100, notifiIntent, 0
        );
        mBuilder.setVisibility(Notification.VISIBILITY_SECRET);
        mBuilder.setOngoing(true);
        mBuilder.setShowWhen(false);
        mBuilder.setContentIntent(pendingIntent);
        mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotifyMgr.notify(1, mBuilder.build());

    }

    public Notification.Builder updateNotification(Speed speed){

        speed= new Speed(0,0,0,0);
        icon= Icon.createWithBitmap(speed.createBitmapFromString("0", "KB/s"));
        mBuilder.setSmallIcon(icon);//Icon.createWithBitmap(speed.createBitmapFromString(mTotalMobileData, mMTUnits))
        mBuilder.setContentTitle("Down: " + 0 + " " + "B/s" + "   Up: " + 0 + " " + "B/s");
        mBuilder.setContentText("Mobile: " + 0 + " " + "MB");


    return mBuilder;
    }


    public void setupIcon(String speed, String units) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(55);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        Paint unitsPaint = new Paint();
        unitsPaint.setAntiAlias(true);
        unitsPaint.setTextSize(40); // size is in pixels
        unitsPaint.setTextAlign(Paint.Align.CENTER);
        unitsPaint.setTypeface(Typeface.DEFAULT_BOLD);

       /* Rect textBounds = new Rect();
        paint.getTextBounds(speed, 0, speed.length(), textBounds);

        Rect unitsTextBounds = new Rect();
        unitsPaint.getTextBounds(units, 0, units.length(), unitsTextBounds);
*/
//        int width = (textBounds.width() > unitsTextBounds.width()) ? textBounds.width() : unitsTextBounds.width();

        bitmap = Bitmap.createBitmap(96, 96,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(speed, 48 + 5, 50, paint);
        canvas.drawText(units, 48, 95, unitsPaint);
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
