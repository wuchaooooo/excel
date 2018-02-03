package com.wuchaooooo.service.excel.util;
/*
 * @Author wuchaooooo
 * @Date 2018年02月03日
 * @Time 14:16
 */

import javafx.scene.control.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    public static List<String> readXLSXFile(String filePath, int sheetIndex, int startRowIndex, int endRowIndex, int cellIndex) throws IOException {
        InputStream ExcelFileToRead = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();
        List<String> list = new ArrayList<>();

        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();
            if (row.getRowNum() < startRowIndex || row.getRowNum() > endRowIndex) {
                //不在指定范围row
                continue;
            } else {
                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                    if (cell.getAddress().getColumn() != cellIndex) {
//                        不在指定范围cell
                            continue;
                    } else {
                        if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            int cellValue = (int) cell.getNumericCellValue();
                            String cellValueStr = String.valueOf(cellValue);
                            list.add(cellValueStr);
                        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            String cellValue = cell.getStringCellValue();
                            list.add(cellValue);
                        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
                            String cellValue = cell.getRichStringCellValue().getString();
                            list.add(cellValue);
                        }
                    }
                }
            }
        }
        return list;
    }


    //向已存在的Excel追加数据(追加多行，单列)
    public static void writeXLSXFile(String filePath, int sheetIndex, int startRowIndex, int cellIndex, List<String> data) throws IOException
    {
        InputStream ExcelFileToRead = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        ExcelFileToRead.close();

        XSSFSheet sheet = wb.getSheetAt(sheetIndex);

        FileOutputStream out=new FileOutputStream(filePath);

        for (int i = 0; i < data.size(); i++) {
            if (sheet.getRow(startRowIndex) == null) {
                sheet.createRow(startRowIndex).createCell(cellIndex).setCellValue(data.get(i));
            } else {
                sheet.getRow(startRowIndex).createCell(cellIndex).setCellValue(data.get(i));
            }
            startRowIndex++;
        }

        out.flush();
        wb.write(out);
        out.close();
    }
}
