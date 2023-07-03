/*
 * ChartBuilder.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import util.BarChart;
import util.CalendarUtil;
import data.ResultTable;
import data.SpyReader;
import data.XMLFileReader;
import domain.Measurement;
import domain.Result;
import domain.SpyProperties;

/**
 * Gerador de gráficos.<br>
 * 
 * O gráfico é retornado em um <code>JPanel</code>.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class ChartBuilder extends ViewBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeDailyView(java.util.Calendar)
	 */
	public JPanel makeDailyView(Calendar targetTime) {
		return makeChart(targetTime, SpyProperties.DAILY_TITLE,
				SpyProperties.DAILY_AXISX_TITLE, Calendar.HOUR_OF_DAY,
				ViewBuilder.DAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeWeeklyView(java.util.Calendar)
	 */
	public JPanel makeWeeklyView(Calendar targetTime) {
		return makeChart(targetTime, SpyProperties.WEEKLY_TITLE,
				SpyProperties.WEEKLY_AXISX_TITLE, Calendar.DAY_OF_WEEK,
				ViewBuilder.WEEK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeMonthlyView(java.util.Calendar)
	 */
	public JPanel makeMonthlyView(Calendar targetTime) {
		return makeChart(targetTime, SpyProperties.MONTHLY_TITLE,
				SpyProperties.MONTHLY_AXISX_TITLE, Calendar.DAY_OF_MONTH,
				ViewBuilder.MONTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ViewBuilder#makeYearlyView(java.util.Calendar)
	 */
	public JPanel makeYearlyView(Calendar targetTime) {
		return makeChart(targetTime, SpyProperties.YEARLY_TITLE,
				SpyProperties.YEARLY_AXISX_TITLE, Calendar.MONTH,
				ViewBuilder.YEAR);
	}

	/**
	 * Cria um gráfico de barra de acordo com a data-alvo fornecida como
	 * parâmetro.
	 * 
	 * @param targetTime
	 *            data-alvo
	 * @param title
	 *            tí­tulo do gráfico
	 * @param axisX
	 *            rótulo para o eixo X
	 * @param delimiter
	 *            delimitador (variável da classe
	 *            <code>java.util.Calendar</code>) para geração do gráfico
	 * @param period
	 *            período delimitador do gráfico
	 * @return <code>JPanel</code> contendo o gráfico gerado
	 */
	@SuppressWarnings("unchecked")
	private JPanel makeChart(Calendar targetTime, String title, String axisX,
			int delimiter, String period) {
		String format = "dd/MM/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String date = formatter.format(targetTime.getTime());
		
		BarChart chart = new BarChart(title + " (" + date + ") - " + SpyProperties.USER_NAME, axisX, SpyProperties.AXISY_TITLE,
				true);
		ResultTable table = null;
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

			// Calcula as fronteiras do gráfico
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

			table = new ResultTable(delimiter, measurements);
			Iterator<Result> ite = table.iterator();
			int index = begin;
			while (ite.hasNext()) {
				Result next = ite.next();

				int nextKey = next.getKey();
				m = ViewBuilder.class.getMethod("create" + period + "Label",
						Integer.class);

				// Leituras fictí­cias
				while (index < nextKey) {
					String label = (String) m.invoke(this, index);
					chart.addItem(0, SpyProperties.KEY_ATTRIBUTE_NAME, label);
					chart.addItem(0, SpyProperties.MOUSE_ATTRIBUTE_NAME, label);
					index++;
				}

				// Leituras realizadas
				Long keyPressed = next.getKeyPressed();
				Long mousePressed = next.getMousePressed();
				String label = (String) m.invoke(this, next.getKey());
				chart.addItem(keyPressed, SpyProperties.KEY_ATTRIBUTE_NAME,
						label);
				chart.addItem(mousePressed, SpyProperties.MOUSE_ATTRIBUTE_NAME,
						label);
				index++;
			}

			// Termina de preencher com leituras fictí­cias
			while (index <= end) {
				String label = (String) m.invoke(this, index);
				chart.addItem(0, SpyProperties.KEY_ATTRIBUTE_NAME, label);
				chart.addItem(0, SpyProperties.MOUSE_ATTRIBUTE_NAME, label);
				index++;
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

		return chart.getAsPanel();
	}
}
