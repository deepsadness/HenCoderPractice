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
//        System.out.println("Result= 99=" + getNumberLast9count(99, getNumberSizeCount(0, 99)));
//        System.out.println("Result= 9=" + getNumberLast9count(9, getNumberSizeCount(0, 9)));
//        System.out.println("Result 109=" + getNumberLast9count(109, getNumberSizeCount(0, 109)));
//        System.out.println("Result 199=" + getNumberLast9count(199, getNumberSizeCount(0, 199)));
//        System.out.println("Result 999=" + getNumberLast9count(999, getNumberSizeCount(0, 999)));
//        System.out.println("Result=" + getNumberLast9count(7999, getNumberSizeCount(0, 1999)));
//        System.out.println("Result=" + getNumberLast0count(89999, 0));
//        System.out.println("Result=" + getNumberLast0count(8797999, 0));
//        System.out.println("Result=" + getNumberLast0count(9, 0));
//        System.out.println("Result=" + getNumberLast0count(19, 0));
//        System.out.println("Result=" + getNumberLast0count(20, 0));
//        System.out.println("Result=" + getNumberLast0count(2100, 0));
        System.out.println("Result=" + getNumberLast0count(20100, 0));
//        System.out.println("Result=" + getNumberLast0count(9000, 0));
//        System.out.println(40 % 100);
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

    /**
     * 19= 10+9
     * 199=100+90+9
     * 20999=2*10000+900+90+9
     * <p>
     * 999=9*100+90+9
     *
     * @param oldNumber
     * @param countSize
     * @return
     */
    private int getNumberLast9count(int oldNumber, int countSize) {
        int count = 0;
        for (int j = 1; j < countSize; j++) {
            int subSum = 0;
            for (int i = 1; i < j; i++) {
                int multi = 9;
                subSum += Math.pow(10, i) * multi;
            }
            double pow = Math.pow(10, j);
            double v = oldNumber % pow;
            if (v - subSum == 9) {
                count++;
            } else {
                break;
            }
        }
        count++;
        String oldNumberString = String.valueOf(oldNumber);
        if (count == oldNumberString.length()) {
            if (oldNumberString.indexOf("9") == 0) {
                count++;
            }
        }
        return count;
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