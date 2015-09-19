package com.basilarray.futurebounce;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends IntentService {
    public MyService(String name) {
        super(name);
    }

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public IBinder onBind(Intent subIntent) {
        return super.onBind(subIntent);
    }

    @Override
    public int onStartCommand(Intent subIntent, int flags, int startId) {
// notification is selected

        Intent intent = new Intent(this, MainActivity.class); // Intent intent = new Intent(this, NotificationReceiver.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Kitty fountain cleaning")
                .setContentText("Replaced the kitty water and will need to do so again")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_stat_name, "Later", pIntent)
                .addAction(0, "Done", pIntent)
                .addAction(0, "More", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

        Toast.makeText(this, "On Bind Toast", Toast.LENGTH_LONG).show();

        return Service.START_STICKY;
        //return super.onStartCommand(subIntent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "On Destroy Toast", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
