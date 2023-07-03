/*
 * XMLFileReader.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package data;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import util.CalendarUtil;
import domain.Measurement;
import domain.SpyProperties;

/**
 * Leitor de medidas armazenadas em arquivos XML. Obt�m as medidas relativas a
 * uma data-alvo.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 * @see data.SpyReader
 * @see domain.Measurement
 */
public class XMLFileReader implements SpyReader {

	/**
	 * Cria um novo leitor, tendo como diret�rio de trabalho o fornecido como
	 * par�metro. Os arquivos ser�o buscados apenas no diret�rio especificado.
	 * 
	 * @param dirName
	 *            Caminho do diret�rio.
	 * @throws IllegalArgumentException
	 *             se o par�metro n�o for um diret�rio.
	 */
	public XMLFileReader(String dirName) {
		File dir = new File(dirName);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dirName + " is not a directory");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.SpyReader#getByDay(java.util.Calendar)
	 */
	public List<Measurement> getByDay(Calendar targetTime) {
		return getMeasurements(getFileByCalendar(targetTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.SpyReader#getByWeek(java.util.Calendar)
	 */
	public List<Measurement> getByWeek(Calendar targetTime) {
		// Obtém as datas equivalentes à semana da data-alvo
		Calendar[] days = CalendarUtil.getCalendarsInWeek(targetTime);

		// Configura os arquivos equivalentes às datas
		File[] files = new File[days.length];
		for (int i = 0; i < files.length; i++) {
			files[i] = getFileByCalendar(days[i]);
		}

		return getMeasurements(files);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.SpyReader#getByMonth(java.util.Calendar)
	 */
	public List<Measurement> getByMonth(Calendar targetTime) {
		// Obtém as datas equivalentes ao mês da data-alvo
		Calendar[] days = CalendarUtil.getCalendarsInMonth(targetTime);

		// Configura os arquivos equivalentes às datas
		File[] files = new File[days.length];
		for (int i = 0; i < files.length; i++) {
			files[i] = getFileByCalendar(days[i]);
		}

		return getMeasurements(files);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.SpyReader#getByYear(java.util.Calendar)
	 */
	public List<Measurement> getByYear(Calendar targetTime) {
		// Obtém as datas equivalentes ao ano da data-alvo
		Calendar[] days = CalendarUtil.getCalendarsInYear(targetTime);

		// Configura os arquivos equivalentes às datas
		File[] files = new File[days.length];
		for (int i = 0; i < files.length; i++) {
			files[i] = getFileByCalendar(days[i]);
		}

		return getMeasurements(files);
	}

	/**
	 * Obt�m as medidas de um arquivo.
	 * 
	 * @param file
	 *            Arquivo contendo as medidas.
	 * @return Lista de medidas contidas no arquivo especificado.<br>
	 *         <code>null</code> se o par�metro for um arquivo inv�lido ou
	 *         inexistente.
	 */
	private List<Measurement> getMeasurements(File file) {
		XMLParser parser = new XMLParser(file);
		return parser.getMeasurements();
	}

	/**
	 * Obt�m as medidas de um conjunto de arquivos.
	 * 
	 * @param files
	 *            Arquivos contendo as medidas.
	 * @return Lista de medidas contidas nos arquivos especificados.<br>
	 *         <code>null</code> se todos os arquivos do conjunto forem
	 *         inv�lidos ou inexistentes.
	 */
	private List<Measurement> getMeasurements(File[] files) {
		List<Measurement> measurements = new ArrayList<Measurement>();

		for (File file : files) {
			XMLParser parser = new XMLParser(file);
			List<Measurement> measurementsInFile = parser.getMeasurements();
			if (measurementsInFile != null) {
				measurements.addAll(measurementsInFile);
			}
		}

		if (measurements.size() > 0) {
			return measurements;
		} else {
			return null;
		}
	}

	/**
	 * Obt�m a descri��o de um arquivo atrav�s de um calend�rio.<br>
	 * O arquivo especificado � do formato:
	 * <code>dir + File.separator() + yyyyMMdd.xml</code>, onde:<br>
	 * <li> <code>dir</code> - Diret�rio fornecido na constru��o
	 * <li> <code>yyyy</code> - Ano do calend�rio fornecido como par�metro.
	 * <li> <code>mm</code> - M�s do calend�rio fornecido como par�metro.
	 * <li> <code>dd</code> - Dia do calend�rio fornecido como par�metro.
	 * 
	 * @param calendar
	 * @return
	 * @see java.util.Calendar
	 * @see java.io.File#separator
	 */
	private File getFileByCalendar(Calendar calendar) {
		Date date = calendar.getTime();
		String format = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dirName = SpyProperties.USER_DIR_NAME;

		if (!dirName.endsWith(File.separator)) {
			dirName += File.separator;
		}

		String fileName = dirName + formatter.format(date) + ".xml";
		return new File(fileName);
	}
}
