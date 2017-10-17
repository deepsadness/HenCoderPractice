package com.cry.coderpractice;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * DESCRIPTION:
 * Author: Cry
 * DATE: 2017/10/16 下午9:31
 */

public class ZangView2 extends View {

    private Paint paint;
    private int count = 0;
    private int newNumbers;
    private String oldNumbers;
    public float mTextSize;
    private int distanceChange;
    private int animateDistance;
    private boolean upOrDown;
    private int countSize;
    private boolean isNewNoChangePart = true;
    private String noChangeNumberPartResult;
    private boolean isNewDownPart = true;
    private String changeNumberDownPartResult;
    private boolean isNewUpPart = true;
    private String changeNumberUpPart;
    private boolean isChangeNumberPartUp = true;
    private boolean isChangeNumberPartDown = true;
    private float getChangeNumberPartTranlateXUp;
    private float getChangeNumberPartTranlateXDown;

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        mTextSize = 100f;
        paint.setTextSize(mTextSize);
        changeFlagsSetFalse();
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
        canvas.drawText(getNoChangeNumberPart(), getStartLocationX(), 200, paint);
        canvas.restore();

        canvas.save();
        //需要动画的部分
        float alpha = transLateY * 1f / totalDistance;
        paint.setAlpha((int) (255 * alpha));
        canvas.translate(getChangeNumberPartTranlateX(false), totalDistance - transLateY);
        canvas.drawText(getChangeNumberDownPart() + "", getStartLocationX(), 200, paint);
        canvas.restore();

