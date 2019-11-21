package com.vpu.mp.service.foundation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    private static final List<String> STR = Arrays.asList(
       new String[] { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" });



    public static List<String> getSubStrList(String first,String lastStr,String content){
        List<String> result = new ArrayList<>();
        for(String key: STR){
            if(first.contains(key)){
                first = first.replace(key, "\\" + key);
            }
            if( lastStr.contains(key) ){
                lastStr = lastStr.replace(key, "\\" + key);
            }
        }
        StringBuilder pStr = new StringBuilder("(?<=")
            .append(first)
            .append(").*?(?=")
            .append(lastStr)
            .append(")");
        Pattern p = Pattern.compile(pStr.toString());
        Matcher m = p.matcher(content);
        while (m.find()){
            result.add(m.group());
        }
        return result;
    }
}
