package com.xing.app.myutils.Utils;

import java.math.BigInteger;

/**
 * 数学公式计算工具类
 */
public class MathUtil {

    private MathUtil() {}

    /**
     * @return C (n,m)
     * 从n个元素中随机取m个元素进行组合，返回组合数
     */
    public static BigInteger calcCombination(int m,int n){
        if (m > n || n <= 0 || m <= 0) return null;

        BigInteger N = calcFactorial(n);
        BigInteger NsubtractM = calcFactorial(n-m);
        BigInteger M = calcFactorial(m);

        return N.divide(M.multiply(NsubtractM));
    }

    /**
     * @return P (n,m)
     * 从n个元素中随机抽取m个元素进行随机排列，返回排列数
     */
    public static BigInteger calcPermutation(int m,int n){
        if (m > n || n <= 0 || m <= 0) return null;

        BigInteger N = calcFactorial(n);
        BigInteger NsubtractM = calcFactorial(n-m);

        return N.divide(NsubtractM);
    }

    /**
     * @return PR(n,m)
     * 返回 n个不同元素的一个m-可重排列
     */
    public static BigInteger calcPermutationRepetition(int m,int n){
        if (m > n || n <= 0 || m <= 0) return null;

        BigInteger M = new BigInteger(String.valueOf(m));
        BigInteger N = new BigInteger(String.valueOf(n));
        BigInteger result = new BigInteger("1");
        for ( BigInteger i = new BigInteger("1");
              M.max(i).equals(M);
              i = i.add(new BigInteger("1"))){
            result = result.multiply(N);
        }
        return result;
    }

    /**
     * @return 返回 X 的阶乘
     */
    public static BigInteger calcFactorial(int x){
        return calcFactorial(String.valueOf(x));
    }

    public static BigInteger calcFactorial(String s){
        return calcFactorial(new BigInteger(s));
    }

    public static BigInteger calcFactorial(BigInteger b){
        BigInteger result = new BigInteger("1");
        for ( BigInteger i = new BigInteger("1");
              b.max(i).equals(b);
              i = i.add(new BigInteger("1"))){

            result = i.multiply(result);
        }
        return result;
    }

    /**
     * @return 判断一个数是否为奇数,反之为偶数
     */
    public static boolean isOddNum(int i){
        return (i & 1) == 1;
    }

    public static boolean isOddNum(long l){
        return (l & 1L) == 1L;
    }

    /**
     * @param value 真数
     * @param base 底数
     * @return 对数
     */
    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

    public static double log(int value, int base){
        return log((long)value,(long)base);
    }

}
