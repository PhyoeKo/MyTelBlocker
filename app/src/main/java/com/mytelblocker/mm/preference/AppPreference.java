package com.mytelblocker.mm.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.mytelblocker.mm.R;

import static com.mytelblocker.mm.constant.AppConstants.PHONE;

public class AppPreference {

    private SharedPreferences mSharedPreferences;

    public static final String KEY_SERVICE= "KEY_SERVICE";


    public AppPreference(Context context) {
        super();
        mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setServiceStatus(Boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(PHONE, value);
        editor.apply();
    }
    public Boolean getServiceStatus() {
        return mSharedPreferences.getBoolean(PHONE,false);
    }

}