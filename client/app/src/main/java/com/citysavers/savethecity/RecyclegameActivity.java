package com.citysavers.savethecity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.citysavers.savethecity.databinding.ActivityRecyclegameBinding;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RecyclegameActivity extends AppCompatActivity {
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
    private ActivityRecyclegameBinding binding;

    public int counter = 30;
    TextView timer;
    public boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        running = true;
        super.onCreate(savedInstanceState);

        binding = ActivityRecyclegameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;
        setRandItem(mControlsView);
        //calculateTime(mControlsView);

        timer= (TextView) findViewById(R.id.timer);

        new CountDownTimer(counter * 1000, 1000){
            public void onTick(long millisUntilFinished){
                if (running) {
                    timer.setText("Time Left: " + String.valueOf(counter));
                    counter--;
                }
            }
            public  void onFinish(){
                timer.setText("WIN!!");
                gameEnd(true);
            }
        }.start();
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

    //added functions
    public void calculateTime(View v){
        //here it gets value from database and performs calculation

        //as of right now it will return 9
        TextView timer = findViewById(R.id.timer);

        String time = "Time Left: 9";
        timer.setText(time);

        Intent intent = getIntent();
    }

    public void setRandItem(View v){
        String[] itemArray = new String[15];
        itemArray[0] = "@drawable/apple";
        itemArray[1] = "@drawable/bananna";
        itemArray[2] = "@drawable/bottle";
        itemArray[3] = "@drawable/box";
        itemArray[4] = "@drawable/chips";
        itemArray[5] = "@drawable/fishbones";
        itemArray[6] = "@drawable/glass";
        itemArray[7] = "@drawable/glassbottle";
        itemArray[8] = "@drawable/jar";
        itemArray[9] = "@drawable/newspaper";
        itemArray[10] = "@drawable/paper";
        itemArray[11] = "@drawable/plasticcontainer";
        itemArray[12] = "@drawable/shoe";
        itemArray[13] = "@drawable/toothbrush";
        itemArray[14] = "@drawable/trashbag";

        Random rand = new Random();
        int upperBound = 15;
        int int_random = rand.nextInt(upperBound);

        ImageView item = findViewById(R.id.item);
        int imageResource = getResources().getIdentifier(itemArray[int_random], null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        item.setImageDrawable(res);
        item.setTag(Integer.toString(int_random));
    }

    public void glassBin_onClick(View v){
        ImageView item = findViewById(R.id.item);
        if (item.getTag().toString().equals("6") || item.getTag().toString().equals("7") || item.getTag().toString().equals("8")){
            //correct sort
            TextView score = findViewById(R.id.score);
            int numScore = Integer.parseInt(score.getText().toString());
            numScore++;
            score.setText(Integer.toString(numScore));
        } else {
            //incorrect sort
            gameEnd(false);
        }

        setRandItem(v);
    }
    public void paperBin_onClick(View v){
        ImageView item = findViewById(R.id.item);
        if (item.getTag().toString().equals("3") || item.getTag().toString().equals("9") || item.getTag().toString().equals("10")){
            System.out.println("correct");
            //correct sort
            TextView score = (TextView) findViewById(R.id.score);
            int numScore = Integer.parseInt(score.getText().toString());
            numScore++;
            score.setText(Integer.toString(numScore));

        } else {
            //incorrect sort
            gameEnd(false);
        }

            setRandItem(v);
    }
    public void orgBin_onClick(View v){
        ImageView item = findViewById(R.id.item);
        if (item.getTag().toString().equals("0") || item.getTag().toString().equals("1") || item.getTag().toString().equals("5")){
            System.out.println("correct");
            //correct sort
            TextView score = (TextView) findViewById(R.id.score);
            int numScore = Integer.parseInt(score.getText().toString());
            numScore++;
            score.setText(Integer.toString(numScore));
        } else {
            //incorrect sort
            gameEnd(false);
        }

            setRandItem(v);
    }
    public void plasticBin_onClick(View v){
        ImageView item = findViewById(R.id.item);
        if (item.getTag().toString().equals("2") || item.getTag().toString().equals("11") || item.getTag().toString().equals("4")){
            System.out.println("correct");
            //correct sort
            TextView score = (TextView) findViewById(R.id.score);
            int numScore = Integer.parseInt(score.getText().toString());
            numScore++;
            score.setText(Integer.toString(numScore));
        } else {
            //incorrect sort
            gameEnd(false);
        }

            setRandItem(v);
    }
    public void furnace_onClick(View v){
        ImageView item = findViewById(R.id.item);
        if (item.getTag().toString().equals("12") || item.getTag().toString().equals("13") || item.getTag().toString().equals("14")){
            System.out.println("correct");
            //correct sort
            TextView score = (TextView) findViewById(R.id.score);
            int numScore = Integer.parseInt(score.getText().toString());
            numScore++;
            score.setText(Integer.toString(numScore));
        } else {
            //incorrect sort
            gameEnd(false);
        }

            setRandItem(v);
    }

    public void gameEnd(Boolean win){
        Button g = findViewById(R.id.glass);
        Button o = findViewById(R.id.organic);
        Button pa = findViewById(R.id.paper);
        Button pl = findViewById(R.id.plastic);
        Button f = findViewById(R.id.furnace);
        g.setEnabled(false);
        o.setEnabled(false);
        pa.setEnabled(false);
        pl.setEnabled(false);
        f.setEnabled(false);

        running = false;

        if (win) {

        } else {
            timer.setText("Fail!!");
        }
    }
}