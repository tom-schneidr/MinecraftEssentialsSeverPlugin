package io.essentials.utils;

import java.util.concurrent.TimeUnit;

public class Maths {
    
    public static boolean isNumeric(String str) {
        if(str == null) {
            return false;
        }
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String timeConverter(Long time){
        long days = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) % 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60L;

        return (days == 0L ? "" : days + " days ") + (hours == 0L ? "" : hours + " hours ") + (minutes == 0L ? "" : minutes + " minutes ") + (seconds == 0L ? "" : seconds + " seconds");
    }
}
