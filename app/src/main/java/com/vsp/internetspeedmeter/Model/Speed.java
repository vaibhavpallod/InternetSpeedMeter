package com.vsp.internetspeedmeter.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.text.DecimalFormat;

public class Speed {
    float todaydata = 0;
    String mDownloadSpeedWithDecimals, mUploadSpeedWithDecimals, mTotalSpeedWithDecimals, mTotalMobileData, mDUnits, mUUnits, mTUnits = "B/s", mMTUnits = "MB";
    long dPreBytes = 0,dPostBytes=0;
    long uPreBytes = 0,uPostBytes=0;


    public Speed(long dPreBytes, long dPostBytes, long uPreBytes, long uPostBytes) {
        this.dPreBytes = dPreBytes;
        this.dPostBytes = dPostBytes;
        this.uPreBytes = uPreBytes;
        this.uPostBytes = uPostBytes;
    }



    public void getSpeed(OnCompleteListener<String> onCompleteListener) {


        long downspeed = dPostBytes - dPreBytes;
        long upspeed = uPostBytes - uPreBytes;
        long totalSpeed = 0;

        DecimalFormat df = new DecimalFormat("#.00");
        if (downspeed >= 1000000000) {
            mDownloadSpeedWithDecimals = String.valueOf(df.format((float) downspeed / (float) 1000000000));
            mDUnits = " GB/s";
        } else if (downspeed >= 1000000) {
            mDownloadSpeedWithDecimals = String.valueOf(df.format((float) downspeed / (float) 1000000));
            mDUnits = " MB/s";

        } else if (downspeed >= 1000) {
            mDownloadSpeedWithDecimals = String.valueOf((int) (downspeed / 1000));
            mDUnits = " KB/s";
        } else {
            mDownloadSpeedWithDecimals = String.valueOf((int) downspeed);
            mDUnits = " B/s";
        }


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
        onCompleteListener.OnComplete(mDownloadSpeedWithDecimals,mDUnits,mUploadSpeedWithDecimals,mUUnits,mTotalSpeedWithDecimals,mTUnits);
    }



   /* class HumanSpeed {

        long prevSpeed, currentSpeed;
        String unit;

        HumanSpeed(long speed) {
            this.prevSpeed = speed;
        }


    }

    ;*/


}
