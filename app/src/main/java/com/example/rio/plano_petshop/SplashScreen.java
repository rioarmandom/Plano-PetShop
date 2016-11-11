package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by almantera on 10/11/16.
 */

public class SplashScreen extends Activity {

    private final int SPLASH_LENGTH = 800;

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.welcome_screen);
        databaseHelper = new DatabaseHelper(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (databaseHelper.logStatus()) {
                    Intent mainIntent = new Intent(SplashScreen.this, MainMenu.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginPage.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_LENGTH);
    }

}
