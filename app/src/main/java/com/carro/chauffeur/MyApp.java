package com.carro.chauffeur;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //FirebaseApp.initializeApp(this);
    }
}
