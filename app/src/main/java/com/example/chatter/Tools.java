package com.example.chatter;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static void setSystemBarColorInt(Activity activity, @ColorInt int color) {

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);

    }

    public static String getFormattedTimeEvent(long dateTime) {

        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(dateTime));

    }

}
