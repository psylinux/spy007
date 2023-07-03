/*
 * ReportBuilder.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package controller;

import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import util.CalendarUtil;
import data.ResultTable;
import data.SpyReader;
import data.XMLFileReader;
import domain.Measurement;
import domain.Result;
import domain.SpyProperties;

/**
 * Gerador de relatórios.<br>
 * 
 * O relatório é retornado em um <code>JPanel</code>.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class ReportBuilder extends ViewBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeDailyView(java.util.Calendar)
	 */
	public JPanel makeDailyView(Calendar targetTime) {
		return makeTable(targetTime, SpyProperties.DAILY_AXISX_TITLE,
				Calendar.HOUR_OF_DAY, ViewBuilder.DAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeWeeklyView(java.util.Calendar)
	 */
	public JPanel makeWeeklyView(Calendar targetTime) {
		return makeTable(targetTime, SpyProperties.WEEKLY_AXISX_TITLE,
				Calendar.DAY_OF_WEEK, ViewBuilder.WEEK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeMonthlyView(java.util.Calendar)
	 */
	public JPanel makeMonthlyView(Calendar targetTime) {
		return makeTable(targetTime, SpyProperties.MONTHLY_AXISX_TITLE,
				Calendar.DAY_OF_MONTH, ViewBuilder.MONTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeYearlyView(java.util.Calendar)
	 */
	public JPanel makeYearlyView(Calendar targetTime) {
		return makeTable(targetTime, SpyProperties.YEARLY_AXISX_TITLE,
				Calendar.MONTH, ViewBuilder.YEAR);
	}

	/**
	 * Cria um relatório de acordo com a data-alvo fornecida como parâmetro.
	 * 
	 * @param targetTime
	 *            data-alvo
	 * @param axisX
	 *            rótulo para as linhas do relatório
	 * @param delimiter
	 *            delimitador (variável da classe
	 *            <code>java.util.Calendar</code>) para geração do gráfico
	 * @param period
	 *            perí­odo delimitador do gráfico
	 * @return <code>JPanel</code> contendo o gráfico gerado
	 */
	@SuppressWarnings("unchecked")
	private JPanel makeTable(Calendar targetTime, String axisX, int delimiter,
			String period) {
		String[] columns = { axisX, SpyProperties.KEY_ATTRIBUTE_NAME,
				SpyProperties.MOUSE_ATTRIBUTE_NAME,
				SpyProperties.REPORT_MINUTE_STRING,
				SpyProperties.REPORT_DV1_TITLE, SpyProperties.REPORT_DV2_TITLE };
		ResultTable resTable = null;
		Object[][] values = null;
		SpyReader reader = new XMLFileReader(SpyProperties.USER_DIR_NAME);

		try {
			// Invoca o método apropriado para leitura de dados
			Method m = reader.getClass().getMethod("getBy" + period,
					Calendar.class);
			List<Measurement> measurements = (List<Measurement>) m.invoke(
					reader, targetTime);
			if (measurements == null) {
				return null;
			}

			// Calcula as fronteiras do relatório
			int begin = 0, end = 0;
			switch (delimiter) {
			case Calendar.HOUR_OF_DAY:
				begin = measurements.get(0).getCalendar().get(
						Calendar.HOUR_OF_DAY);
				end = measurements.get(measurements.size() - 1).getCalendar()
						.get(Calendar.HOUR_OF_DAY);
				break;
			case Calendar.DAY_OF_WEEK:
				begin = Calendar.SUNDAY;
				end = Calendar.SATURDAY;
				break;
			case Calendar.DAY_OF_MONTH:
				begin = 1;
				Calendar c = measurements.get(measurements.size() - 1)
						.getCalendar();
				end = CalendarUtil.daysInMonth(c.get(Calendar.MONTH), c
						.get(Calendar.YEAR));
				break;
			case Calendar.MONTH:
				begin = Calendar.JANUARY;
				end = Calendar.DECEMBER;
				break;
			}

			resTable = new ResultTable(delimiter, measurements);
			values = new Object[end - begin + 1][columns.length];
			Iterator<Result> ite = resTable.iterator();
			int index = begin;
			int row = 0;
			while (ite.hasNext()) {
				Result next = ite.next();

				// Leituras não realizadas
				int nextKey = next.getKey();
				m = ViewBuilder.class.getMethod("create" + period + "Label",
						Integer.class);
				while (index < nextKey) {
					values[row][0] = (String) m.invoke(this, index);
					values[row][1] = "N/A";
					values[row][2] = "N/A";
					values[row][3] = "N/A";
					values[row][4] = "N/A";
					values[row][5] = "N/A";
					index++;
					row++;
				}

				// Leituras realizadas
				Long keyPressed = next.getKeyPressed();
				Long mousePressed = next.getMousePressed();
				Long minutes = next.getMinutes();
				values[row][0] = (String) m.invoke(this, index);
				values[row][1] = keyPressed;
				values[row][2] = mousePressed;
				values[row][3] = minutes;
				values[row][4] = keyPressed / minutes;
				values[row][5] = mousePressed / minutes;
				index++;
				row++;
			}

			// Termina de preencher com leituras não realizadas
			while (index <= end) {
				values[row][0] = (String) m.invoke(this, index);
				values[row][1] = "N/A";
				values[row][2] = "N/A";
				values[row][3] = "N/A";
				values[row][4] = "N/A";
				values[row][5] = "N/A";
				index++;
				row++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}

		JTable table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(values, columns));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(1, 0));
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setViewportView(table);
		tablePanel.add(scrollTable);
		table.setToolTipText(SpyProperties.REPORT_TIP_TEXT);

		return tablePanel;
	}
}
