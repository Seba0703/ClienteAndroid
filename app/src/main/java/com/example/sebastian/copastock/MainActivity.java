package com.example.sebastian.copastock;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sebastian.copastock.Common.ActivityStarter;

public class MainActivity extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (ImageView) findViewById(R.id.imageView);
        logo.setImageResource(R.drawable.logo_copa);
        logo.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                goApp();
            }
        }, 3000);

    }

    public void goApp() {
        logo.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ActivityStarter.startClear(getApplicationContext(), LoginActivity.class);
            }
        }, 375);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
