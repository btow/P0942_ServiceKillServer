package com.example.samsung.p0942_servicekillserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    final String LOG_TAG = "myLogs";
    private String message = "";

    @Override
    public void onCreate() {
        super.onCreate();
        message = "MyService onCreate()";
        Log.d(LOG_TAG, message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        message = "MyService onDestroy()";
        Log.d(LOG_TAG, message);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        message = "MyService onStartCommand()";
        Log.d(LOG_TAG, message);
        readeFlags(flags);
        MyRun myRun = new MyRun(startId);
        new Thread(myRun).start();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void readeFlags(int flags) {
        if ((flags&START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY) {
            message = "START_FLAG_REDELIVERY";
            Log.d(LOG_TAG, message);
        }
        if ((flags&START_FLAG_RETRY) == START_FLAG_RETRY) {
            message = "START_FLAG_RETRY";
            Log.d(LOG_TAG, message);
        }
    }

    private class MyRun implements Runnable {

        int startId;

        public MyRun(int startId) {
            this.startId = startId;
            message = "MyRun#" + startId + " create";
            Log.d(LOG_TAG, message);
        }

        @Override
        public void run() {
            message = "MyRun#" + startId + " start";
            Log.d(LOG_TAG, message);
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        private void stop() {
            message = "MyRun#" + startId + " end, stopSelfResult(" + startId + ") = " + stopSelfResult(startId);
            Log.d(LOG_TAG, message);
        }
    }
}
