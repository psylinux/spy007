/*
 * ResultTable.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package data;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import domain.Measurement;
import domain.Result;

/**
 * Tabela Hash usada para armazenar os resultados.<br>
 * Cria resultados de acordo com as medidas coletadas e os armazena na ordem
 * correta.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 * @see Result
 * @see domain.Measurement
 */
public class ResultTable {

	/**
	 * Tabela usada para armazenar os resultados. As chaves da tabela s�o
	 * valores que seguem o delimitador estabelecido.
	 */
	private Map<Integer, Result> table;

	/**
	 * Delimitador da tabela. <br>
	 * Indica que campo da data deve ser analisada ao adicionar um valor �
	 * tabela. <br>
	 * Admite apenas os seguintes valores:
	 * <li> Calendar.HOUR_OF_DAY
	 * <li> Calendar.DAY_OF_WEEK
	 * <li> Calendar.DAY_OF_MONTH
	 * <li> Calendar.MONTH
	 */
	private int delimiter;

	/**
	 * Cria uma nova tabela usando o delimitador e a lista de medidas fornecidos
	 * como par�metro. <br>
	 * As medidas s�o agrupadas em resultados na tabela.
	 * 
	 * @param delimiter
	 *            Delimitador que indica o campo a ser analisado.
	 * @param measurements
	 *            Lista de medidas coletadas.
	 * @see domain.Result
	 * @see domain.Measurement
	 */
	public ResultTable(int delimiter, List<Measurement> measurements) {
		setDelimiter(delimiter);
		initTable(measurements);
	}

	/**
	 * Inicializa a tabela adicionando a lista de medidas fornecida como
	 * par�metro.
	 * 
	 * @param measurements
	 *            Medidas que ser�o usadas para criar os resultados adicionados
	 *            na tabela.
	 */
	private void initTable(List<Measurement> measurements) {
		table = new TreeMap<Integer, Result>();
		Iterator<Measurement> iteMeasuraments = measurements.iterator();

		while (iteMeasuraments.hasNext()) {
			Measurement measurement = iteMeasuraments.next();
			updateTable(measurement);
		}
	}

	/**
	 * Atualiza a tabela adicionando a medida fornecida. <br>
	 * Se n�o existir uma entrada para a chave correspondente, essa � criada.
	 * Caso contr�rio, o resultado equivalente � chave � atualizado.
	 * 
	 * @param measurement
	 *            Medida a ser adicionada � tabela.
	 */
	public void updateTable(Measurement measurement) {
		Calendar calendar = measurement.getCalendar();
		Integer key = calendar.get(delimiter);

		if (!table.keySet().contains(key)) {
			// Cria uma nova entrada na tabela
			Result res = new Result();
			res.setKey(key);
			res.setKeyPressed(measurement.getKeyPressed());
			res.setMousePressed(measurement.getMousePressed());
			res.setMinutes(1);

			table.put(key, res);
		} else {
			// Atualiza um valor j� existente
			Result value = table.get(key);

			long keyPressed = value.getKeyPressed();
			value.setKeyPressed(keyPressed + measurement.getKeyPressed());

			long mousePressed = value.getMousePressed();
			value.setMousePressed(mousePressed + measurement.getMousePressed());

			long minutes = value.getMinutes();
			value.setMinutes(minutes + 1);
		}
	}

	/**
	 * Configura o delimitador que ser� usado para indexar a tabela. Deve ser um
	 * dos seguintes valores:
	 * <li> Calendar.HOUR_OF_DAY
	 * <li> Calendar.DAY_OF_WEEK
	 * <li> Calendar.DAY_OF_MONTH
	 * <li> Calendar.MONTH
	 * 
	 * @param d
	 *            Delimitador
	 * @throws IllegalArgumentException
	 *             se não for fornecido um delimitador válido.
	 */
	private void setDelimiter(int d) {
		if (d != Calendar.HOUR_OF_DAY && d != Calendar.DAY_OF_WEEK
				&& d != Calendar.DAY_OF_MONTH && d != Calendar.MONTH) {
			throw new IllegalArgumentException("Delimiter invalid: " + d);
		}
		delimiter = d;
	}

	/**
	 * Obt�m o iterator para percorrer a tabela.
	 * 
	 * @return Iterator da tabela.
	 * @see java.util.Iterator
	 */
	public Iterator<Result> iterator() {
		return table.values().iterator();
	}

	/**
	 * Obt�m o tamanho da tabela.
	 * 
	 * @return Quantidade de entradas da tabela.
	 */
	public int size() {
		return table.size();
	}
}
