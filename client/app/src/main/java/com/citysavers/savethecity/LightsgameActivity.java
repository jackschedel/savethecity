package com.citysavers.savethecity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.citysavers.savethecity.databinding.ActivityLightsgameBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LightsgameActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private View mContentView;

    private boolean light1state = false;
    private boolean light2state = false;
    private Boolean[] lights = new Boolean[8];
    private ImageView[] lightsI = new ImageView[8];

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private ActivityLightsgameBinding binding;


    public int counter = 20;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLightsgameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing the lights to be randomly on or off
        Random rand = new Random();
        int upperBound = 2;
        for(int i = 0; i < lights.length; i++) {
            int int_random = rand.nextInt(upperBound);
            if(int_random == 0) {
                lights[i] = false;
            } else {
                lights[i] = true;
            }
        }
        lightsI[0] = (ImageView)findViewById(R.id.upLamp);
        lightsI[1] = (ImageView)findViewById(R.id.botLamp);
        lightsI[2] = (ImageView)findViewById(R.id.comp);
        lightsI[3] = (ImageView)findViewById(R.id.phone);
        lightsI[4] = (ImageView)findViewById(R.id.upstairs);
        lightsI[5] = (ImageView)findViewById(R.id.downstairs);
        lightsI[6] = (ImageView)findViewById(R.id.smallLamp);

        for(int i = 0; i < 7; i++) {
            if(lights[i]){
                lightsI[i].setImageAlpha(255);
            } else{
                lightsI[i].setImageAlpha(0);
            }
        }

        timer= (TextView) findViewById(R.id.timer);

        new CountDownTimer(20000, 1000){
            public void onTick(long millisUntilFinished){
                timer.setText(String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                timer.setText("FINISH!!");
            }
        }.start();

        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(0);


    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            mContentView.getWindowInsetsController().show(
                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void upLampB_onClick(View v){
        if(lights[0]){
            //If light was on, turn off
            lightsI[0].setImageAlpha(0);
            lights[0] = false;
        }else {
            //If light was off, turn on
            lightsI[0].setImageAlpha(255);
            lights[0] = true;
        }
    }
    public void botLampB_onClick(View v){
        if(lights[1]){
            //If light was on, turn off
            lightsI[1].setImageAlpha(0);
            lights[1] = false;
        }else {
            //If light was off, turn on
            lightsI[1].setImageAlpha(255);
            lights[1] = true;
        }
    }
    public void compB_onClick(View v){
        if(lights[2]){
            //If light was on, turn off
            lightsI[2].setImageAlpha(0);
            lights[2] = false;
        }else {
            //If light was off, turn on
            lightsI[2].setImageAlpha(255);
            lights[2] = true;
        }
    }
    public void phoneB_onClick(View v){
        if(lights[3]){
            //If light was on, turn off
            lightsI[3].setImageAlpha(0);
            lights[3] = false;
        }else {
            //If light was off, turn on
            lightsI[3].setImageAlpha(255);
            lights[3] = true;
        }
    }
    public void upstairsB_onClick(View v){
        if(lights[4]){
            //If light was on, turn off
            lightsI[4].setImageAlpha(0);
            lights[4] = false;
        }else {
            //If light was off, turn on
            lightsI[4].setImageAlpha(255);
            lights[4] = true;
        }
    }
    public void downstairsB_onClick(View v){
        if(lights[5]){
            //If light was on, turn off
            lightsI[5].setImageAlpha(0);
            lights[5] = false;
        }else {
            //If light was off, turn on
            lightsI[5].setImageAlpha(255);
            lights[5] = true;
        }
    }
    public void smallLampB_onClick(View v){
        if(lights[6]){
            //If light was on, turn off
            lightsI[6].setImageAlpha(0);
            lights[6] = false;
        }else {
            //If light was off, turn on
            lightsI[6].setImageAlpha(255);
            lights[6] = true;
        }
    }

}