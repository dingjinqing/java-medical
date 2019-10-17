package com.vpu.mp.service.foundation.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.LoggerFactory;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 */
public class ChineseToPinYinUtil {

    private static final HanyuPinyinOutputFormat format;

    static {
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 获取中文名对应的拼音名的首字母
     * @param chinese 待转换字符串
     * @return 转换后的中文拼音首字母字符串
     */
    public static String getStartAlphabet(String chinese) {
        String otherCharacter = "#";
        if (chinese == null ||chinese.length()==0) {
            return otherCharacter;
        }
        StringBuilder pinName = new StringBuilder();
        char[] chineseChar = chinese.toCharArray();

        char startChar = chineseChar[0];
        if (Character.toString(startChar).matches("[\\u4E00-\\u9FA5]")){
            try {
                pinName.append(PinyinHelper.toHanyuPinyinStringArray(startChar, format)[0].charAt(0));
            } catch (BadHanyuPinyinOutputFormatCombination message) {
                LoggerFactory.getLogger(ChineseToPinYinUtil.class).warn("中文转拼音错误："+message.getMessage());
                pinName.append(otherCharacter);
            }
        }else{
            pinName.append(startChar);
        }

        return pinName.toString();
    }

    public static String getPinYin(String chinese) {
        String otherCharacter = "#";
        if (chinese == null ||chinese.length()==0) {
            return otherCharacter;
        }
        StringBuilder pinName = new StringBuilder();
        char[] chineseChar = chinese.toCharArray();
        for (int i = 0; i < chineseChar.length; i++) {
            if (Character.toString(chineseChar[i]).matches("[\\u4E00-\\u9FA5]")) {
                try {
                   pinName.append( PinyinHelper.toHanyuPinyinStringArray(chineseChar[i],format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination message) {
                    LoggerFactory.getLogger(ChineseToPinYinUtil.class).warn("中文转拼音错误："+message.getMessage());
                    pinName.append(otherCharacter);
                }
            } else {
                pinName.append(chineseChar[i]);
            }
        }
        return pinName.toString();
    }
}
