package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ps.edu.israaNow.storge.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

        private static int SPLASH_TIMER = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(SplashScreen.this).getUser().getId() == -1) {
                    Intent homeIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }

        }, SPLASH_TIMER);
    }
}
