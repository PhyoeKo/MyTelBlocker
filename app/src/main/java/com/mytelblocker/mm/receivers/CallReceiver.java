/*
 * Copyright © Ricki Hirner (bitfire web engineering).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package com.mytelblocker.mm.receivers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mytelblocker.mm.BlacklistActivity;
import com.mytelblocker.mm.ITelephony;
import com.mytelblocker.mm.R;
import com.mytelblocker.mm.preference.AppPreference;

import java.lang.reflect.Method;


public class CallReceiver extends BroadcastReceiver {
    private static final String TAG = "NoPhoneSpam";

    private static final int NOTIFY_REJECTED = 0;
    private static boolean AlreadyOnCall = false;
    private boolean enableService;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction()) &&
                intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            AppPreference appPreference = new AppPreference(context);
            enableService =  appPreference .getServiceStatus();

            if (incomingNumber == null)
                return;

            Log.i(TAG, "Received call: " +enableService);
            if(enableService)
            {
                if (incomingNumber.startsWith("096")||incomingNumber.startsWith("+9596")){
                    rejectCall(context, incomingNumber);
                }
            }

        }

             if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) AlreadyOnCall = true;
        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))    AlreadyOnCall = false;
    }

    @SuppressLint("MissingPermission")
    protected void rejectCall(@NonNull Context context, String number) {
        Log.i(TAG, "REJECT call: ");

        if (!AlreadyOnCall) {
            boolean failed = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

                try {
                    telecomManager.endCall();
                    Log.d(TAG, "Invoked 'endCall' on TelecomManager");
                } catch (Exception e) {
                    Log.e(TAG, "Couldn't end call with TelecomManager", e);
                    failed = true;
                }
            } else {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Method m = tm.getClass().getDeclaredMethod("getITelephony");
                    m.setAccessible(true);

                    ITelephony telephony = (ITelephony) m.invoke(tm);

                    telephony.endCall();
                } catch (Exception e) {
                    Log.e(TAG, "Couldn't end call with TelephonyManager", e);
                    failed = true;
                }
            }
            if (failed) {
                Toast.makeText(context, context.getString(R.string.call_blocking_unsupported), Toast.LENGTH_LONG).show();
            }
        }

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    "default", context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(context.getString(R.string.receiver_notify_call_rejected));
            notificationManager.createNotificationChannel(channel);
        }

        Notification notify = new NotificationCompat.Builder(context, "M_CH_ID")
                .setSmallIcon(R.drawable.ic_launcher_small)
                .setContentTitle(context.getString(R.string.receiver_notify_call_rejected))
                .setContentText(number)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, BlacklistActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .addPerson("tel:" + number)
                .setGroup("rejected")
                .setChannelId("default")
                .setGroupSummary(true)
                .build();

        String tag = number != null ? number : "private";
        NotificationManagerCompat.from(context).notify(tag, NOTIFY_REJECTED, notify);

    }

}
