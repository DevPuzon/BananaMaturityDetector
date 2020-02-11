package com.example.bananaanalyzer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {
    public Context context;
    public Activity activity;

    public Permission(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean checkReadExternal() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void reqReadExternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("Storage Permission")
                    .setMessage("Please allow storage permission in App Settings.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    public boolean checkCamera() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void reqCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("Camera Permission")
                    .setMessage("Please allow storage permission in App Settings.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}
