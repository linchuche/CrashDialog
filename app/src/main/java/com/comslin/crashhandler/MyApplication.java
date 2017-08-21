package com.comslin.crashhandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by linChao on 2017-08-21.
 */

public class MyApplication extends Application {
    static Activity currentActivity;
    String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.d(TAG, "uncaughtException: 1");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        try {Log.e("tet", "uncaughtException: "+Thread.currentThread().getName() );
                            Toast.makeText(AppManager.currentActivity(), "发生异常，正在退出", Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(AppManager.currentActivity()).
                                    setMessage("程序发生异常，现在退出").
                                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.e("test", "run: "+Thread.currentThread().getName());
                                                 System.exit(0);
                                                }
                                            }).start();

                                        }
                                    }).create().show();

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }
                }).start();
                Log.d(TAG, "uncaughtException: 2");

                    }
        });


    }
}
