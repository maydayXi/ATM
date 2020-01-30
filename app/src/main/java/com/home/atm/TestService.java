package com.home.atm;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends IntentService {

    public static final String ACTION_DONE = "Action Done";
    private static final String TAG = TestService.class.getSimpleName();

    public TestService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "onStartCommand: ");
//        return START_STICKY;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String result = "";
        if (intent != null) {
            result = intent.getStringExtra("name");
        }
        Log.d(TAG, "onHandleIntent: " + result);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initialBroadcast();
    }

    // <summary> 建立廣播機制 </summary>
    private void initialBroadcast() {
        // 宣告廣播用 intent
        Intent doneIntent = new Intent();
        // 設定 Action 字串
        doneIntent.setAction(ACTION_DONE);
        // 送出廣播
        sendBroadcast(doneIntent);
    }
}
