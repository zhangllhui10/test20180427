/*
 * NumberUtils.java
 * 创建者：ZhouBC
 * 创建日期：2006-10-25
 *
 * 版权所有(C) 2006-2009。北京英泰伟业通信技术开发有限公司。
 * 保留所有权利。
 */
package com.hui10.app.common.lottery;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

/**
 * @author ZhouBC
 */
public class NumberUtils {






    /**
     * 检查指定的号码数组中的无效号码，没有被候选号码数组包含的号码。
     * 
     * @param numbers 指定的号码数组
     * @param candidateNumbers 候选号码数组
     * @return 无效号码数组
     */
    public static int[] checkInvalidNumbers(int[] numbers, int[] candidateNumbers) {
        // 无效号码
        int[] invalidNumbers = new int[0];
        for (int i = 0; i < numbers.length; i++) {
            if (!isValidNumber(numbers[i], candidateNumbers)) {
                invalidNumbers = NumberUtils.addNumber(invalidNumbers, numbers[i]);
            }
        }

        return invalidNumbers;
    }
    
    /**
     * 检查指定的号码数组中的有效号码数量。
     * 
     * @param numbers 指定的号码数组
     * @param candidateNumbers 候选号码数组
     * @return 有效号码数量
     */
    public static int checkValidNumberCounts(int[] numbers, String[] candidateNumbers) {
        int validCount = 0;
        
        int[] candidateNumbersInt = new int[candidateNumbers.length];  
        for(int i=0; i < candidateNumbers.length;i++){
            candidateNumbersInt[i] = Integer.parseInt(candidateNumbers[i]);
        }
          
        for (int i = 0; i < numbers.length; i++) {
            if (isValidNumber(numbers[i], candidateNumbersInt)) {
                validCount++;
            }
        }

        return validCount;
    }
    
