package com.vpu.mp.service.foundation.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
	/**随机数上限9999*/
	public final static int MAX_RANDOM_9999 = 9999;
	/**随机数下限1000*/
	public final static int MIN_RANDOM_1000 = 1000;
	
	/**
	 * 	获取[min,max)区间的随机整数数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getIntRandom (int min , int max) {
		return min + ThreadLocalRandom.current().nextInt(max - min);
	}
	
	/**
	 * 	获取[1000,9999)区间的随机整数数
	 * @return
	 */
	public static int getIntRandom () {
		return MIN_RANDOM_1000 + ThreadLocalRandom.current().nextInt(MAX_RANDOM_9999 - MIN_RANDOM_1000);
	}
}
