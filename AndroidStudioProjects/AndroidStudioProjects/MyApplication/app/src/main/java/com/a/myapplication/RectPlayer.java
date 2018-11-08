package com.a.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    // private Rect rect;
    private int color;


    private int height = 110;
    private int width = 20;
    private int x, y;
    //private point point= new Point(0,0);


    public RectPlayer(int x, int y, int color) {
        this.x = x;
        this.y = y;

        // this.rect = new Rect(x, y, color);
        this.color = color;
        //this.rect.width();


    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        x = point.x;
        y = point.y;


    }


}
