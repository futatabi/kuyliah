package com.biastomo.crew.kuyliah;

/**
 * Created by andy on 25/11/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread splash=new Thread(){
            public void run(){
                try{
                    sleep(2500);
                } catch(InterruptedException a){
                    a.printStackTrace();
                }finally{
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        };
        splash.start();
    }

}
