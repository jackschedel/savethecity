package com.citysavers.savethecity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.citysavers.savethecity.databinding.ActivityFishgameBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FishgameActivity extends AppCompatActivity {
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
    private ActivityFishgameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFishgameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;


        // Set up the user interaction to manually show or hide the system UI.

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(0);

        //ImageButton trashButton1 = (ImageButton)findViewById(R.id.trashButton1);

        ImageButton[] buttonArray = {
                (ImageButton)findViewById(R.id.trashButton1),
                (ImageButton)findViewById(R.id.trashButton2),
                (ImageButton)findViewById(R.id.trashButton3),
                (ImageButton)findViewById(R.id.fishButton1),
                (ImageButton)findViewById(R.id.fishButton2),
                (ImageButton)findViewById(R.id.fishButton3),
                (ImageButton)findViewById(R.id.fishButton5),
                (ImageButton)findViewById(R.id.fishButton6),
                (ImageButton)findViewById(R.id.fishButton7)};

        List<ImageButton> buttonList = Arrays.asList(buttonArray);

        Collections.shuffle(buttonList);

        buttonList.toArray(buttonArray);

        ImageView riverImageElem2 = (ImageView)findViewById(R.id.riverImage2);

        double speedVar = 1.0;

        new CountDownTimer((long) (25200* (1/speedVar)), 1){

            boolean bobbingUp1 = true;
            boolean bobbingUp2 = true;
            boolean bobbingUp3 = true;

            boolean riverImageBool = true;

            int iterations = 0;

            int path1X = 1000;
            int path1Y = 285;

            int path2X = 1000;
            int path2Y = 285;

            int path3X = 1000;
            int path3Y = 285;

            int offsetVar = 500;

            double angleOffset = .74;

            public void onTick(long millisUntilFinished) {

                buttonArray[0].setX(path1X);
                buttonArray[0].setY(path1Y);

                buttonArray[1].setX(path2X + offsetVar);
                buttonArray[1].setY(path2Y - offsetVar);

                buttonArray[2].setX(path3X + 2 * offsetVar);
                buttonArray[2].setY(path3Y - 2 * offsetVar);

                buttonArray[3].setX(path1X + 3 * offsetVar);
                buttonArray[3].setY(path1Y - 3 * offsetVar);

                buttonArray[4].setX(path2X + 4 * offsetVar);
                buttonArray[4].setY(path2Y - 4 * offsetVar);

                buttonArray[5].setX(path3X + 5 * offsetVar);
                buttonArray[5].setY(path3Y - 5 * offsetVar);

                buttonArray[6].setX(path1X + 6 * offsetVar);
                buttonArray[6].setY(path1Y - 6 * offsetVar);

                buttonArray[7].setX(path2X + 7 * offsetVar);
                buttonArray[7].setY(path2Y - 7 * offsetVar);

                buttonArray[8].setX(path3X + 8 * offsetVar);
                buttonArray[8].setY(path3Y - 8 * offsetVar);


                if (bobbingUp1) {
                    path1Y += 7.8 * speedVar * angleOffset;
                    path1X -= 2.8 * speedVar;
                } else {
                    path1Y += 3.8 * speedVar * angleOffset;
                    path1X -= 5.8 * speedVar;
                }
                if (bobbingUp2) {
                    path2Y += 7.8 * speedVar * angleOffset;
                    path2X -= 2.8 * speedVar;
                } else {
                    path2Y += 3.8 * speedVar * angleOffset;
                    path2X -= 5.8 * speedVar;
                }

                if (bobbingUp3) {
                    path3Y += 7.8 * speedVar * angleOffset;
                    path3X -= 2.8 * speedVar;
                } else {
                    path3Y += 3.8 * speedVar * angleOffset;
                    path3X -= 5.8 * speedVar;
                }


                if(iterations %21 == 0)
                    bobbingUp1 = !bobbingUp1;

                if(iterations %21 == 7)
                    bobbingUp2 = !bobbingUp2;

                if(iterations %21 == 14)
                    bobbingUp3 = !bobbingUp3;


                if(iterations %10 == 0) {

                    if (riverImageBool) {
                        riverImageElem2.setImageAlpha(255);
                    } else {
                        riverImageElem2.setImageAlpha(0);
                    }

                    riverImageBool = !riverImageBool;
                }

                iterations++;
            }
            public  void onFinish(){

            }
        }.start();




    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

        mVisible = true;

    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    int score = 0;


    public void fishButton1_onClick(View v){
        onLose();
    }



    public void trashButton1_onClick(View v) {
        ImageButton trashButton1 = (ImageButton)findViewById(R.id.trashButton1);


        if(trashButton1.getBackground() != null){
            score++;
        }

        trashButton1.setBackground(null);


        Log.e("owo", "test " + score);

    }

    private void onLose(){


    }


}