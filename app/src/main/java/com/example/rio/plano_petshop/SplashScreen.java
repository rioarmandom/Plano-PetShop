package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by almantera on 10/11/16.
 */

public class SplashScreen extends Activity {

    private final int SPLASH_LENGTH = 800; // How long splash will showed up (in ms)

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle bundle) {
        // Show welcome screen
        super.onCreate(bundle);
        setContentView(R.layout.welcome_screen);
        // Add database
        databaseHelper = new DatabaseHelper(this);

        // Splash handle
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (databaseHelper.logStatus()) { // Check if user login or not
                    Intent mainIntent = new Intent(SplashScreen.this, MainMenu.class); // Redirect into MainMenu
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginPage.class); // Redirect into LoginPage
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_LENGTH);
    }

}
