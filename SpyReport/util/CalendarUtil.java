/*
 * CalendarUtil.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class CalendarUtil {

	/**
	 * Obt�m os calend�rios contidos na semana equivalente � data fornecida.
	 * 
	 * @param targetTime
	 *            Dara para qual se deseja obter semana
	 * @return Calend�rio dos dias da semana equivalente
	 */
	public static Calendar[] getCalendarsInWeek(Calendar targetTime) {

		switch (targetTime.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return getCalendarWeekMon(targetTime);
		case Calendar.TUESDAY:
			return getCalendarWeekTue(targetTime);
		case Calendar.WEDNESDAY:
			return getCalendarWeekWed(targetTime);
		case Calendar.THURSDAY:
			return getCalendarWeekThu(targetTime);
		case Calendar.FRIDAY:
			return getCalendarWeekFri(targetTime);
		case Calendar.SATURDAY:
			return getCalendarWeekSat(targetTime);
		case Calendar.SUNDAY:
			return getCalendarWeekSun(targetTime);
		default:
			return null;
		}
	}

	/**
	 * Obt�m os calend�rios contidos no m�s equivalente � data fornecida.
	 * 
	 * @param targetTime
	 *            Dara para qual se deseja obter m�s
	 * @return Calend�rio dos dias do m�s equivalente
	 */
	public static Calendar[] getCalendarsInMonth(Calendar targetTime) {
		List<Calendar> list = new ArrayList<Calendar>();

		int month = targetTime.get(Calendar.MONTH);
		int year = targetTime.get(Calendar.YEAR);
		int days = daysInMonth(month, year);

		for (int day = 1; day <= days; day++) {
			Calendar calendar = (Calendar) targetTime.clone();
			calendar.set(Calendar.DAY_OF_MONTH, day);
			list.add(calendar);
		}

		return listToArray(list);
	}

	/**
	 * Calcula a quantidade de dias em um dado m�s
	 * 
	 * @param month
	 *            M�s que se deseja saber a quantidade de dias
	 * @param year
	 *            Ano do m�s que se deseja saber a quantidade de dias
	 * @return Quantidade de dias do m�s fornecido
	 */
	public static int daysInMonth(int month, int year) {
		int qtdeDays = -1;
		GregorianCalendar calendar = new GregorianCalendar();

		switch (month) {
		case Calendar.JANUARY:
			qtdeDays = 31;
			break;
		case Calendar.FEBRUARY:
			if (calendar.isLeapYear(year)) {
				qtdeDays = 29;
			} else {
				qtdeDays = 28;
			}
			break;
		case Calendar.MARCH:
			qtdeDays = 31;
			break;
		case Calendar.APRIL:
			qtdeDays = 30;
			break;
		case Calendar.MAY:
			qtdeDays = 31;
			break;
		case Calendar.JUNE:
			qtdeDays = 30;
			break;
		case Calendar.JULY:
			qtdeDays = 31;
			break;
		case Calendar.AUGUST:
			qtdeDays = 31;
			break;
		case Calendar.SEPTEMBER:
			qtdeDays = 30;
			break;
		case Calendar.OCTOBER:
			qtdeDays = 31;
			break;
		case Calendar.NOVEMBER:
			qtdeDays = 30;
			break;
		case Calendar.DECEMBER:
			qtdeDays = 31;
		}
		return qtdeDays;
	}

	/**
	 * Obt�m os calend�rios contidos no ano equivalente � data fornecida.
	 * 
	 * @param targetTime
	 *            Dara para qual se deseja obter ano
	 * @return Calend�rio dos dias do ano equivalente
	 */
	public static Calendar[] getCalendarsInYear(Calendar targetTime) {
		List<Calendar> list = new ArrayList<Calendar>();

		int year = targetTime.get(Calendar.YEAR);
		for (int month = 0; month < 12; month++) {
			int days = daysInMonth(month, year);
			for (int day = 1; day <= days; day++) {
				Calendar calendar = (Calendar) targetTime.clone();
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				list.add(calendar);
			}
		}

		return listToArray(list);
	}

	/**
	 * Converte uma lista em um array
	 * 
	 * @param list
	 *            Lista a ser convertida
	 * @return Array equivalente � lista fornecida
	 */
	private static Calendar[] listToArray(List<Calendar> list) {
		if (list.size() == 0) {
			return null;
		}

		Calendar[] array = new Calendar[list.size()];
		Iterator<Calendar> iterator = list.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			array[index] = iterator.next();
			index++;
		}

		return array;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.SUNDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekSun(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = targetTime;
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[1].add(Calendar.DAY_OF_WEEK, +1);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, +2);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, +3);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, +4);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, +5);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +6);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.MONDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekMon(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = targetTime;
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -1);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, +1);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, +2);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, +3);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, +4);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +5);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.TUESDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekTue(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = targetTime;
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -2);
		diasSemana[1].add(Calendar.DAY_OF_WEEK, -1);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, +1);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, +2);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, +3);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +4);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.WEDNESDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekWed(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = targetTime;
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -3);
		diasSemana[1].add(Calendar.DAY_OF_WEEK, -2);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, -1);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, +1);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, +2);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +3);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.THURSDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekThu(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = targetTime;
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -4);
		diasSemana[1].add(Calendar.DAY_OF_WEEK, -3);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, -2);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, -1);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, +1);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +2);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.FRIDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekFri(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = targetTime;
		diasSemana[6] = (Calendar) targetTime.clone();

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -5);
		diasSemana[1].add(Calendar.DAY_OF_WEEK, -4);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, -3);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, -2);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, -1);
		diasSemana[6].add(Calendar.DAY_OF_WEEK, +1);

		return diasSemana;
	}

	/**
	 * Obt�m calend�rios quando o dia da semana da data-alvo � <code>Calendar.SATURDAY</code>
	 * 
	 * @param targetTime
	 *            Calend�rio de refer�ncia
	 * @return Conjunto de calend�rios da semana equivalente
	 */
	private static Calendar[] getCalendarWeekSat(Calendar targetTime) {
		Calendar[] diasSemana = new Calendar[7];

		diasSemana[0] = (Calendar) targetTime.clone();
		diasSemana[1] = (Calendar) targetTime.clone();
		diasSemana[2] = (Calendar) targetTime.clone();
		diasSemana[3] = (Calendar) targetTime.clone();
		diasSemana[4] = (Calendar) targetTime.clone();
		diasSemana[5] = (Calendar) targetTime.clone();
		diasSemana[6] = targetTime;

		diasSemana[0].add(Calendar.DAY_OF_WEEK, -6);
		diasSemana[1].add(Calendar.DAY_OF_WEEK, -5);
		diasSemana[2].add(Calendar.DAY_OF_WEEK, -4);
		diasSemana[3].add(Calendar.DAY_OF_WEEK, -3);
		diasSemana[4].add(Calendar.DAY_OF_WEEK, -2);
		diasSemana[5].add(Calendar.DAY_OF_WEEK, -1);

		return diasSemana;
	}
}
