/*
 * ViewBuilder.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package controller;

import java.util.Calendar;

import javax.swing.JPanel;

/**
 * Gerador de visualizações das estatí­sticas.<br>
 * 
 * As visualizações são retornadas em um <code>JPanel</code>.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public abstract class ViewBuilder {

	/**
	 * Indicador de perÃ­odo diário.
	 */
	public static final String DAY = "Day";

	/**
	 * Indicador de perí­odo semanal.
	 */
	public static final String WEEK = "Week";

	/**
	 * Indicador de perí­odo mensal.
	 */
	public static final String MONTH = "Month";

	/**
	 * Indicador de perí­odo anual.
	 */
	public static final String YEAR = "Year";

	/**
	 * Cria uma visualização diária. <br>
	 * 
	 * As estatí­sticas são geradas tendo como referência a data fornecida como
	 * parâmetro e como delimitador as horas do dia. São apresentadas as horas
	 * compreendidas entre a hora da primeira leitura e a hora da última leitura
	 * referente à consulta.
	 * 
	 * @param targetTime
	 *            data-alvo para geração da visualização.
	 * @return um painel contendo a visualização desejada <br>
	 *         <code>null</code> se não for encontrado um arquivo válido
	 *         referente a esse perí­odo
	 */
	public abstract JPanel makeDailyView(Calendar targetTime);

	/**
	 * Cria uma visualização semanal. <br>
	 * 
	 * As estatí­sticas são geradas tendo como referência a data fornecida como
	 * parâmetro e como delimitador os dias da semana.
	 * 
	 * @param targetTime
	 *            data-alvo para geração da visualização.
	 * @return um painel contendo a visualização desejada <br>
	 *         <code>null</code> se não for encontrado nenhum arquivo válido
	 *         referente a esse perí­odo
	 */
	public abstract JPanel makeWeeklyView(Calendar targetTime);

	/**
	 * Cria uma visualização mensal. <br>
	 * 
	 * As estatísticas são geradas tendo como referência a data fornecida como
	 * parâmetro e como delimitador os dias do mês.
	 * 
	 * @param targetTime
	 *            data-alvo para geração da visualização.
	 * @return um painel contendo a visualização desejada <br>
	 *         <code>null</code> se nÃ£o for encontrado nenhum arquivo válido
	 *         referente a esse perí­odo
	 */
	public abstract JPanel makeMonthlyView(Calendar targetTime);

	/**
	 * Cria uma visualização anual. <br>
	 * 
	 * As estatí­sticas são geradas tendo como referência a data fornecida como
	 * parâmetro e como delimitador os dias do ano.
	 * 
	 * @param targetTime
	 *            data-alvo para geração da visualização.
	 * @return um painel contendo a visualização desejada <br>
	 *         <code>null</code> se não for encontrado nenhum arquivo válido
	 *         referente a esse perí­odo
	 */
	public abstract JPanel makeYearlyView(Calendar targetTime);

	/**
	 * Cria uma label para visualizações diárias com a hora fornecida como
	 * parâmetro e a próxima hora. Ex.:<br>
	 * <code>createDayLabel(8) = 8:00-9:00</code><br>
	 * <code>createDayLabel(23) = 23:00-0:00</code>
	 * 
	 * @param hour
	 *            hora da label
	 * @return label referente à hora fornecida e a hora posterior
	 * @throws IllegalArgumentException
	 *             se não for fornecida uma hora válida
	 */
	public String createDayLabel(Integer hour) {
		if (hour < 0 || hour > 24) {
			throw new IllegalArgumentException("Hour invalid: " + hour);
		}

		return (hour + ":00-" + ((hour + 1) % 23) + ":00");
	}

	/**
	 * Cria uma label para visualizações semanais com o dia da semana (de acordo
	 * com <code>java.util.Calendar</code>) fornecido como parâmetro
	 * 
	 * @param dayOfWeek
	 *            dia da semana da label
	 * @return label referente ao dia da semana fornecido
	 * @throws IllegalArgumentException
	 *             se não for fornecida um dia da semana válido
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
			return "Terça";
		case (Calendar.WEDNESDAY):
			return "Quarta";
		case (Calendar.THURSDAY):
			return "Quinta";
		case (Calendar.FRIDAY):
			return "Sexta";
		case (Calendar.SATURDAY):
			return "Sábado";
		default:
			return null;
		}
	}

	/**
	 * Cria uma label para visualizações mensais com o dia do mês fornecido como
	 * parâmetro
	 * 
	 * @param dayOfMonth
	 *            dia do mês da label
	 * @return label referente ao dia do mês fornecido
	 * @throws IllegalArgumentException
	 *             se nÃ£o for fornecida um dia de mês válido
	 */
	public String createMonthLabel(Integer dayOfMonth) {
		if (dayOfMonth < 1 || dayOfMonth > 31) {
			throw new IllegalArgumentException("Hour invalid: " + dayOfMonth);
		}

		return String.valueOf(dayOfMonth);
	}

	/**
	 * Cria uma label para visualizações anuais com o mês (de acordo
	 * com <code>java.util.Calendar</code>) fornecido como parâmetro
	 * 
	 * @param dia da semana
	 *            mês da label
	 * @return label referente ao mês fornecido.
	 * @throws IllegalArgumentException
	 *             se não for fornecida um mês válido
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
			return "Março";
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
