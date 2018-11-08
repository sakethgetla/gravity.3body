package com.a.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private RectPlayer pRect;
    private Point playerPoint;
    private final int speed = 5;
    private Button btn;

    public GamePanel(Context context) {

        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        playerPoint = new Point(this.getWidth() / 2, this.getHeight());

        pRect = new RectPlayer(playerPoint.x, playerPoint.y, Color.rgb(255, 0, 0));

        btn = new Button(context);

        btn.setLeft(50);
        btn.setRight(50);
        btn.setWidth(100);
        btn.setHeight(100);

        //btn.setOnClickListener();

       /* Button leftBtn = (Button) findViewById(R.id.button);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerPoint.x -= speed;
            }
        });*/


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
                break;


        }

        //return super.onTouchEvent(event);
        return true;
    }

    public void update() {
        pRect.update(playerPoint);


    }

    //public Picture(R.drawable.spacecraft);


    /**/
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spacecraft);

        bmp = Bitmap.createScaledBitmap(
                bmp,140,190, false);
          //pic = R.drawable.spacecraft;


       // BitmapDrawable drawable = new BitmapDrawable(getResources(), bmp);
       // drawable.setAlpha(100);
       // drawable.
       //  bmp.setHasAlpha(true);
       // bmp.setWidth(50);
       /* Paint paint = new Paint();
        paint.setColor(Color.rgb(255,0,0));
        */

         // canvas.drawARGB(10, 110, 130, 100);
        //canvas.draw
        //drawable.draw(canvas);

       // bmp = (Bitmap) drawable;

          canvas.drawBitmap(bmp , 50,50 - 24, null); // 24 is the height of image

        //canvas.drawPicture();
        //btn.draw(canvas);


        pRect.draw(canvas);
    }

}
