package com.vsp.internetspeedmeter.Room;




import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;


@Entity(tableName = "Usage_Table")
public class Usage {

    @NonNull
    @PrimaryKey()
    public String date;

    public String mobile, wifi, total;

    public Usage(@NonNull String date, String mobile, String wifi, String total) {
        this.date = date;
        this.mobile = mobile;
        this.wifi = wifi;
        this.total = total;
    }

    public String getdate() {
        return date;
    }

    public String getMobile() {
        return mobile;
    }

    public String getWifi() {
        return wifi;
    }

    public String getTotal() {
        return total;
    }
}
