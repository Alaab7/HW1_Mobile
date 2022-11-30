package com.example.threelinegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton main_BTN_right;
    private ExtendedFloatingActionButton main_BTN_left;
    private AppCompatImageView[][] rocks;
    private ShapeableImageView[] lives;
    private AppCompatImageView[] horses;
    GameManager gameManager;
    private int numOfLives = 3;
    private int numRows = 5;
    private int numCol = 3;
    private Timer timer;
    private static final int DELAY = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        leftAndRight();
        gameManager = new GameManager(numOfLives, numRows, numCol);
        gameManager.initItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateScreen());
            }
        }, DELAY, DELAY);
    }

    private void updateScreen()
    {
        gameManager.updateTable();
        boolean isGameFinish = gameManager.isGameFinish();

        if (isGameFinish)
        {
            showLives();
            showRocks();
            showToast("Game Over");
            finish();
            makeVibrate();
        } else
        {
            boolean isTouched = gameManager.isTouched();
            if (isTouched)
            {
                showLives();
                showToast("Lost life");
                gameManager.setIsCrash(false);
                makeVibrate();
            }
        }
        showRocks();
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    private void showRocks() {
        int[][] managerRocks = gameManager.getRocks();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                if (managerRocks[i][j] == 1) {
                    rocks[i][j].setVisibility(View.VISIBLE);
                } else {
                    rocks[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void showLives() {
        int[] managerLives = gameManager.getLives();
        for (int i = 0; i < numCol; i++) {
            if (managerLives[i] == 1) {
                lives[i].setVisibility(View.VISIBLE);
            } else {
                lives[i].setVisibility(View.INVISIBLE);

            }
        }
    }
    private void makeVibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }


    public void leftAndRight() {

        main_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameManager.click("right");
                showHorse();
            }
        });


        main_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameManager.click("left");
                showHorse();
            }
        });

    }

    private void showHorse() {
        int [] managerHorse = gameManager.getHorse();
        for (int i = 0; i < numCol; i++) {
            if(managerHorse[i] == 1){
                horses[i].setVisibility(View.VISIBLE);
            }else{
                horses[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void findViews() {
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        findRocks();
        findHearts();
        findHorses();


    }

    public void findHearts() {
        lives = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };


    }

    public void findHorses() {
        horses = new AppCompatImageView[]{
                findViewById(R.id.main_IC_horse_0),
                findViewById(R.id.main_IC_horse_1),
                findViewById(R.id.main_IC_horse_2)

        };

    }


    public void findRocks() {

        rocks = new AppCompatImageView[][]{
                {findViewById(R.id.main_IC_rock_00),
                        findViewById(R.id.main_IC_rock_01),
                        findViewById(R.id.main_IC_rock_02)},

                {findViewById(R.id.main_IC_rock_10),
                        findViewById(R.id.main_IC_rock_11),
                        findViewById(R.id.main_IC_rock_12)},


                {findViewById(R.id.main_IC_rock_20),
                        findViewById(R.id.main_IC_rock_21),
                        findViewById(R.id.main_IC_rock_22)},


                {findViewById(R.id.main_IC_rock_30),
                        findViewById(R.id.main_IC_rock_31),
                        findViewById(R.id.main_IC_rock_32)},


                {findViewById(R.id.main_IC_rock_40),
                        findViewById(R.id.main_IC_rock_41),
                        findViewById(R.id.main_IC_rock_42)}

        };
    }
}

