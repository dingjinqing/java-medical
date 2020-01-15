package com.vpu.sql.util;

import com.google.common.collect.Lists;
import com.vpu.sql.entity.ColumnOperator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileUtil {

    /**
     * 读取sql文件的sql语句，并返回sql文件里的所有的sql语句的List集合
     * @param sqlPath sql文件的绝对路径
     * @return sql语句的List集合
     */
    public static List<String> readSqlFile(String sqlPath){
        List<String>  results = getSqlLines(sqlPath);
        if( !CollectionUtils.isEmpty(results) ){
            return substringSql(results);
        }else{
            return results;
        }
    }
    /**
     * 读取sql文件的sql语句，并返回sql文件里的所有的sql语句的List集合
     * @param sqlPath sql文件的绝对路径
     * @return sql语句的List集合
     */
    public static List<String> readSqlFileByJar(String sqlPath){
        List<String>  results = getJarSqlLines(sqlPath);
        if( !CollectionUtils.isEmpty(results) ){
            return substringSql(results);
        }else{
            return results;
        }
    }

    /**
     * 读取文件的内容
     * @param url 文件的路径
     * @return List文件每一行的内容
     */
    private static List<String> getSqlLines(String url){
        Objects.requireNonNull(url);
        Path path = Paths.get(url);
        List<String> results = Lists.newArrayList();
        try {
            results.addAll(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    /**
     * 读取文件的内容
     * @param url 文件的路径
     * @return List文件每一行的内容
     */
    private static List<String> getJarSqlLines(String url){
        Objects.requireNonNull(url);
        List<String> results = Lists.newArrayList();
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines().forEach(results::add);
        return results;
    }
    /**
     * 读取sql文件的需要执行sql语句，并返回
     * @param sqlPath sql文件的绝对路径
     * @param version 指定的版本号
     * @return sql语句的List集合
     */
    public static List<String> readSqlFile(String sqlPath,String version){
        Objects.requireNonNull(sqlPath);
        List<String> sqlLines = getSqlLines(sqlPath);
        if( StringUtils.isEmpty(version) ){
            return substringSql(sqlLines);
        }else{
            int begin = -1,end = -1;
            for (int i = 0; i < sqlLines.size(); i++) {
                String line = sqlLines.get(i);
                if( line.indexOf("/")!= 0 ) {
                    continue;
                }
                if ( (version+"begin").equals(getSqlLineVersion(line)) ){
                    begin = i;
                }
                if ( (version+"end").equals(getSqlLineVersion(line)) ){
                    end = i;
                }
            }

            if( begin>0 && end>0 && end>begin ) {
                return substringSql(sqlLines.subList(begin,end));
            }else{
                return Lists.newArrayList();
            }
        }
    }

    private static String getSqlLineVersion(String line){
        return line.replaceAll("\\*","").replaceAll("/","").toLowerCase();
    }
    /**
     * 判断当前行是否是注释
     * @param sql 当前行
     * @return true/false
     */
    private static boolean checkInvalidSql(String sql){
        return sql.indexOf("-") == 0 || sql.indexOf("#") == 0 || StringUtils.isEmpty(sql)|| sql.indexOf("/")==0;
    }
    /**
     * 检查当前行是否是设置索引sql，且设置了索引的长度
     * @param sql 当前行
     * @return true/false
     */
    private static boolean checkSetIndexLength(String sql){
        String newSql = sql.trim().toLowerCase();
        int indexAddress = newSql.indexOf("key");
        int lastParenthesesAddress = newSql.lastIndexOf(")");
        if(indexAddress != 0 || newSql.length() - lastParenthesesAddress > 2){
            return false;
        }
        return (int)newSql.charAt(newSql.lastIndexOf("191")+3)==41;
    }

    private static List<String> substringSql(List<String> sqlLines){
        List<String> sqlList = Lists.newArrayList();
        StringBuilder sqlBuilder = new StringBuilder();
        for( String sql : sqlLines ){
            if( StringUtils.isEmpty(sql) || checkInvalidSql(sql)){
                continue;
            }
            if( checkSetIndexLength(sql) ){
                sql = sql.replaceAll("\\(191\\)","");
            }
            if( sql.lastIndexOf(";") == sql.length()-1 ){
                if( StringUtils.isEmpty(sqlBuilder) ){
                    sqlList.add(sql);
                }else{
                    sqlList.add(sqlBuilder.append(" ").append(sql).toString());
                    sqlBuilder.delete(0,sqlBuilder.length());
                }

            }else{
                sqlBuilder.append(" ").append(sql);
            }
        }
        return sqlList;
    }

    public static void main(String[] args) {
        String a= "key `index`(`index`(191)),";
        System.out.println(a.length());
        System.out.println(checkSetIndexLength(a));
        System.out.println(a.indexOf(")"));
        System.out.println(a.lastIndexOf(")"));
    }

    public static Map<String, ColumnOperator> getUpdateSqlData(String sql){
        return null;
    }
}

