package com.vsp.internetspeedmeter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vsp.internetspeedmeter.BroadcastReciever.InternetService;
import com.vsp.internetspeedmeter.Room.Usage;
import com.vsp.internetspeedmeter.Room.UsageViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "1";
    public static final String CHANNEL_NAME = "SpeedNoti";
    public static final String CHANNEL_DESC = "Hii there";
    public static final String TAG = "internetspeed";


    private UsageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(UsageViewModel.class);

        viewModel.getAllNotes().observe(this, new Observer<List<Usage>>() {
            @Override
            public void onChanged(List<Usage> usages) {
                for (Usage usage:usages){
                    Log.e(TAG, "onChanged: "+usage.getMobile()+"   "+usage.getdate());
                }
            }
        });

        Intent serviceintent = new Intent(this, InternetService.class);
        startService(serviceintent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void stopService() {
        Intent serviceintent = new Intent(this, InternetService.class);
        stopService(serviceintent);
    }

    private void toast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }
}