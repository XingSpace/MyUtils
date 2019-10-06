package com.xing.app.myutils;

import org.junit.Test;

import java.math.BigInteger;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        BigInteger b = CalcPermutationRepetition(3,5);
//        System.out.println("5取3 计算结果:"+b.toString());
//        System.out.println("奇偶 计算结果:"+isOddNum(-89L));

        //暂时做记录，该方法可以输出任意一个正整数的负数形式
        String s = Integer.toBinaryString(-29);//输出的是补码

        String fan = Integer.toBinaryString(~29 + 1);

        System.out.println("-29的原码："+s);
        System.out.println("~29的原码："+fan);
        System.out.println("29的负数："+Integer.parseUnsignedInt(fan,2));



//        System.out.println("奇偶 计算结果:"+Integer.parseUnsignedInt("11111111",2));
    }

    /**
     * @return PR(n,m)
     * 返回 n个不同元素的一个m-可重排列
     */
    public static BigInteger CalcPermutationRepetition(int m,int n){
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
    /**
     * @return 判断一个数是否为奇数,反之为偶数
     */
    public static boolean isOddNum(int i){
        return (i & 1) == 1;
    }

    public static boolean isOddNum(long l){
        return (l & 1L) == 1L;
    }
}