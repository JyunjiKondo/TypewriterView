package com.github.jyunjikondo.typewriterview.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;

import com.github.jyunjikondo.typewriterview.TypewriterView;

import java.lang.reflect.Type;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TypewriterView view = (TypewriterView) findViewById(R.id.typewriter);
        view.setListener(new TypewriterView.Listener() {
            @Override
            public void onTypeFinished() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlphaAnimation anim = new AlphaAnimation(1f, 0f);
                        anim.setDuration(1000);
                        anim.setFillAfter(true);
                        view.startAnimation(anim);
                    }
                });
            }
        });
        view.setText(getString(R.string.text));
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
