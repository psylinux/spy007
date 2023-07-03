/*
 * Measurement.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Medida coletada.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class Measurement {

	/**
	 * Quantidade de a��es em teclado durante o per�odo
	 */
	private long keyPressed;

	/**
	 * Quantidade de a��es em mouse durante o per�odo
	 */
	private long mousePressed;

	/**
	 * Data da coleta da medida
	 */
	private Calendar calendar;

	/**
	 * Cria uma nova medida configurando como data da medida o calend�rio atual
	 */
	public Measurement() {
		setCalendar(new GregorianCalendar());
	}

	/**
	 * @return Quantidade de a��es em teclado durante o per�odo
	 */
	public long getKeyPressed() {
		return keyPressed;
	}

	/**
	 * @param keyPressed
	 *            Quantidade de a��es em teclado durante o per�odo
	 */
	public void setKeyPressed(long keyPressed) {
		this.keyPressed = keyPressed;
	}

	/**
	 * @return Quantidade de a��es em mouse durante o per�odo
	 */
	public long getMousePressed() {
		return mousePressed;
	}

	/**
	 * @param mousePressed
	 *            Quantidade de a��es em mouse durante o per�odo
	 */
	public void setMousePressed(long mousePressed) {
		this.mousePressed = mousePressed;
	}

	/**
	 * @return Data da coleta da medida
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            Data da coleta da medida
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * Configura a data da coleta da medida
	 * 
	 * @param day
	 *            dia da coleta
	 * @param month
	 *            m�s da coleta
	 * @param year
	 *            ano da coleta
	 * @param hour
	 *            hora da coleta
	 * @param minute
	 *            minuto da coleta
	 */
	public void setCalendar(int day, int month, int year, int hour, int minute) {
		calendar = new GregorianCalendar(year, month, day, hour, minute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "/"
				+ calendar.get(Calendar.MONTH) + "/"
				+ calendar.get(Calendar.YEAR) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE);

		return "Measurement: " + date + " keyPressed=" + keyPressed
				+ " mousePressed=" + mousePressed;
	}
}
