package com.biastomo.crew.kuyliah;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by andy on 26/11/2017.
 */
public class TugasReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    Vibrator vibrator;

    @Override
    public void onReceive(Context c, Intent arg1) {
        // TODO Auto-generated method stub
Intent i=new Intent();
        i.setClassName("com.biastomo.crew.kuyliah", "com.biastomo.crew.kuyliah.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        c.startActivity(i);


        mp = MediaPlayer.create(c, R.raw.kuynugas);
        //vibrator=(Vibrator)System(c.VIBRATOR_SERVICE);
        mp.start();
        mp.setLooping(true);
        Toast.makeText(c, "Kuy kerjakan!! Ada Tugas terbaru!", Toast.LENGTH_LONG).show();

            }

}
