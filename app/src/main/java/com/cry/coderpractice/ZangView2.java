package com.cry.coderpractice;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
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

public class ZangView2 extends View {
    public Drawable mDrawable;
    private Paint paint;
    private int count = 0;
    private int newNumbers;
    private int oldNumbers;
    private int changeNumberPart;
    public float mTextSize;
    private int distanceChange;
    private int animateDistance;
    private boolean upOrDown;

    public ZangView2(Context context) {
        super(context);
        initViews(context);
    }

    public ZangView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ZangView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }


    private void initViews(Context context) {
        mDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        mTextSize = 100f;
        paint.setTextSize(mTextSize);
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
        //不需要动画的部分
        canvas.save();
        paint.setAlpha(255);
        canvas.drawText(getNoChangeNumberPart(), measuredWidth / 2 - 100, 200, paint);
        canvas.restore();

        canvas.save();
        //需要动画的部分
        float alpha = transLateY * 1f / totalDistance;
        paint.setAlpha((int) (255 * alpha));
        canvas.translate(getChangeNumberPartTranlateX(), totalDistance - transLateY);
        canvas.drawText(getChangeNumberNewPart() + "", measuredWidth / 2 - 100, 200, paint);
        canvas.restore();

        canvas.save();
        paint.setAlpha((int) (255 * (1 - alpha)));
        canvas.translate(getChangeNumberPartTranlateX(), 0 - transLateY);
        canvas.drawText(getChangeNumberOldPart() + "", measuredWidth / 2 - 100, 200, paint);
        canvas.restore();

    }

    private float getChangeNumberPartTranlateX() {
        //unchangeNumber
        return paint.measureText(getNoChangeNumberPart());
    }

    private String getChangeNumberOldPart() {

        if (upOrDown) {//++
            int temp = count - 1;
            double v = temp % (Math.pow(10, animateDistance));
//            v++;
            return ((int) v) + "";
        } else { //--
            int temp = count + 1;
            double v = temp % (Math.pow(10, animateDistance));
//            v--;
            return ((int) v) + "";
        }
    }

    private String getChangeNumberNewPart() {
        int temp = count - 1;
        if (upOrDown) {//++
            double v = temp % (Math.pow(10, animateDistance));
            v++;
            String s = ((int) v) + "";
            if (s.length() > 1) {
                s = s.substring(1);
            }
            return s;
        } else { //--
            double v = temp % (Math.pow(10, animateDistance));
            v--;
            return ((int) v) + "";
        }
    }

    private String getNoChangeNumberPart() {
        double v = count / Math.pow(10, animateDistance);
        return ((int) v) + "";
    }

    int transLateY;

    int totalDistance = 100;

    public void addNumbers() {
        upOrDown = true;
        count++;
        this.newNumbers = count;
        oldNumbers = (count - 1);
        totalDistance = (int) paint.getFontSpacing();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, totalDistance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                transLateY = animatedValue;
                invalidate();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    public void subNumbers() {
        count--;
        this.newNumbers = count;
        oldNumbers = (count + 1);
        totalDistance = (int) paint.getFontSpacing();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(totalDistance, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                transLateY = animatedValue;
                invalidate();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    public void AddAndgetChangeNumberPart() {
        //需要改变的数字
        int oldNumber = count;
        count++;
        int newNumber = count;
        //找到old和new之间的区别
//        因为每次都是+1,故依次判断最后一位是否是9
        //先得到该数是几位数
        int countSize = getNumberSizeCount(0, oldNumber);
        //判断从低位开始需要改变的范围
        int count9size = getNumberLast9count(oldNumber, countSize);
        if (count9size >= countSize) {
            //全部改变，则不管
            distanceChange = 0;
            animateDistance = countSize;
        } else {
            //部分改变
            distanceChange = countSize - count9size;
            animateDistance = count9size;
        }
        count--;
        addNumbers();
    }

    //往上+,可能需要改变的位数
    private int getNumberLast9count(int oldNumber, int countSize) {
        int count = 0;
        for (int j = 1; j < countSize; j++) {
            int subSum = 0;
            for (int i = 1; i < j; i++) {
                int multi = 9;
                subSum += Math.pow(10, i) * multi;
            }
            double pow = Math.pow(10, j);
            if (oldNumber % pow - subSum == 9) {
                count++;
            } else {
                break;
            }
        }
        count++;
        return count;
    }

    public int getNumberSizeCount(int count, int oldNumber) {
        if (oldNumber / 10 != 0) {
            count++;
            return getNumberSizeCount(count, oldNumber / 10);
        } else if (oldNumber / 10 == 0) {
            count++;
            return count;
        } else {
            return count;
        }
    }

    private int getNumberLast0count(int oldNumber, int count) {
        if (oldNumber % 10 == 0) {
            count++;
            return getNumberLast0count(oldNumber / 10, count);
        } else {
            count++;
            return count;
        }
    }
}
