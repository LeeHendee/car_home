package com.example.gtercn.car.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity堆栈式管理
 *
 */
public class AppManager {
    private static final String TAG = AppManager.class.getSimpleName();

    private static Stack<Activity> mStack;

    private static AppManager mInstance;

    private AppManager() {}

    public static AppManager getInstance() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                if (mInstance == null) {
                    mInstance = new AppManager();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        if (mStack == null) {
            mStack = new Stack<>();
        }
        if(activity != null)
            mStack.add(activity);
    }

    public Activity currentActivity() {
        return mStack.lastElement();
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            mStack.remove(activity);
            activity.finish();
        }
    }

    public void finishAllActivity() {
       for (Activity activity : mStack)  {
           activity.finish();
        }
        mStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
