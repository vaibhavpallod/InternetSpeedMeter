package com.vsp.internetspeedmeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.NetworkEvent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.vsp.internetspeedmeter.BroadcastReciever.InternetService;

public class MainActivity extends AppCompatActivity{
    public static final String CHANNEL_ID = "1";
    public static final String CHANNEL_NAME = "SpeedNoti";
    public static final String CHANNEL_DESC = "Hii there";
public static final String TAG = "internetspeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent serviceintent = new Intent(this, InternetService.class);
        ContextCompat.startForegroundService(this,serviceintent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public  void stopService(){
        Intent serviceintent = new Intent(this, InternetService.class);
        stopService(serviceintent);
    }





  /*  public void displaynotification(int up, int down) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_internet_speed_24)
                        .setContentTitle("Speed: " + down / 1024 + "  KB")
                        .setContentText("This is a test notification")
                        .setPriority(NotificationCompat.PRIORITY_MAX);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(1, builder.build());
    }
*/
    private void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }
}