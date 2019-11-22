package com.vpu.mp.service.foundation.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.STRING_ZERO;

/**
 * 	扩展BigDecimal
 * @author 王帅
 *
 */
public class BigDecimalUtil {
    public static final int DEFAULT_SCALE = 2;
    public static final BigDecimal BIGDECIMAL_ZERO = BigDecimal.ZERO.setScale(DEFAULT_SCALE);
	public enum Operator {
		//加
		add,
		//减
		subtrac,
		//乘法
		multiply
		//除法
		,Divide
	}
	/**
	 * 	比较左右值,如为null默认取0,比较的是数学上的有效数字
	 * eg:(0,1)->-1;(1,1)->0;(1,0)->1
	 * @param left left
	 * @param right right
	 * @return int[result] = {-1,0,1}
	 */
	static public int compareTo(BigDecimal left , BigDecimal right) {
        left = left == null ? BigDecimal.ZERO : left;
        right = right == null ? BigDecimal.ZERO : right;
		return left.compareTo(right);
	}

	/**
	 * 加法,如为null默认取0
     * @param left left
     * @param right right
	 * @return result left+right
	 */
	static public BigDecimal add(BigDecimal left , BigDecimal right) {
        left = left == null ? BigDecimal.ZERO : left;
        right = right == null ? BigDecimal.ZERO : right;
		return left.add(right);
	}

	/**
	 * 减法,如为null默认取0
     * @param left left
     * @param right right
	 * @return result left-+right
	 */
	static public BigDecimal subtrac(BigDecimal left , BigDecimal right) {
        left = left == null ? BigDecimal.ZERO : left;
        right = right == null ? BigDecimal.ZERO : right;
		return left.subtract(right);
	}

