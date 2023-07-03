/*
 * XMLParser.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

import domain.Measurement;
import domain.SpyProperties;

/**
 * Parser de um arquivo XML. Obtém medidas de um arquivo.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 * @see domain.Measurement
 */
class XMLParser {

	/**
	 * Arquivo que é lido pelo parser.
	 */
	private File file;

	/**
	 * Lista de medidas obtidas do arquivo.
	 */
	private List<Measurement> measurements;

	/**
	 * Cria um parser para o arquivo fornecido como parâmetro.
	 * 
	 * @param file
	 *            Arquivo que será analisado.
	 */
	public XMLParser(File file) {
		this.file = file;
	}

	/**
	 * Obtém as medidas de um arquivo.
	 * 
	 * @throws IOException
	 *             se for um arquivo inválido ou inexistente.
	 */
	@SuppressWarnings("unchecked")
	private void parse() throws IOException {
		measurements = new ArrayList<Measurement>();

		SAXBuilder sb = new SAXBuilder();
		Document document;
		try {
			document = sb.build(file);
			Element root = document.getRootElement();
			List<Element> elements = root.getContent();

			Iterator<Element> i = elements.iterator();
			while (i.hasNext()) {
				Object next = i.next();
				// Se for um comentário no arquivo
				if (next instanceof Comment || next instanceof Text) {
					continue;
				} else {
					Element element = (Element) next;
					measurements.add(convertElement(element));
				}

			}
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new IOException("Invalid file: " + file);
		}
	}

	/**
	 * Converte um elemento em uma medida.
	 * 
	 * @param element
	 *            Elemento a ser convertido.
	 * @return Medida equivalente ao elemento fornecido.
	 * @see org.jdom.Element
	 * @see domain.Measurement
	 */
	private Measurement convertElement(Element element) {
		Measurement measurement = new Measurement();
		long key = Long.parseLong(element
				.getAttributeValue(SpyProperties.KEY_ATTRIBUTE_NAME));
		long mouse = Long.parseLong(element
				.getAttributeValue(SpyProperties.MOUSE_ATTRIBUTE_NAME));
		int day = Integer.parseInt(element
				.getAttributeValue(SpyProperties.DAY_ATTRIBUTE_NAME));
		int month = Integer.parseInt(element
				.getAttributeValue(SpyProperties.MONTH_ATTRIBUTE_NAME)) - 1;
		int year = Integer.parseInt(element
				.getAttributeValue(SpyProperties.YEAR_ATTRIBUTE_NAME));
		int hour = Integer.parseInt(element
				.getAttributeValue(SpyProperties.HOUR_ATTRIBUTE_NAME));
		int minute = Integer.parseInt(element
				.getAttributeValue(SpyProperties.MINUTE_ATTRIBUTE_NAME));

		measurement.setKeyPressed(key);
		measurement.setMousePressed(mouse);
		measurement.setCalendar(day, month, year, hour, minute);

		return measurement;
	}

	/**
	 * Obtém as medidas contidas no arquivo.
	 * 
	 * @return Lista de medidas do arquivo.<br>
	 *         <code>null</code> se for um arquivo inválido ou inexistente.
	 */
	public List<Measurement> getMeasurements() {
		// se é a primeira vez que é feito o parser
		if (measurements == null) {
			try {
				parse();
			} catch (IOException e) {
				return null;
			}
		}

		return measurements;
	}
}
