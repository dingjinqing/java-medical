package com.vpu.mp.service.foundation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date工具
 * @author: 卢光耀
 * @date: 2019-07-26 10:51
 *
*/
public class DateUtil {

    public static final String pattern_one = "yyyyMMdd";

    public static String dateFormat(String pattern, Date date){
        return new SimpleDateFormat(pattern).format(date);
    }

}
