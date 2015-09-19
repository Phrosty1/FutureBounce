package com.basilarray.futurebounce;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tmp", "Alarm onReceive Begin");
        Toast.makeText(context, "Alarm onReceive Begin", Toast.LENGTH_LONG).show();
        String title = intent.getStringExtra("title");
        //title = "Kitty fountain cleaning";
        String text = intent.getStringExtra("text");
        //text = "Replaced the kitty water and will need to do so again";
        Intent notIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_stat_name, "Later", pIntent)
                .addAction(0, "Done", pIntent)
                .addAction(0, "More", pIntent).build();
        notificationManager.notify(0, n);
        Toast.makeText(context, "Alarm onReceive End", Toast.LENGTH_LONG).show();

    }
}
