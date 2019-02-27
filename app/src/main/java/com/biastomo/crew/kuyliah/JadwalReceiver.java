package com.biastomo.crew.kuyliah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by andy on 28/11/2017.
 */
public class JadwalReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    Vibrator vibrator;

    @Override
    public void onReceive(Context c, Intent arg1) {
        // TODO Auto-generated method stub
        Intent i=new Intent();
        i.setClassName("com.biastomo.crew.kuyliah", "com.biastomo.crew.kuyliah.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        c.startActivity(i);


        mp = MediaPlayer.create(c, R.raw.kuyliah);
        //vibrator=(Vibrator)System(c.VIBRATOR_SERVICE);
        mp.start();
        mp.setLooping(true);
        Toast.makeText(c, "Kuyliah!! Hari ini ada jadwal kuliah, jangan terlambat!", Toast.LENGTH_LONG).show();

    }

}
