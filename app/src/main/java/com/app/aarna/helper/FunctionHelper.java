package com.app.aarna.helper;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import java.util.List;


public class FunctionHelper {

    private static CustomLoader animProgressDialog;

    public static void disable_user_Intration(Context context, String text, FragmentManager manager) {
        if (!isAppIsInBackground(context)) {
            if (animProgressDialog == null || animProgressDialog.getContext() != context) {
                try {
                    animProgressDialog = new CustomLoader();
                    animProgressDialog.show(manager, animProgressDialog.getTag());
                } catch (WindowManager.BadTokenException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void enableUserIntraction(Context context) {
        if (context!=null) {
            if (!isAppIsInBackground(context)) {
                try {
                    if (animProgressDialog != null &&  animProgressDialog.isAdded()) {
                        animProgressDialog.dismiss();
                    }
                    animProgressDialog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            if (runningProcesses!=null){
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }





}
