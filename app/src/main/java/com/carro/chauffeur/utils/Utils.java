package com.carro.chauffeur.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static boolean isAdvShow=true;
    public static String getDate(long milliSeconds, String outputFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String changeDateFormat(String fromFormat, String toFormat, String dateStr) {

        SimpleDateFormat sdfIn = new SimpleDateFormat(fromFormat, Locale.US);
        Date date = null;
        try {
            date = sdfIn.parse(dateStr);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat sdfOut = new SimpleDateFormat(toFormat, Locale.US);
        String formattedTime = sdfOut.format(date);

        return formattedTime;

    }

    public static String formatDate(long millis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(millis);
        return dateFormat.format(date);
    }

    public static String formatTime(long millis) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return timeFormat.format(new Date(millis));
    }
    public static String formatTimeString(String fromFormat, String toFormat, String timeStr) {
        SimpleDateFormat sdfIn = new SimpleDateFormat(fromFormat, Locale.getDefault());
        Date date = null;
        try {
            date = sdfIn.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeStr; // Return the original string if parsing fails
        }
        SimpleDateFormat sdfOut = new SimpleDateFormat(toFormat, Locale.getDefault());
        return sdfOut.format(date);
    }


    public static String getMonthName(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String getMonthShortName(int monthNumber) {
        String monthName = "";

        if (monthNumber >= 1 && monthNumber <= 12) {
            try {
                Calendar calendar = Calendar.getInstance();
                // Adjust for zero-based indexing
                calendar.set(Calendar.MONTH, monthNumber - 1);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return monthName;
    }

    public static void openDialer(Context context, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            Toast.makeText(context, "Phone number not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber.trim()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openSms(Context context, String phoneNumber, String message) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            Toast.makeText(context, "Phone number not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber)); // only SMS apps
        intent.putExtra("sms_body", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void openRouteInGoogleMaps(
            Context context,
            String pickupLat,
            String pickupLng,
            String destLat,
            String destLng
    ) {

        String uri = "https://www.google.com/maps/dir/?api=1"
                + "&origin=" + pickupLat + "," + pickupLng
                + "&destination=" + destLat + "," + destLng
                + "&travelmode=driving";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // open in Google Maps only
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show();
        }
    }

}
