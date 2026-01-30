package com.carro.chauffeur.utils;


import com.carro.chauffeur.BuildConfig;

public class ImagePathDecider {

    public static String getUserImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"vendor/";
    }
    public static String getDriverImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"driver/";
    }
    public static String getCarImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"cars/";
    }
    public static String getNotificationImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"Notification/";
    }
    public static String getSliderImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"slider/";
    }
    public static String getBookingImgPath(){
        return BuildConfig.BASE_IMAGE_URL+"booking/";
    }
    public static String getAdvertisementPath(){
        return BuildConfig.BASE_IMAGE_URL+"apps/";
    }
}
