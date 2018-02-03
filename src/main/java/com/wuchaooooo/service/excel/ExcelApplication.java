package com.wuchaooooo.service.excel;

import com.wuchaooooo.service.excel.service.ErrorCode;

//@SpringBootApplication
public class ExcelApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ExcelApplication.class, args);
		ErrorCode.start("/Users/wuchaooooo/Desktop/事件中心错误码.xlsx", "/Users/wuchaooooo/Desktop/事件中心多语言字符管理文档.xlsx", "/Users/wuchaooooo/Idea/wuchaooooo/java-web-projects/excel/src/main/resources/config.properties");
	}
}
