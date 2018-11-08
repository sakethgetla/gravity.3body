package com.example.a.math_invaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import static java.lang.System.currentTimeMillis;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    public boolean getRunning() {

        return running;
    }

    public void setRunning(boolean r) {
        this.running = r;
    }


    @Override
    public void run() {

//        int count =0;
//        long millis= System.currentTimeMillis();
//        while(running) {
//
//
//            count++;
//            if( System.currentTimeMillis() - millis > 1000 ){
//                System.out.print(count);
//                millis += 1000;
//                count =0;
//            }
//        }


        ///////////////////////////////////
        int Fps = 40, FPScount = 0;
        long milliSecTimer = System.currentTimeMillis(), compRefresh = 0;

        double before = System.nanoTime();
        double timeDiff = 0, now = 0, amountOfNanos = 0;

        while (running) {
            amountOfNanos = 1000000000 / Fps;
            now = System.nanoTime();
            timeDiff += (now - before) / amountOfNanos;
            before = now;
            while (timeDiff >= 1) {

                timeDiff--;
                FPScount++;
                this.gamePanel.update();
                this.gamePanel.draw(canvas);
            }
            compRefresh++;



            while (System.currentTimeMillis() - milliSecTimer > 1000) {
                milliSecTimer += 1000;
                System.out.println("comp> " + compRefresh + "   fps> " + FPScount );
                compRefresh = 0;
                FPScount = 0;
            }

        }

    }


}