package com.cry.coderpractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * DESCRIPTION:
 * Author: Cry
 * DATE: 2017/10/16 下午9:31
 */

public class ZangView1 extends View {
    public Drawable mDrawable;
    private Paint paint;
    private float mTx = 90;
    float mTy = 90;

    public ZangView1(Context context) {
        super(context);
        initViews(context);
    }

    public ZangView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ZangView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }


    private void initViews(Context context) {
        mDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
    }

    int measuredHeight;
    int measuredWidth;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mDrawable.setBounds(
                0,
                0,
                300,
                300);
        mDrawable.draw(canvas);
        canvas.translate(mTx, mTy);
        canvas.drawLine(measuredWidth / 2, 0, measuredWidth / 2, measuredHeight, paint);
        drawLines(canvas);
        drawTexts(canvas);
    }

    private void drawTexts(Canvas canvas) {
        canvas.save();
        paint.setColor(Color.GREEN);
        canvas.translate(0, measuredHeight / 2 + 20);
        for (int i = 0; i < 20; i++) {
            if (i == 10) {

            } else {

                canvas.drawText("miter", measuredWidth / 20 * i, 20, paint);

            }
        }
        canvas.restore();
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i < 20; i++) {
            if (i == 10) {
                continue;
            }
            paint.setColor(Color.BLUE);
            canvas.drawLine(measuredWidth / 20 * i, 0, measuredWidth / 20 * i, measuredHeight / 2, paint);
        }
    }
}
