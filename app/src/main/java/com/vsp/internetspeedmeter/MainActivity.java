package com.vsp.internetspeedmeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vsp.internetspeedmeter.BroadcastReciever.InternetService;
import com.vsp.internetspeedmeter.BroadcastReciever.ResetWork;
import com.vsp.internetspeedmeter.Model.DisplayModel;
import com.vsp.internetspeedmeter.Recyclerview.UsageAdapter;
import com.vsp.internetspeedmeter.Room.Usage;
import com.vsp.internetspeedmeter.Room.UsageViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "1";
    public static final String CHANNEL_NAME = "SpeedNoti";
    public static final String CHANNEL_DESC = "Hii there";
    public static final String TAG = "internetspeed";

    List<String> date = new ArrayList<>(), mobile = new ArrayList<>(), wifi = new ArrayList<>(), total = new ArrayList<>();
    private UsageViewModel viewModel;
    RecyclerView recyclerView;
    UsageAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NotificationService notificationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclervewUsage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(UsageViewModel.class);
        adapter = new UsageAdapter(this, new DisplayModel(date, mobile, wifi, total));
        preferences = getSharedPreferences("internetSpeed", MODE_PRIVATE);
        editor = preferences.edit();
        if (!preferences.contains("isStarted")) {
            editor.putBoolean("isStarted", false);
            editor.apply();
        }

//        notificationService = new NotificationService(this);
//        Gson gson = new Gson();
//        String s = gson.toJson(notificationService);

        viewModel.getAllNotes().observe(this, new Observer<List<Usage>>() {
            @Override
            public void onChanged(List<Usage> usages) {
                Log.e(TAG, "onChanged: " + usages.size());

                for (int i = 0; i < usages.size(); i++) {
                    Usage usage = usages.get(i);
                    if (date.size() != 0) {
                        date.set(i, usage.getdate());
                        mobile.set(i, usage.getMobile());
                        wifi.set(i, usage.getWifi());
                        total.set(i, usage.getTotal());
                    } else {
                        date.add(i, usage.getdate());
                        mobile.add(i, usage.getMobile());
                        wifi.add(i, usage.getWifi());
                        total.add(i, usage.getTotal());
                    }


                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "onChanged: " + usage.getMobile() + "   " + usage.getdate());
                }
            }
        });

        recyclerView.setAdapter(adapter);

        check();


    }

    private void check() {
        if ((!preferences.contains("isStarted")) || !preferences.getBoolean("isStarted", false)) {
            editor.putBoolean("isStarted", true);
            editor.apply();
            Intent serviceintent = new Intent(this, InternetService.class);
            startService(serviceintent);


        }

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