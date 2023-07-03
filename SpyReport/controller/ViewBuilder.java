/*
 * ViewBuilder.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package controller;

import java.util.Calendar;

import javax.swing.JPanel;

/**
 * Gerador de visualiza��es das estat�sticas.<br>
 * 
 * As visualiza��es s�o retornadas em um <code>JPanel</code>.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public abstract class ViewBuilder {

	/**
	 * Indicador de período di�rio.
	 */
	public static final String DAY = "Day";

	/**
	 * Indicador de per�odo semanal.
	 */
	public static final String WEEK = "Week";

	/**
	 * Indicador de per�odo mensal.
	 */
	public static final String MONTH = "Month";

	/**
	 * Indicador de per�odo anual.
	 */
	public static final String YEAR = "Year";

	/**
	 * Cria uma visualiza��o di�ria. <br>
	 * 
	 * As estat�sticas s�o geradas tendo como refer�ncia a data fornecida como
	 * par�metro e como delimitador as horas do dia. S�o apresentadas as horas
	 * compreendidas entre a hora da primeira leitura e a hora da �ltima leitura
	 * referente � consulta.
	 * 
	 * @param targetTime
	 *            data-alvo para gera��o da visualiza��o.
	 * @return um painel contendo a visualiza��o desejada <br>
	 *         <code>null</code> se n�o for encontrado um arquivo v�lido
	 *         referente a esse per�odo
	 */
	public abstract JPanel makeDailyView(Calendar targetTime);

	/**
	 * Cria uma visualiza��o semanal. <br>
	 * 
	 * As estat�sticas s�o geradas tendo como refer�ncia a data fornecida como
	 * par�metro e como delimitador os dias da semana.
	 * 
	 * @param targetTime
	 *            data-alvo para gera��o da visualiza��o.
	 * @return um painel contendo a visualiza��o desejada <br>
	 *         <code>null</code> se n�o for encontrado nenhum arquivo v�lido
	 *         referente a esse per�odo
	 */
	public abstract JPanel makeWeeklyView(Calendar targetTime);

	/**
	 * Cria uma visualiza��o mensal. <br>
	 * 
	 * As estat�sticas s�o geradas tendo como refer�ncia a data fornecida como
	 * par�metro e como delimitador os dias do m�s.
	 * 
	 * @param targetTime
	 *            data-alvo para gera��o da visualiza��o.
	 * @return um painel contendo a visualiza��o desejada <br>
	 *         <code>null</code> se não for encontrado nenhum arquivo v�lido
	 *         referente a esse per�odo
	 */
	public abstract JPanel makeMonthlyView(Calendar targetTime);

	/**
	 * Cria uma visualiza��o anual. <br>
	 * 
	 * As estat�sticas s�o geradas tendo como refer�ncia a data fornecida como
	 * par�metro e como delimitador os dias do ano.
	 * 
	 * @param targetTime
	 *            data-alvo para gera��o da visualiza��o.
	 * @return um painel contendo a visualiza��o desejada <br>
	 *         <code>null</code> se n�o for encontrado nenhum arquivo v�lido
	 *         referente a esse per�odo
	 */
	public abstract JPanel makeYearlyView(Calendar targetTime);

	/**
	 * Cria uma label para visualiza��es di�rias com a hora fornecida como
	 * par�metro e a pr�xima hora. Ex.:<br>
	 * <code>createDayLabel(8) = 8:00-9:00</code><br>
	 * <code>createDayLabel(23) = 23:00-0:00</code>
	 * 
	 * @param hour
	 *            hora da label
	 * @return label referente � hora fornecida e a hora posterior
	 * @throws IllegalArgumentException
	 *             se n�o for fornecida uma hora v�lida
	 */
	public String createDayLabel(Integer hour) {
		if (hour < 0 || hour > 24) {
			throw new IllegalArgumentException("Hour invalid: " + hour);
		}

		return (hour + ":00-" + ((hour + 1) % 23) + ":00");
	}

	/**
	 * Cria uma label para visualiza��es semanais com o dia da semana (de acordo
	 * com <code>java.util.Calendar</code>) fornecido como par�metro
	 * 
	 * @param dayOfWeek
	 *            dia da semana da label
	 * @return label referente ao dia da semana fornecido
	 * @throws IllegalArgumentException
	 *             se n�o for fornecida um dia da semana v�lido
	 * @see java.util.Calendar.SUNDAY
	 * @see java.util.Calendar.MONDAY
	 * @see java.util.Calendar.TUESDAY
	 * @see java.util.Calendar.WEDNESDAY
	 * @see java.util.Calendar.THURSDAY
	 * @see java.util.Calendar.FRIDAY
	 * @see java.util.Calendar.SATURDAY
	 */
	public String createWeekLabel(Integer dayOfWeek) {
		if (dayOfWeek < Calendar.SUNDAY || dayOfWeek > Calendar.SATURDAY) {
			throw new IllegalArgumentException("Hour invalid: " + dayOfWeek);
		}

		switch (dayOfWeek) {
		case (Calendar.SUNDAY):
			return "Domingo";
		case (Calendar.MONDAY):
			return "Segunda";
		case (Calendar.TUESDAY):
			return "Ter�a";
		case (Calendar.WEDNESDAY):
			return "Quarta";
		case (Calendar.THURSDAY):
			return "Quinta";
		case (Calendar.FRIDAY):
			return "Sexta";
		case (Calendar.SATURDAY):
			return "S�bado";
		default:
			return null;
		}
	}

	/**
	 * Cria uma label para visualiza��es mensais com o dia do m�s fornecido como
	 * par�metro
	 * 
	 * @param dayOfMonth
	 *            dia do m�s da label
	 * @return label referente ao dia do m�s fornecido
	 * @throws IllegalArgumentException
	 *             se não for fornecida um dia de m�s v�lido
	 */
	public String createMonthLabel(Integer dayOfMonth) {
		if (dayOfMonth < 1 || dayOfMonth > 31) {
			throw new IllegalArgumentException("Hour invalid: " + dayOfMonth);
		}

		return String.valueOf(dayOfMonth);
	}

	/**
	 * Cria uma label para visualiza��es anuais com o m�s (de acordo
	 * com <code>java.util.Calendar</code>) fornecido como par�metro
	 * 
	 * @param dia da semana
	 *            m�s da label
	 * @return label referente ao m�s fornecido.
	 * @throws IllegalArgumentException
	 *             se n�o for fornecida um m�s v�lido
	 * @see java.util.Calendar.JANUARY
	 * @see java.util.Calendar.FEBRUARY
	 * @see java.util.Calendar.MARCH
	 * @see java.util.Calendar.APRIL
	 * @see java.util.Calendar.MAY
	 * @see java.util.Calendar.JUNE
	 * @see java.util.Calendar.JULY
	 * @see java.util.Calendar.AUGUST
	 * @see java.util.Calendar.SEPTEMBER
	 * @see java.util.Calendar.OCTOBER
	 * @see java.util.Calendar.NOVEMBER
	 * @see java.util.Calendar.DECEMBER
	 */
	public String createYearLabel(Integer month) {
		if (month < Calendar.JANUARY || month > Calendar.DECEMBER) {
			throw new IllegalArgumentException("Hour invalid: " + month);
		}
		
		switch (month) {
		case (Calendar.JANUARY):
			return "Janeiro";
		case (Calendar.FEBRUARY):
			return "Fevereiro";
		case (Calendar.MARCH):
			return "Mar�o";
		case (Calendar.APRIL):
			return "Abril";
		case (Calendar.MAY):
			return "Maio";
		case (Calendar.JUNE):
			return "Junho";
		case (Calendar.JULY):
			return "Julho";
		case (Calendar.AUGUST):
			return "Agosto";
		case (Calendar.SEPTEMBER):
			return "Setembro";
		case (Calendar.OCTOBER):
			return "Outubro";
		case (Calendar.NOVEMBER):
			return "Novembro";
		case (Calendar.DECEMBER):
			return "Dezembro";
		default:
			return null;
		}
	}
}