        canvas.save();
        paint.setAlpha((int) (255 * (1 - alpha)));
        canvas.translate(getChangeNumberPartTranlateX(true), 0 - transLateY);
        canvas.drawText(getChangeNumberUpPart() + "", getStartLocationX(), 200, paint);
        canvas.restore();

    }

    private float getChangeNumberPartTranlateX(boolean isUpOrDown) {
//        //++时，如果时进位，需要进行变化。
//        if (!isChangeNumberPartUp) {
//            return getChangeNumberPartTranlateXUp;
//        }
        if (!isChangeNumberPartDown && !isUpOrDown && !upOrDown) {
            return getChangeNumberPartTranlateXDown;
        }
        float result = -Float.MAX_VALUE;
        int length;
        if (upOrDown) {
            String s = oldNumbers;
            length = s.length();
            if (length < animateDistance && isUpOrDown) {   //进位
                System.out.println("TAG 进位 count=" + count);
                if (length > 1) {
                    s = s.substring(0, 1);
                    result = paint.measureText(s);
                } else if (length == 0) {
                    result = paint.measureText(getNoChangeNumberPart());
                } else {
                    result = paint.measureText(s);
                }
            }
        } else {
            length = oldNumbers.length();
            int newlength = String.valueOf(count).length();
            if (newlength < length) {
                System.out.println("TAG 退位 count=" + count);
                if (isUpOrDown) {
                    result = -paint.measureText("");
                } else {
                    result = -paint.measureText("9");
                }
            }
        }
        if (result == -Float.MAX_VALUE) {
            result = paint.measureText(getNoChangeNumberPart());
        }
        if (isUpOrDown) {
            this.getChangeNumberPartTranlateXUp = result;
            isChangeNumberPartUp = false;
        } else {
            this.getChangeNumberPartTranlateXDown = result;
            isChangeNumberPartDown = false;
        }
        return result;
    }

    private int getStartLocationX() {
        return (int) (measuredWidth / 2 - 100 - paint.measureText(String.valueOf(count)));
    }

    //从上方消失，或者从上方进入的数字。对于+，则表示上一个数字。对于-，则表示当前的数字
    private String getChangeNumberUpPart() {
        if (isNewUpPart) {
            String result;
            if (upOrDown) {//++
                int temp = count - 1;
                double v = temp % (Math.pow(10, animateDistance));
//            v++;
                result = ((int) v) + "";
            } else { //--
                int temp = count;
                double v = temp % (Math.pow(10, animateDistance));
//            v--;
                result = ((int) v) + "";
            }
            isNewUpPart = false;
            this.changeNumberUpPart = result;
            return result;
        } else {
            return changeNumberUpPart;
        }
    }

    /**
     * 从下方进入，或者从下方消失的数字。对于+，则表示当前的数字。对于-，则表示上一个的数字
     *
     * @return
     */
    private String getChangeNumberDownPart() {
        String result;
        if (isNewDownPart) {
            String countString;
            if (upOrDown) {//++
                countString = String.valueOf(count);
            } else {
                countString = oldNumbers;
            }
            //如果需要动画的位置相当的话，
            int length = countString.length();
            if (length <= animateDistance) {
                result = countString;
            } else {
                int i = length - animateDistance;
                result = countString.substring(i, length);
            }
            isNewDownPart = false;
            this.changeNumberDownPartResult = result;
            return result;
        } else {
            return changeNumberDownPartResult;
        }
    }

    //获取不变化的部分
    //进位时，有变化。不进位时不变化
    @NonNull
    private String getNoChangeNumberPart() {
        String result = "";
        if (isNewNoChangePart) {
            String countString = String.valueOf(count);
            //如果需要动画的位置相当的话，
            int length = countString.length();
            if (length == animateDistance) {
                result = "";
            } else if (length > animateDistance) {
                int i = length - animateDistance;
                result = countString.substring(0, i);
            } else {
                if (upOrDown) {
                    result = countString;
                } else {
                    result = "";
                }
            }
            this.noChangeNumberPartResult = result;
            isNewNoChangePart = false;
            return result;
        } else {
            return noChangeNumberPartResult;
        }
    }

    @NonNull
    private String getNoChangeNumberPart(int innerAnimate) {
        String countString = String.valueOf(count);
        //如果需要动画的位置相当的话，
        int length = countString.length();
        if (length == innerAnimate) {
            return "";
        } else if (length > innerAnimate) {
            int i = length - innerAnimate;
            return countString.substring(0, i);
        } else {
            return countString;
        }
    }

    int transLateY;

    int totalDistance = 100;

    public void addNumbers() {
        upOrDown = true;
        count++;
        this.newNumbers = count;
        oldNumbers = String.valueOf(count - 1);
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
        upOrDown = false;
        count--;
        this.newNumbers = count;
        oldNumbers = String.valueOf(count + 1);
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
        changeFlagsSetFalse();
        //需要改变的数字
        int oldNumber = count;
        count++;
        int newNumber = count;
        //找到old和new之间的区别
//        因为每次都是+1,故依次判断最后一位是否是9
        //先得到该数是几位数
        countSize = getNumberSizeCount(0, oldNumber);
        //判断从低位开始需要改变的范围
        int count9size = getNumberLast9count(oldNumber, countSize);
        if (count9size >= countSize) {
            //全部改变，则不管
            distanceChange = 0;
            animateDistance = count9size;
        } else {
            //部分改变
            distanceChange = countSize - count9size;
            animateDistance = count9size;
        }
        count--;
        addNumbers();
    }

    public void subAndgetChangeNumberPart() {
        if (count <= 0) {
            return;
        }
        changeFlagsSetFalse();

        //需要改变的数字
        int oldNumber = count;
        count--;
        int newNumber = count;
        //找到old和new之间的区别
//        因为每次都是+1,故依次判断最后一位是否是9
        //先得到该数是几位数
        int countSize = getNumberSizeCount(0, oldNumber);
        //判断从低位开始需要改变的范围
        animateDistance = getNumberLast0count(oldNumber, 0);
        distanceChange = countSize - animateDistance;
        count++;
        subNumbers();
    }

    private void changeFlagsSetFalse() {
        isNewNoChangePart = true;
        isNewDownPart = true;
        isNewUpPart = true;
        isChangeNumberPartUp = true;
        isChangeNumberPartDown = true;
        getChangeNumberPartTranlateXUp = -Float.MAX_VALUE;
        getChangeNumberPartTranlateXDown = -Float.MAX_VALUE;
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
        //补充。如果首位是9，还需要进位，估+1
        String oldNumberString = String.valueOf(oldNumber);
        if (count == oldNumberString.length()) {
            if (oldNumberString.indexOf("9") == 0) {
                count++;
            }
        }
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
