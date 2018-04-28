/*
 * DoubleColorBall.java
 * 创建者：杨帆
 * 创建日期：2014年5月20日
 *
 * 版权所有(C) 2011-2014。英泰伟业科技(北京)有限公司。
 * 保留所有权利。
 */
package com.hui10.app.common.lottery;

import java.io.Serializable;

/**
 * 双色球游戏.
 * 
 * @author 杨帆
 */
public class DoubleColorBall implements Serializable {

    private static final long serialVersionUID = -5592630375484143568L;

    /**
     * 游戏编码
     */
    public static final String GAME_ID = "10001";

    /**
     * 候选红球
     */
    public static final int[] CANDIDATE_NUMBERS_RED = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33 };

    /**
     * 候选蓝球
     */
    public static final int[] CANDIDATE_NUMBERS_BLUE = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16 };

}
