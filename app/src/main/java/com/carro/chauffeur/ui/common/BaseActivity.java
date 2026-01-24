package com.carro.chauffeur.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.carro.chauffeur.BuildConfig;
import com.carro.chauffeur.widgets.CustomProgressDialog;


public class BaseActivity extends AppCompatActivity {

    Dialog mProgressDialog;
    public static BaseActivity baseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        //progress dialog
        mProgressDialog = new CustomProgressDialog(this);

    }

    /**
     * show loader
     */
    public void showLoader() {
        try {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Hide Loader
     */
    public void hideLoader() {
        try {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }


    /**
     * Show Error
     */
    public void showError(String msg) {
        if (msg == null) return;
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Show alert
     */
    public void showAlert(String msg) {
        if (msg == null) return;
        Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void log_d(String className, String message) {
        if (BuildConfig.DEBUG)
            Log.d(className, "" + message);
    }

    public void log_e(String className, String message, Exception e) {
        if (BuildConfig.DEBUG)
            Log.e(className, "" + message, e);
    }


}
