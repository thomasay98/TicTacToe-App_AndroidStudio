package com.mystartup.dmtictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, No
    }

    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];
    int [][] winnerRowCol  = {{0,1,2}, {3,4,5}, {6,7,8},
                                {0,3,6}, {1,4,7}, {2,5,8},
                                {0,4,8}, {2,4,6}};

    boolean gameOver = false;
    private Button btnReset;
    private GridLayout gridLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int index = 0; index < playerChoices.length; index++){
            playerChoices[index] = Player.No;
        }

        btnReset = findViewById(R.id.btnReset);
        gridLay = findViewById(R.id.gridLay);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameReset();
            }
        });
    }


    public void tappedImage(View imageview){
        ImageView tappedImageView = (ImageView) imageview;
        Animation animat = AnimationUtils.loadAnimation(this,R.anim.pop);

        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoices[tiTag] == Player.No && !gameOver) {
            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.darkmagician);
                //tappedImageView.animate().alpha(1).rotation(1800).setDuration(1000);
                tappedImageView.startAnimation(animat);
                currentPlayer = Player.TWO;
            }
            else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.darkmagiciangirl);
                //tappedImageView.animate().alpha(1).rotation(-1800).setDuration(1000);
                tappedImageView.startAnimation(animat);
                currentPlayer = Player.ONE;
            }

            //Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerCol : winnerRowCol) {
                if (playerChoices[winnerCol[0]] == playerChoices[winnerCol[1]]
                        && playerChoices[winnerCol[1]] == playerChoices[winnerCol[2]]
                        && playerChoices[winnerCol[0]] != Player.No) {

                    gameOver = true;
                    btnReset.setVisibility(View.VISIBLE);
                    String winnerGame = "";
                    if (currentPlayer == Player.ONE) {
                        winnerGame = "Player 2";
                    }
                    else if (currentPlayer == Player.TWO) {
                        winnerGame = "Player 1";
                    }
                    Toast.makeText(this, winnerGame + "Win!", Toast.LENGTH_SHORT).show();
                }
            }

            int counter = 0;
            for (Player playerChoice : playerChoices) {
                if (playerChoice != Player.No) {
                    counter++;
                }
            }
            if (counter == 9 && !gameOver){
                Toast.makeText(this, "Draw Game!", Toast.LENGTH_SHORT).show();
                btnReset.setVisibility(View.VISIBLE);
            }
        }
    }

    //Game Reset Function
    private void gameReset(){
        for(int index = 0; index < gridLay.getChildCount(); index++) {
            ImageView imageView = (ImageView) gridLay.getChildAt(index);
            imageView.setImageDrawable(null);
            playerChoices[index] = Player.No;
        }

        currentPlayer = Player.ONE;
        gameOver = false;
        btnReset.setVisibility(View.INVISIBLE);
    }

}