	/**
	 * BigDecimal乘法：精度保留小数点后两位，采取四舍五入
	 * @param left	null->zero
	 * @param right null->zero
	 * @return value
	 */
	static public BigDecimal multiply(BigDecimal left , BigDecimal right) {
        left = left == null ? BigDecimal.ZERO : left;
        right = right == null ? BigDecimal.ZERO : right;
        return left.multiply(right).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

	/**
	 * BigDecimal除法：精度保留小数点后两位，采取四舍五入
	 * @param left=null->zero
     * @param right=null throw Exception
	 * @return value
	 */
	static public BigDecimal divide(BigDecimal left , BigDecimal right) throws ArithmeticException{
        left = left == null ? BigDecimal.ZERO : left;
		if(right == null || compareTo(right, null) < 1) {
			throw new ArithmeticException("Division by zero");
		}
        return left.divide(right, DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal除法：精度保留小数点后两位，采取四舍五入
     *
     * @param left  left
     * @param right right
     * @return value left/right为null,为0直接返回0.00
     */
    static public BigDecimal divideWithOutCheck(BigDecimal left, BigDecimal right) {
        if (left == null || left.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (right == null || right.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return left.divide(right, DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal除法：精度保留小数点后两位，采取四舍五入
     *
     * @param left  left
     * @param right right
     * @return value left/right为null,为0直接返回0.00
     */
    static public BigDecimal divideWithOutCheck(Object left, Object right) {
        if (left == null || StringUtils.isBlank(left.toString()) || STRING_ZERO.equals(left.toString())) {
            return BigDecimal.ZERO;
        }
        if (right == null || StringUtils.isBlank(right.toString()) || STRING_ZERO.equals(right.toString())) {
            return BigDecimal.ZERO;
        }
        BigDecimal tempLeft = new BigDecimal(left.toString());
        BigDecimal tempRight = new BigDecimal(right.toString());
        return tempLeft.divide(tempRight, 2, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal乘法：精度保留小数点后两位，采取RoundingMode
     * @param left	null->zero
     * @param right null->zero
     * @return value
     */
    static public BigDecimal multiply(BigDecimal left , BigDecimal right , RoundingMode roundingMode) {
        left = left == null ? BigDecimal.ZERO : left;
        right = right == null ? BigDecimal.ZERO : right;
        return left.multiply(right).setScale(DEFAULT_SCALE, roundingMode);
    }

    /**
     * BigDecimal除法：精度保留小数点后两位，采取RoundingMode
     * @param left=null->zero
     * @param right=null throw Exception
     * @return value
     */
    static public BigDecimal divide(BigDecimal left , BigDecimal right , RoundingMode roundingMode) throws ArithmeticException{
        left = left == null ? BigDecimal.ZERO : left;
        if(right == null || compareTo(right, null) < 1) {
            throw new ArithmeticException("Division by zero");
        }
        return left.divide(right, DEFAULT_SCALE, roundingMode);
    }

	/**
	 * 	支持按照bigDecimals数组顺序进行加减运算，精度保留小数点后两位
	 * @param bigDecimals BigDecimalPlus类属性为value（值）与operator（该值与其后一位的运算符）
	 * @return value
	 */
	static public BigDecimal addOrSubtrac(BigDecimalPlus...bigDecimals) {
		if(bigDecimals == null || bigDecimals.length < DEFAULT_SCALE) {
			throw new IllegalArgumentException("method param Illegal,The parameter length should be greater than or equal to two.");
		}
		BigDecimalPlus left = bigDecimals[0];

        for (int i = 1 , n = bigDecimals.length ; i < n ; i++) {
			left.toOperator(bigDecimals[i]);
		}
		return left.getValue();
	}

    /**
	 * 	支持按照bigDecimals数组顺序进行乘除运算，精度保留小数点后两位，采取四舍五入
	 * @param bigDecimals BigDecimalPlus类属性为value（值）与operator（该值与其后一位的运算符）
	 * @return value
	 */
	static public BigDecimal multiplyOrDivide(BigDecimalPlus...bigDecimals) {
		if(bigDecimals == null || bigDecimals.length < DEFAULT_SCALE) {
			throw new IllegalArgumentException("method param Illegal,The parameter length should be greater than or equal to two.");
		}
		BigDecimalPlus left = bigDecimals[0];

        for (int i = 1 , n = bigDecimals.length ; i < n ; i++) {
			left.toOperator(bigDecimals[i]);
		}
		return left.getValue().setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
	}

    /**
	 * 	四则运算增强（目前仅支持乘除法或加减法，不支持加减乘除同时运算）
	 * @author 王帅
	 *
	 */
	@Data
	static public class BigDecimalPlus {
		private BigDecimal value;
		private Operator operator;
		private BigDecimalPlus(BigDecimal bigDecimal , Operator operator) {
			this.value = bigDecimal;
			this.operator = operator;
		}
		/**
		 * 	静态构造器
		 * @param bigDecimal bigDecimal
		 * @param operator 操作符
		 * @return BigDecimalPlus
		 */
		public static BigDecimalPlus create(BigDecimal bigDecimal , Operator operator) {
			return new BigDecimalPlus(bigDecimal,operator);
		}
		private BigDecimalPlus toOperator(BigDecimalPlus bigDecimalPlus) {
			if(operator == null) {
				throw new IllegalArgumentException("non-last parameter must be input operator");
			}
			switch (operator) {
			case add:
				value = BigDecimalUtil.add(value, bigDecimalPlus.value);
				break;
			case subtrac:
				value = BigDecimalUtil.subtrac(value, bigDecimalPlus.value);
				break;
			case multiply:
				value = multiply(value, bigDecimalPlus.value);
				break;
			case Divide:
				value = divide(value, bigDecimalPlus.value);
				break;
			default:
				throw new IllegalArgumentException("method param Illegal,BigDecimalPlus object field operator must be an Operator enum type.");
			}
			operator = bigDecimalPlus.getOperator();
			return this;
		}

        /**
         * BigDecimal乘法：针对BigDecimalPlus四则运算增强
         * @param left	null->zero
         * @param right null->zero
         * @return value
         */
        private static BigDecimal multiply(BigDecimal left , BigDecimal right) {
            left = left == null ? BigDecimal.ZERO : left;
            right = right == null ? BigDecimal.ZERO : right;
            return left.multiply(right);
        }

        /**
         * BigDecimal除法：精度保留小数点后两位，采取四舍五入
         * @param left=null->zero
         * @param right=null throw Exception
         * @return value
         */
        static public BigDecimal divide(BigDecimal left , BigDecimal right) throws ArithmeticException{
            left = left == null ? BigDecimal.ZERO : left;
            if(right == null || compareTo(right, null) < 1) {
                throw new ArithmeticException("Division by zero");
            }
            return left.divide(right,8 , RoundingMode.HALF_UP);
        }

		public static void main(String[] args) {
			BigDecimal multiplyOrDivide = multiplyOrDivide(new BigDecimalPlus(new BigDecimal("1"),Operator.Divide),
					new BigDecimalPlus(new BigDecimal("3"),Operator.multiply),
					new BigDecimalPlus(new BigDecimal("3"),null)
					);
			System.out.println(multiplyOrDivide);
			BigDecimal addOrSubtrac = addOrSubtrac( BigDecimalPlus.create(new BigDecimal("1"),Operator.add),
					BigDecimalPlus.create(new BigDecimal("3"),null));
			System.out.println(addOrSubtrac);
		}
	}

}
