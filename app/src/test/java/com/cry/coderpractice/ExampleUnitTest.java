package com.cry.coderpractice;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int numberSizeCount = getNumberSizeCount(0, -100);

//        System.out.println("Result=" + getNumberLast9count(-100, numberSizeCount));
//        System.out.println("Result=" + getNumberLast9count(100, numberSizeCount));
//        System.out.println("Result=" + getNumberLast9count(99, getNumberSizeCount(0, 99)));
//        System.out.println("Result=" + getNumberLast9count(109, getNumberSizeCount(0, 109)));
//        System.out.println("Result=" + getNumberLast9count(199, getNumberSizeCount(0, 199)));
//        System.out.println("Result=" + getNumberLast9count(7999, getNumberSizeCount(0, 1999)));
//        System.out.println("Result=" + getNumberLast0count(89999, getNumberSizeCount(0, 19999)));
//        System.out.println("Result=" + getNumberLast0count(8797999, getNumberSizeCount(0, 19999)));
//        System.out.println("Result=" + getNumberLast0count(9, getNumberSizeCount(0, 19999)));
        System.out.println("Result=" + getNumberLast0count(9000, 0));
    }


    private int getNumberSizeCount(int count, int oldNumber) {
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

    private int getNumberLast0count(int oldNumber, int count) {
        if (oldNumber % 10 == 0) {
            count++;
            return getNumberLast0count(oldNumber / 10, count);
        } else {
            return count;
        }
    }
}