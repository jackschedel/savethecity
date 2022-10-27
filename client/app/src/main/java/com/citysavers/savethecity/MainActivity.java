package com.citysavers.savethecity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.citysavers.savethecity.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tips, R.id.navigation_playscreen, R.id.navigation_city)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Intent intent = new Intent(MainActivity.this, FishgameActivity.class);
        // startActivity(intent);


    }
    public void waterB_onClick(View v){
        ConstraintLayout waterScreen = (ConstraintLayout)findViewById(R.id.water);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.GONE);
        waterScreen.setVisibility(View.VISIBLE);
    }
    public void back1_onClick(View v){
        ConstraintLayout waterScreen = (ConstraintLayout)findViewById(R.id.water);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.VISIBLE);
        waterScreen.setVisibility(View.GONE);
    }
    public void healthB_onClick(View v){
        ConstraintLayout healthScreen = (ConstraintLayout)findViewById(R.id.health);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.GONE);
        healthScreen.setVisibility(View.VISIBLE);
    }
    public void back2_onClick(View v){
        ConstraintLayout healthScreen = (ConstraintLayout)findViewById(R.id.health);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.VISIBLE);
        healthScreen.setVisibility(View.GONE);
    }
    public void pollutionB_onClick(View v){
        ConstraintLayout pollutionScreen = (ConstraintLayout)findViewById(R.id.pollution);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.GONE);
        pollutionScreen.setVisibility(View.VISIBLE);
    }
    public void back3_onClick(View v){
        ConstraintLayout pollutionScreen = (ConstraintLayout)findViewById(R.id.pollution);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.VISIBLE);
        pollutionScreen.setVisibility(View.GONE);
    }
    public void energyB_onClick(View v){
        ConstraintLayout energyScreen = (ConstraintLayout)findViewById(R.id.energy);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.GONE);
        energyScreen.setVisibility(View.VISIBLE);
    }
    public void back4_onClick(View v){
        ConstraintLayout energyScreen = (ConstraintLayout)findViewById(R.id.energy);
        ConstraintLayout tipsButtons = (ConstraintLayout)findViewById(R.id.tipsButtons);
        tipsButtons.setVisibility(View.VISIBLE);
        energyScreen.setVisibility(View.GONE);
    }


    public void startRandomGame(View v){

        Random rand = new Random();
        int upperBound = 3;
        int randGame = rand.nextInt(upperBound);

        if(randGame == 0){
            Intent intent = new Intent(MainActivity.this, FishgameActivity.class);
            startActivity(intent);
        }else if(randGame == 1){
            Intent intent = new Intent(MainActivity.this, RecyclegameActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, LightsgameActivity.class);
            startActivity(intent);
        }
    }
}