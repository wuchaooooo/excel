package com.wuchaooooo.service.excel.util;
/*
 * @Author wuchaooooo
 * @Date 2018年02月03日
 * @Time 16:06
 */

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class CoderUtils {

    /*
         * string转普通类型的unicode
         */
    public static String StringToUnicode(String input) {
        String str = "";
        for (char c : input.toCharArray()) {
            if ((int) c > 128)
                str += "\\u" + Integer.toHexString((int) c);
            else
                str += c;
        }
        return str;

    }

    public static String string2Unicode(String s) {
        StringBuffer out = new StringBuffer("");
        try {
            // 转换为byte数组
            byte[] bytes = s.getBytes("unicode");
            /*
             * 从bytes[2]位开始取值，每一个字符都在数组中占两位
             * 每一个数组前一位转换为16进制字符串str1，后一位转换为16进制字符串str2；依次序拼接'\\u'+str1+str2
             * 得到一个字符的完整Unicode码
             */
            for (int i = 2; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                // 将前位转换为16进制
                // &的位运算保证bytes[i]的前三个字节是0，只有最后一个字节有数（bytes[i]占位4个字节）
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                // 转换为16进制后默认需要两位，不足两位的用0补齐
                for (int j = str1.length(); j < 2; j++) {
                    out.append("0");
                }
                out.append(str1);
                // 将前位转换为16进制
                String str2 = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str2.length(); j < 2; j++) {
                    out.append("0");
                }
                out.append(str2);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 转换Unicode码为大写，并返回
        return   out.toString().toUpperCase(Locale.US).replace("\\U", "\\u");
    }
}
