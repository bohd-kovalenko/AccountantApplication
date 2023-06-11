package com.java.coursework.services.impl;

import com.java.coursework.models.Person;
import com.java.coursework.services.ExcelCreatorService;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelCreatorServiceImpl implements ExcelCreatorService {
    private static final String SHEET_NAME = "Звіт";
    private final ObservableList<Person> mainList;
    @Value("${excel-file-path}")
    private String excelFilePath;
    private CellStyle headerFooterStyle;
    private CellStyle defaultStyle;
    private CellStyle blueCellStyle;

    /*
        Метод, що викликається з інших класів
     */
    @SneakyThrows
    @Override
    public void createExcelDocument() {
        Workbook workbook = new XSSFWorkbook();
        initWorkBook(workbook);
        downloadTable(workbook);
    }

    /*
        Ініціалізація всієї ексель таблиці
     */
    private void initWorkBook(Workbook workbook) {
        initBlueCellStyle(workbook);
        initHeaderFooterStyle(workbook);
        initDefaultCellStyle(workbook);
        initHeader(workbook);
        fillInfo(workbook);
        initFooter(workbook);
    }

    // Ініціалізація заголовка таблиці
    private void initHeader(Workbook workbook) {
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        sheet.setDefaultRowHeight((short) 500);
        Row header = sheet.createRow(0);
        List<String> columnNames = List.of("№", "ПІБ відрядженого", "Добові", "Проживання", "Проїзд", "Інше", "Разом");
        for (int i = 0; i < columnNames.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(columnNames.get(i));
            headerCell.setCellStyle(headerFooterStyle);
            sheet.autoSizeColumn(i);
        }
    }

    // Ініціалізація підвалу таблиці
    private void initFooter(Workbook workbook) {
        Sheet sheet = workbook.getSheet(SHEET_NAME);
        Row footerRow = sheet.createRow(sheet.getLastRowNum() + 1);
        Cell currCell = footerRow.createCell(0);
        currCell.setCellValue("Всього за період:");
        currCell.setCellStyle(headerFooterStyle);
        currCell = footerRow.createCell(1);
        currCell.setCellStyle(defaultStyle);
        CellRangeAddress region = new CellRangeAddress(footerRow.getRowNum(), footerRow.getRowNum(), 0, 1);
        sheet.addMergedRegion(region);
        for (int i = 2; i <= 6; i++) {
            currCell = footerRow.createCell(i);
            double columnResultValue = 0;
            for (int e = 1; e < sheet.getLastRowNum(); e++) {
                Row currRow = sheet.getRow(e);
                columnResultValue += Double.parseDouble(currRow.getCell(i).getStringCellValue());
            }
            currCell.setCellValue(columnResultValue);
            currCell.setCellStyle(blueCellStyle);
        }
    }

    // Заповнення інформацією в таблиці
    private void fillInfo(Workbook workbook) {
        Sheet sheet = workbook.getSheet(SHEET_NAME);
        Row reportRow;
        Cell currentCell;
        for (int i = 0; i < mainList.size(); i++) {
            Person person = mainList.get(i);
            reportRow = sheet.createRow(i + 1);
            currentCell = reportRow.createCell(0);
            currentCell.setCellValue(i);
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(1);
            currentCell.setCellValue(person.getNameSurname());
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(2);
            currentCell.setCellValue(person.getPerDiemValue().toString());
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(3);
            currentCell.setCellValue(person.getLeavingSumValue().toString());
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(4);
            currentCell.setCellValue(person.getTravelSumValue().toString());
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(5);
            currentCell.setCellValue(person.getOthersSumValue().toString());
            currentCell.setCellStyle(defaultStyle);
            currentCell = reportRow.createCell(6);
            currentCell.setCellValue(person.getValue().toString());
            currentCell.setCellStyle(defaultStyle);
        }
    }

    // Ініціалізація стилю заголовку та підвалу таблиці
    private void initHeaderFooterStyle(Workbook workbook) {
        headerFooterStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerFooterStyle.setFont(font);
        headerFooterStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerFooterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorders(headerFooterStyle);
    }

    // Ініціалізація стилю для комірок за замовчуванням
    private void initDefaultCellStyle(Workbook workbook) {
        defaultStyle = workbook.createCellStyle();
        setBorders(defaultStyle);
    }

    // Ініціалізація стилю для синіх комірок
    private void initBlueCellStyle(Workbook workbook) {
        blueCellStyle = workbook.createCellStyle();
        blueCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        blueCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorders(blueCellStyle);
    }

    // Встановлення границь для стилю комірок
    private void setBorders(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }

    // Збереження таблиці в Excel-файлі
    @SneakyThrows
    private void downloadTable(Workbook workbook) {
        File file = new File(excelFilePath);
        file.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            System.out.println("Error saving excel report!");
        }
    }
}
