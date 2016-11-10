package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

/**
 * Created by almantera on 10/11/16.
 */

public class SplashScreen extends Activity {

    private final int SPLASH_LENGTH = 1000;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.welcome_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainMenu.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_LENGTH);
    }

}
