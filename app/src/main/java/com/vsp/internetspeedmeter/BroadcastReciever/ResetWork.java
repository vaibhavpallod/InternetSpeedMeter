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
//        notificationService=new Gson().fromJson( workerParams.getInputData().getString("noticontext"),NotificationService.class);

    }

    @NonNull
    @Override
    public Result doWork() {

        Log.e(TAG, "ResetWork: doing work");
        notificationService.updateDate();
        return Result.success();

    }



}