    /**
     * 检查指定的号码数组中的有效号码数量。
     * 
     * @param numbers 指定的号码数组
     * @param candidateNumber 候选号码数组
     * @return 有效号码数量
     */
    public static int checkValidNumberCounts(int[] numbers, String candidateNumber) {
        int validCount = 0;
        
        int candidateNumberInt = Integer.parseInt(candidateNumber);
        
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == candidateNumberInt) {
                validCount++;
            }
        }

        return validCount;
    }

    /**
     * 检查一个指定的号码是否是有效号码，被候选号码所包含。
     * 
     * @param number 指定的号码
     * @param candidateNumbers 候选号码数组
     * @return 有效号码 - true，无效号码 - false
     */
    public static boolean isValidNumber(int number, int[] candidateNumbers) {
        return isInArray(number, candidateNumbers);
    }

    /**
     * 检查指定的号码数组中重复的号码
     * 
     * @param numbers 指定的号码数组
     * @return 重复出现的号码
     */
    public static int[] checkRepeatNumbers(int[] numbers) {
        if (numbers == null || numbers.length <= 0) {
            return new int[0];
        }

        // 已经出现过的号码
        int[] buf = new int[0];
        // 重复出现的号码
        int[] repeatNumbers = new int[0];

        for (int i = 0; i < numbers.length; i++) {
            if (isInArray(numbers[i], buf)) {
                // 如果发现重复，放入 repeatNumbers 中
                if (!isInArray(numbers[i], repeatNumbers)) {
                    repeatNumbers = addNumber(repeatNumbers, numbers[i]);
                }
            } else {
                // 未重复，放入 buf 中
                buf = addNumber(buf, numbers[i]);
            }

        }

        return repeatNumbers;
    }

    /**
     * 对号码进行排序
     * 
     * @return
     */
    public static void order(int[] numbers) {
        if (numbers == null || numbers.length <= 0) {
            return;
        } else {

            // 冒泡
            for (int i = 0; i < numbers.length; i++) {
                for (int j = i + 1; j < numbers.length; j++) {

                    if (numbers[j] < numbers[i]) {
                        // 上升
                        int temp = numbers[i];
                        numbers[i] = numbers[j];
                        numbers[j] = temp;
                    }

                }
            }
        }
    }

    /**
     * 判断一个号码是否在一个号码数组中
     * 
     * @param number 一个号码
     * @param numbers 号码数组
     * @return
     */
    public static boolean isInArray(int number, int[] numbers) {
        if (numbers == null || numbers.length <= 0) {
            return false;
        } else {
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == number) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 向一个号码数组中增加一个号码
     * 
     * @param numbers 号码数组
     * @param number 一个号码
     * @return
     */
    public static int[] addNumber(int[] numbers, int number) {
        if (numbers == null || numbers.length <= 0) {
            return new int[] { number };
        } else {
            int[] newNumbers = new int[numbers.length + 1];
            System.arraycopy(numbers, 0, newNumbers, 0, numbers.length);

            newNumbers[newNumbers.length - 1] = number;

            return newNumbers;
        }
    }

    /**
     * 将号码转换为字符串
     * 
     * @param number 号码
     * @param length 填充后的长度
     * @param fillWith 不足的部分用什么字符串填充
     * @param fillDirect 填充方向，在前面填充还是在后面填充，大于 0 表示在后面填充，小于等于 0 表示在前面填充。
     * @return
     */
    public static String numberToString(int number, int length, String fillWith, int fillDirect) {
        if (fillWith == null) {
            fillWith = " ";
        }
        
        int numberLength = String.valueOf(number).length();
        if (length < numberLength) {
            length = numberLength;
        }
        StringBuffer sb = new StringBuffer();

        sb.append(number);
        if (fillDirect <= 0) {
            // 前面填充
            while (sb.length() < length) {
                sb.insert(0, fillWith);
            }
            return sb.substring(sb.length() - length, sb.length());
        } else {
            // 后面填充
            while (sb.length() < length) {
                sb.append(fillWith);
            }
            return sb.substring(0, length);
        }
    }

    public static String unionNumbers(int[] numbers, String separator) {
        if (numbers == null || numbers.length <= 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < numbers.length; i++) {

                String numberStr = numberToString(numbers[i], 2, "0", -1);
                if (i > 0) {
                    sb.append(separator);
                }
                sb.append(numberStr);
            }
            return sb.toString();
        }
    }
    
    public static String union3DNumbers(int[] numbers, String separator) {
        if (numbers == null || numbers.length <= 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < numbers.length; i++) {

                String numberStr = numberToString(numbers[i], 1, null, -1);
                if (i > 0) {
                    sb.append(separator);
                }
                sb.append(numberStr);
            }
            return sb.toString();
        }
    }
    
    public static String unionTwoDiffNumbers(int[] numbers, String separator) {
        if (numbers == null || numbers.length <= 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < numbers.length; i++) {
                String numberStr = String.valueOf(numbers[i]);
                if (i > 0) {
                    sb.append(separator);
                }
                sb.append(StringUtils.left(numberStr, 1) + "," + StringUtils.mid(numberStr, 1, 1));
            }
            return sb.toString();
        }
    }

    /**
     * 组合计算.
     * 从n个元素中取k个元素计算组合数.
     * 
     * @param n 元素总数
     * @param k 获取元素数
     * @return 组合总数
     */
    public static BigInteger combine(int n, int k) {
        BigInteger fenzi = new BigInteger("1");
        BigInteger fenmu = new BigInteger("1");
        
        for (int i = n - k + 1; i <= n; i++) {
            String s = Integer.toString(i);
            BigInteger stobig = new BigInteger(s);
            fenzi = fenzi.multiply(stobig);
        }
        
        for (int j = 1; j <= k; j++) {
            String ss = Integer.toString(j);
            BigInteger stobig2 = new BigInteger(ss);
            fenmu = fenmu.multiply(stobig2);
        }
        BigInteger result = fenzi.divide(fenmu);
        return result;
    }
    
    /**
     * 解析中彩格式的七乐彩、双色球注码中的号码部分.
     * 例如：01020304050607代表球1,2,3,4,5,6,7
     * 
     * @param ballDetailStr 中彩格式的七乐彩、双色球号码部分
     * @return 号码数组
     */
    public static int[] parseBall(String ballDetailStr) {
        char[] ballDetailArray = ballDetailStr.toCharArray();

        String currentNumber = "";

        int[] numbers = new int[ballDetailArray.length / 2];

        for (int i = 0, j = 0; i < ballDetailArray.length; i++) {
            currentNumber += ballDetailArray[i];
            if (i % 2 != 0) {
                numbers[j] = Integer.parseInt(currentNumber);
                j++;

                currentNumber = "";
            }
        }
        
        return numbers;
    }
    
    /**
     * 解析中彩格式的3D注码中的号码部分.
     * 例如：
     *      11表示号码1；
     *      3123表示号码1,2,3
     *      00123456789表示号码0,1,2,3,4,5,6,7,8,9
     * @param ballDetailStr 中彩格式的3D号码部分
     * @return 号码数组
     */
    public static int[] parse3DBall(String ballDetailStr){
        String ballCountStr = StringUtils.left(ballDetailStr, 1);
        
        int ballCount = Integer.parseInt(ballCountStr) == 0 ? 10 : Integer.parseInt(ballCountStr);
        
        String ballStr = StringUtils.mid(ballDetailStr, 1, ballCount);
        
        char[] ballStrArray = ballStr.toCharArray();
        
        int[] numbers = new int[ballCount];
        
        for (int i = 0; i < ballStrArray.length; i++) {
            numbers[i] = Integer.parseInt(String.valueOf(ballStrArray[i]));
        }
        
        return numbers;
    }
    
    /**
     * 返回开奖号码剃重后的数值个数
     * 
     * @param open1
     * @param open2
     * @param open3
     * @return 剃重后的数值个数
     */
    public static int getUniqueNumber(int open1, int open2, int open3) {
        int unique = 0;
        if ((open1 == open2) && (open2 == open3)) { // 豹子
            unique = 1;
        } else if ((open1 == open2) || (open2 == open3) || (open1 == open3)) {
            unique = 2;
        } else {
            unique = 3;
        }

        return unique;
    }
}
