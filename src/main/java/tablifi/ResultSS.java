package tablifi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ResultSS {

    private static final Logger logger = LogManager.getLogger();
    private final List<String> ssHeaderRow = new ArrayList<>();
    private final List<SSRow> ssRows = new ArrayList<>();

    private final Workbook wb;
    private final Sheet sheet;

    private DataFormat dataFormat;
    private CellStyle headerStyle;
    private CellStyle bodyStyle;
    private CellStyle highlightStyle;

    private CellStyle dateStyle;

    private CellStyle timeStyle;

    public List<String> getSsHeaderRow() {
        return ssHeaderRow;
    }

    public List<SSRow> getSsRows() {
        return ssRows;
    }

     public ResultSS() {
        wb = new XSSFWorkbook();
        sheet = wb.createSheet("Results");
        PrintSetup ps = sheet.getPrintSetup();

        sheet.setHorizontallyCenter(true);
        sheet.setVerticallyCenter(true);

        ps.setLandscape(true);
        ps.setFitHeight((short) 1);
        ps.setFitWidth((short) 1);

        dataFormat = wb.createDataFormat();
    }

    public void createSpreadsheet(OutputStream outputStream) throws IOException {
        sort();

        setUpStyles();

        int maxWidth = getMaxColumnWidth() + 4;

        int noColumns = ssHeaderRow.size();

        //Set column widths.
        for (int i = 0; i <= noColumns - 1; i++) {
            sheet.setColumnWidth(i, maxWidth * 256);
        }

        //Create header row.
        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex);
        row.setRowStyle(headerStyle);

        Cell headerCell;
        int i = 0;
        for (String el : ssHeaderRow) {
            headerCell = row.createCell(i);
            headerCell.setCellValue(el);
            headerCell.setCellStyle(headerStyle);
            i++;
        }

        //Complete body of table.
        rowIndex++;
        Cell bodyCell;
        int colIndex;
        for (SSRow ssRow : ssRows) {
            row = sheet.createRow(rowIndex);
            //row.setHeight((short) (2 * row.getHeight()));

            colIndex = 0;

            //FileName.
            bodyCell = row.createCell(colIndex);
            bodyCell.setCellValue(ssRow.getFileName());
            bodyCell.setCellStyle(bodyStyle);
            colIndex++;

            //Date taken
            bodyCell = row.createCell(colIndex);
            bodyCell.setCellValue(ssRow.getDateTaken());
            bodyCell.setCellStyle(dateStyle);
            colIndex++;

            //Time taken
            bodyCell = row.createCell(colIndex);
            bodyCell.setCellValue(ssRow.getTimeTaken());
            bodyCell.setCellStyle(timeStyle);
            colIndex++;

            //Description
            bodyCell = row.createCell(colIndex);
            bodyCell.setCellValue(ssRow.getDescription());
            bodyCell.setCellStyle(bodyStyle);
            rowIndex++;
        }


        wb.write(outputStream);
        outputStream.close();

    }

    private int getMaxColumnWidth() {
        int maxWidth = 0;
        int width;
        for (SSRow r : ssRows) {
            width = r.getDescription().length();
            maxWidth = Math.max(width, maxWidth);
        }
        return maxWidth;
    }

    public void sort() {
        ssRows.sort(null);

    }

    private void setUpStyles() {
        DataFormat dataFormat = wb.createDataFormat();

        Font normalFont = wb.createFont();
        Font boldFont = wb.createFont();
        boldFont.setBold(true);

        headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFont(boldFont);

        bodyStyle = wb.createCellStyle();
        bodyStyle.setWrapText(true);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setFont(normalFont);

        highlightStyle = wb.createCellStyle();
        highlightStyle.setWrapText(true);
        highlightStyle.setAlignment(HorizontalAlignment.RIGHT);
        highlightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        highlightStyle.setFont(boldFont);

        dateStyle = wb.createCellStyle();
        dateStyle.setWrapText(true);
        dateStyle.setAlignment(HorizontalAlignment.RIGHT);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateStyle.setFont(normalFont);

        dateStyle.setDataFormat(dataFormat.getFormat("dd/mm/yyyy"));

        timeStyle = wb.createCellStyle();
        timeStyle.setWrapText(true);
        timeStyle.setAlignment(HorizontalAlignment.RIGHT);
        timeStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        timeStyle.setFont(normalFont);

        timeStyle.setDataFormat(dataFormat.getFormat("hh:MM:ss"));

    }
}

