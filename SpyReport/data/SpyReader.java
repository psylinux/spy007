/*
 * SpyReader.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package data;

import java.util.Calendar;
import java.util.List;

import domain.Measurement;

/**
 * Leitor de medidas. Obtém as medidas relativas a uma data-alvo.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 * @see domain.Measurement
 */
public interface SpyReader {

	/**
	 * Obtém as medidas para um dia.
	 * 
	 * @param targetTime
	 *            Dia cujas medidas devem ser obtidas.
	 * @return Medidas coletadas no dia especificado.<br>
	 *         <code>null</code> se não foi coletada nenhuma medida para esse
	 *         perí­odo.
	 */
	public List<Measurement> getByDay(Calendar targetTime);

	/**
	 * Obtém as medidas para uma semana.
	 * 
	 * @param targetTime
	 *            Dia da semana cujas medidas devem ser obtidas.
	 * @return Medidas coletadas na semana em que se encontra o dia
	 *         especificado.<br>
	 *         <code>null</code> se não foi coletada nenhuma medida para esse
	 *         perí­odo.
	 */
	public List<Measurement> getByWeek(Calendar targetTime);

	/**
	 * Obtém as medidas para um mês.
	 * 
	 * @param targetTime
	 *            Dia do mês cujas medidas devem ser obtidas.
	 * @return Medidas coletadas no mês em que se encontra o dia especificado.<br>
	 *         <code>null</code> se não foi coletada nenhuma medida para esse
	 *         perí­odo.
	 */
	public List<Measurement> getByMonth(Calendar targetTime);

	/**
	 * Obtém as medidas para um ano.
	 * 
	 * @param targetTime
	 *            Dia do ano cujas medidas devem ser obtidas.
	 * @return Medidas coletadas no ano em que se encontra o dia especificado.<br>
	 *         <code>null</code> se não foi coletada nenhuma medida para esse
	 *         período.
	 */
	public List<Measurement> getByYear(Calendar targetTime);
}
