package com.vsp.internetspeedmeter.BroadcastReciever;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vsp.internetspeedmeter.NotificationService;
import com.vsp.internetspeedmeter.R;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.vsp.internetspeedmeter.MainActivity.TAG;

public class ResetWork extends Worker {
    NotificationService notificationService;
    public ResetWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.e(TAG, "ResetWork: "+context + " work" +workerParams);
        notificationService=new Gson().fromJson( workerParams.getInputData().getString("noticontext"),NotificationService.class);

    }

    @NonNull
    @Override
    public Result doWork() {

        Log.e(TAG, "ResetWork: doing work");
        notificationService.updateDate();
        displayNotification(notificationService.myDate,"working");
        return Result.success();

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


}
