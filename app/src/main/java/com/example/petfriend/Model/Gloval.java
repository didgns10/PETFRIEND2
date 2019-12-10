package com.example.petfriend.Model;

public class Gloval {
    private static double latitude = 0;
    private static double longitude =0;
    private static String state=null;

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        Gloval.state = state;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Gloval.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Gloval.longitude = longitude;
    }
}
