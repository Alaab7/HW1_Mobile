package com.example.threelinegame;

import java.util.Random;

public class GameManager {
    private int numOfLives;
    private  int numRows;
    private int numCol;
    private int[] arrOfLives;
    private int[][] arrOfRocks;
    private int[] arrOfHorse;
    private int horsePosition;
    private boolean isCrash = false;
    private boolean isFinish = false;
    

    public GameManager (int numOfLives , int numRows , int numCol){
        this.numOfLives = numOfLives;
        this.numCol=numCol;
        this.numRows=numRows;
    }
    
    public void click(String direction){
        int nextCol;

        if(direction.equals("left")){
            nextCol = horsePosition - 1;
            if(nextCol >= 0){
                arrOfHorse[horsePosition] = 0;
                arrOfHorse[nextCol] = 1;
                horsePosition = nextCol;
            }
        }

        if(direction.equals("right")){
            nextCol = horsePosition + 1;
            if(nextCol  < numCol){
                arrOfHorse[horsePosition] = 0;
                arrOfHorse[nextCol] = 1;
                horsePosition = nextCol;
            }
        }
        
    }

    public void initItems() {
        initLives();
        initRocks();
        initHorses();
    }

    private void initHorses() {
        arrOfHorse = new int[numCol];
        for (int i = 0; i < numCol; i++) {
            if( i == 1){
                arrOfHorse[1] = 1;
                horsePosition = i;
            }else{
                arrOfHorse[i] = 0;
            }
        }
    }

    private void initRocks() {
        arrOfRocks = new int[numRows][numCol];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                arrOfRocks[i][j] = 0;
            }
        }
        
    }

    private void initLives() {
        arrOfLives = new int[numOfLives];
        for (int i = 0; i < arrOfLives.length; i++) {
            arrOfLives[i] = 1;
        }
    }

    public void updateTable() {
        updateRocks();
        addNewRock();
    }

    private void addNewRock() {
        int randomCol = new Random().nextInt(numCol);
        for (int i = 0; i < numCol; i++) {
            if(randomCol == i) {
                arrOfRocks[0][i] = 1;
            }else{
                arrOfRocks[0][i] = 0;
            }
        }
    }

    private void updateRocks() {
        for (int i = numRows - 1; i >= 0 ; i--) {
            for (int j = 0; j < numCol; j++) {
                if( i == numRows - 1 && arrOfRocks[i][j] == 1){
                     arrOfRocks[i][j] = 0;
                   if(j == horsePosition){
                        isCrash = true;
                        numOfLives = numOfLives -1;
                        arrOfLives[numOfLives] = 0;
                        if(numOfLives == 0){
                            isFinish = true;
                        }
                    }


                } else if (i != numRows - 1){
                    arrOfRocks[i+1][j]=arrOfRocks[i][j];
                }
            }
        }
    }
    public void setIsCrash(boolean b){
        isCrash = b;
    }


    public boolean isGameFinish() {

        return isFinish;
    }

    public boolean isTouched() {
        return isCrash;
    }

    public int[] getLives() {
        return arrOfLives;
    }

    public int[][] getRocks() {
        return arrOfRocks;
    }

    public int[] getHorse() {
        return arrOfHorse;
    }
}
