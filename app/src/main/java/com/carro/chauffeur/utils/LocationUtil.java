package com.carro.chauffeur.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationUtil {

    public interface LocationCallback {
        void onLocationReceived(double lat, double lng);
        void onLocationError(String error);
    }

    public static void getCurrentLocation(Context context, LocationCallback callback) {

        FusedLocationProviderClient fusedClient =
                LocationServices.getFusedLocationProviderClient(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            callback.onLocationError("Location permission not granted");
            return;
        }

        fusedClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(location -> {

            if (location != null) {
                callback.onLocationReceived(
                        location.getLatitude(),
                        location.getLongitude()
                );
            } else {
                callback.onLocationError("Unable to fetch location");
            }

        }).addOnFailureListener(e ->
                callback.onLocationError(e.getMessage())
        );
    }
}
