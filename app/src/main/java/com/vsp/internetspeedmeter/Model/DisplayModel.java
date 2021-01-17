package com.vsp.internetspeedmeter.Model;

import java.util.List;

public class DisplayModel {

    List<String> date, mobile, wifi, total;

    public DisplayModel(List<String> date, List<String> mobile, List<String> wifi, List<String> total) {
        this.date = date;
        this.mobile = mobile;
        this.wifi = wifi;
        this.total = total;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }

    public List<String> getWifi() {
        return wifi;
    }

    public void setWifi(List<String> wifi) {
        this.wifi = wifi;
    }

    public List<String> getTotal() {
        return total;
    }

    public void setTotal(List<String> total) {
        this.total = total;
    }
}