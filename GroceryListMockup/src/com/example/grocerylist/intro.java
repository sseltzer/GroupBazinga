/*
 * Group: BAZINGA!
 * @author: Gustavo Maturana
 * date: 11/01/13
*/

package com.example.grocerylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class intro extends Activity {
 
    // Intro screen timer
    private static int INTRO_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
 
        new Handler().postDelayed(new Runnable() {
        	/* This procedure will show the introduction screen */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(intro.this, MainActivity.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, INTRO_TIME_OUT);
    }
 
}
