package com.wuchaooooo.service.excel.service;
/*
 * @Author wuchaooooo
 * @Date 2018年02月03日
 * @Time 14:43
 */

import com.wuchaooooo.service.excel.util.CoderUtils;
import com.wuchaooooo.service.excel.util.ExcelUtils;

import java.io.*;
import java.util.*;

public class ErrorCode {

    public static void start(String srcExcelPath, String targetExcelPath, String targetPropertiesPath) {
        run(srcExcelPath, targetExcelPath, targetPropertiesPath);
    }

    public static void run(String srcExcelPath, String targetExcelPath, String targetPropertiesPath) {
        try {
            //读取常规错误码编号
            List<String> commonErrorCodeList = ExcelUtils.readXLSXFile(srcExcelPath, 0, 3, 18, 7);
            //读取elsweb错误码编号
            List<String> elsErrorCodeList = ExcelUtils.readXLSXFile(srcExcelPath, 0, 61, 103, 7);
            List<String> allErrorCodeList = new ArrayList<>();
            //错误码转换
            for (String errorCode : commonErrorCodeList) {
                String key = new StringBuffer().append("errorCode.").append(errorCode).append(".description").toString();
                allErrorCodeList.add(key);
            }
            for (String errorCode : elsErrorCodeList) {
                String key = new StringBuffer().append("errorCode.").append(errorCode).append(".description").toString();
                allErrorCodeList.add(key);
            }


            //读取常规错误码描述
            List<String> commonErrorCodeDescList = ExcelUtils.readXLSXFile(srcExcelPath, 0, 3, 18, 8);
            //读取elsweb错误码描述
            List<String> elsErrorCodeDescList = ExcelUtils.readXLSXFile(srcExcelPath, 0, 61, 103, 8);
            List<String> allErrorCodeDescList = new ArrayList<>();
            allErrorCodeDescList.addAll(commonErrorCodeDescList);
            allErrorCodeDescList.addAll(elsErrorCodeDescList);

            //将错误码写入另一个Excel
            ExcelUtils.writeXLSXFile(targetExcelPath, 2, 1, 1, allErrorCodeList);

            //将错误码描述写入另一个Excel
            ExcelUtils.writeXLSXFile(targetExcelPath, 2, 1, 6, allErrorCodeDescList);


            //将错误码描述从中文转成普通unicode编码
            for (int i = 0; i < allErrorCodeDescList.size(); i++) {
//                allErrorCodeDescList.set(i, String.valueOf(CoderUtils.StringToUnicode(allErrorCodeDescList.get(i))));
                allErrorCodeDescList.set(i, String.valueOf(CoderUtils.string2Unicode(allErrorCodeDescList.get(i))));
            }

            //将错误码及其描述unicode写入properties文件
            Map<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < allErrorCodeList.size(); i++) {
                map.put(allErrorCodeList.get(i), allErrorCodeDescList.get(i));
            }
            writeProperties(targetPropertiesPath, map);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeProperties(String targetFilePath, Map<String, String> data) {

        OrderedProperties properties = new OrderedProperties();
//        try {
//            OutputStream outputStream = new FileOutputStream(targetFilePath);
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
//            for (Map.Entry<String, String> entry : data.entrySet()) {
//                properties.setProperty(entry.getKey(), entry.getValue());
//            }
//            properties.store(bw, null);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OutputStream output = null;
        try {
            output = new FileOutputStream(targetFilePath);
            //保存键值对到内存
            for (Map.Entry<String, String> entry : data.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(output, "wangwuchao modify" + new Date().toString());// 保存键值对到文件中
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
