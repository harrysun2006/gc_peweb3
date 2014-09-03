package com.gc.util;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlUtil {

	private JxlUtil() {}

	public static WritableWorkbook copy(OutputStream os, String name) throws Exception {
		return copy(os, new File(name));
	}

	public static WritableWorkbook copy(OutputStream os, File file) throws Exception {
		Workbook template = Workbook.getWorkbook(file);
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setEncoding("GBK");
		return Workbook.createWorkbook(os, template, wbs);
	}

	public static void writeCell(WritableSheet ws, int col, int row, Object value) throws Exception {
		WritableCell wcell = ws.getWritableCell(col, row);
		CellFormat format = wcell.getCellFormat();
		if (wcell instanceof Label) {
			Label label = (Label) wcell;
			label.setString(String.valueOf(value));
		} else if (wcell instanceof jxl.write.Number) {
			jxl.write.Number number = (jxl.write.Number) wcell;
			number.setValue(ObjectUtil.toDouble(value));
		} else if (wcell instanceof jxl.write.DateTime) {
			DateTime date = (DateTime) wcell;
			date.setDate(ObjectUtil.toDate(value));
		} else {
			if (value instanceof Number) wcell = new jxl.write.Number(col, row, ObjectUtil.toDouble(value));
			else if (value instanceof Date) wcell = new DateTime(col, row, ObjectUtil.toDate(value));
			else wcell = new Label(col, row, ObjectUtil.toString(value));
			if (format != null) wcell.setCellFormat(format);
			ws.addCell(wcell);
		}
	}

	public static void insertRow(WritableSheet ws, int row) throws Exception {
		ws.insertRow(row);
		if (row < ws.getRows() - 1) {
			WritableCell wcell, ncell;
			for (int i = 0; i < ws.getColumns(); i++) {
				wcell = ws.getWritableCell(i, row + 1);
				ncell = wcell.copyTo(i, row);
				ws.addCell(ncell);
			}
		}
	}

	public static void insertColumn(WritableSheet ws, int col) throws Exception {
		ws.insertColumn(col);
		if (col < ws.getColumns() - 1) {
			ws.setColumnView(col, ws.getColumnView(col + 1));
			WritableCell wcell, ncell;
			for (int i = 0; i < ws.getRows(); i++) {
				wcell = ws.getWritableCell(col + 1, i);
				ncell = wcell.copyTo(col, i);
				ws.addCell(ncell);
			}
		}
	}

}
