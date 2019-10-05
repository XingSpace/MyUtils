package com.xing.app.myutils.Utils;

import java.math.BigInteger;

/**
 * 数学公式计算工具类
 */
public class MathUtil {

    /**
     * @return C (n,m)
     * 从n个元素中随机取m个元素进行组合，返回组合数
     */
    public static BigInteger CalcCombination(int m,int n){
        if (m > n || n <= 0 || m <= 0) return null;

        BigInteger N = CalcFactorial(n);
        BigInteger NsubtractM = CalcFactorial(n-m);
        BigInteger M = CalcFactorial(m);

        return N.divide(M.multiply(NsubtractM));
    }

    /**
     * @return P (n,m)
     * 从n个元素中随机抽取m个元素进行随机排列，返回排列数
     */
    public static BigInteger CalcPermutation(int m,int n){
        if (m > n || n <= 0 || m <= 0) return null;

        BigInteger N = CalcFactorial(n);
        BigInteger NsubtractM = CalcFactorial(n-m);

        return N.divide(NsubtractM);
    }

    /**
     * @return 返回 X 的阶乘
     */
    public static BigInteger CalcFactorial(int x){
        return CalcFactorial(String.valueOf(x));
    }

    public static BigInteger CalcFactorial(String s){
        return CalcFactorial(new BigInteger(s));
    }

    public static BigInteger CalcFactorial(BigInteger b){
        BigInteger result = new BigInteger("1");
        for ( BigInteger i = new BigInteger("1");
              b.max(i).equals(b);
              i = i.add(new BigInteger("1"))){

            result = i.multiply(result);
        }
        return result;
    }

}
