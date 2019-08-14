package com.vpu.mp.service.foundation.util;

import java.math.BigDecimal;

/**
 * 	扩展BigDecimal
 * @author 王帅
 *
 */
public class BigDecimalUtil {
	/**
	 * 	比较左右值,如为null默认取0,比较的是数学上的有效数字
	 * eg:(0,1)->-1;(1,1)->0;(1,0)->1
	 * @param left
	 * @param right
	 * @return int[] = {-1,0,1}
	 */
	static public int compareTo(BigDecimal left , BigDecimal right) {
		left = left == null ? BigDecimal.ZERO : left;
		right = right == null ? BigDecimal.ZERO : right;
		return left.compareTo(right);
	}
}
