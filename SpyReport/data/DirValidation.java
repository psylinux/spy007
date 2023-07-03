/*
 * DirValidation.java 
 * Vers�o 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import domain.SpyProperties;

public class DirValidation {

	/**
	 * Verifica se um diret�rio est� vazio
	 * 
	 * @param dir
	 *            Diret�rio a ser analisado.
	 * @return <code>true</code> se for um diret�rio vazio<br>
	 *         <code>false</code> se o par�metro for um arquivo ou se for um
	 *         diret�rio que cont�m algum conte�do
	 */
	public static boolean isEmptyDir(File dir) {
		if(!dir.exists()) {
			return false;
		}
		
		// Verifica se � um arquivo
		if (dir.isFile()) {
			return false;
		}

		// Verifica se est� vazio
		File files[] = dir.listFiles();
		if (files.length > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Verifica se um arquivo � v�lido<br>
	 * Um arquivo v�lido � um arquivo XML cujo nome segue o seguinte padr�o:
	 * <code>yyyymmdd</code>, onde:
	 * <li> <code>yyyy</code> = ano
	 * <li> <code>mm</code> = m�s
	 * <li> <code>dd</code> = dia<br>
	 * Al�m das condi��es acima, a tag raiz do XML deve ter o nome do usu�rio
	 * especificado.
	 * 
	 * @param file
	 *            Arquivo a ser analisado.
	 * @return Nome do usu�rio que consta na tag principal do arquivo.<br>
	 *         <code>null</code> se for um arquivo inv�lido.
	 */
	public static String isValidFile(File file) {
		// Verifica se � um arquivo XML
		String fileName = file.getName().toLowerCase();
		if (!fileName.endsWith(".xml")) {
			return null;
		}

		// Verifica se segue o padr�o de nome
		Pattern pattern = Pattern.compile("\\d{4}\\d{2}\\d{2}");
		String correctFileName = file.getName().substring(0,
				file.getName().indexOf(".xml"));
		Matcher search = pattern.matcher(correctFileName);
		if (!search.matches()) {
			return null;
		}

		// Verifica se cont�m o nome do usu�rio na tag principal
		SAXBuilder sb = new SAXBuilder();
		Document document;
		try {
			document = sb.build(file);
			Element root = document.getRootElement();
			String user = root
					.getAttributeValue(SpyProperties.USER_ATTIBUTE_NAME);
			return user;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Verifica se � um diret�rio v�lido.<br>
	 * Um diret�rio v�lido deve conter subdiret�rios de usu�rios com cada
	 * subdiret�rio contendo pelo menos um arquivo v�lido
	 * 
	 * @param dir
	 *            Diret�rio a ser analisado.
	 * @return Uma lista de nomes de usu�rios contidos no diret�rio.<br>
	 *         <code>null</code> se for um diret�rio inv�lido ou se n�o
	 *         existir nenhum arquivo de usu�rio no diret�rio.
	 */
	public static List<String> isValidDir(File dir) {
		List<String> users = new ArrayList<String>();

		// Verifica se � um arquivo
		if (dir.isFile()) {
			return null;
		}

		// Verifica se cont�m subdiret�rios
		if (!containsDir(dir)) {
			return null;
		}

		// Obt�m os usu�rios no diret�rio
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				String user = userInDir(file);
				if (user != null && user.equals(file.getName())) {
					users.add(user);
				}
			}
		}

		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}

	/**
	 * Obt�m o usu�rio cujos arquivos est�o no diret�rio fornecido.
	 * 
	 * @param dir
	 *            Diret�rio a ser analisado.
	 * @return Usu�rio cujos arquivos est�o no diret�rio<br>
	 *         <code>null</code> se for um diret�rio inv�lido ou se n�o
	 *         existir nenhum arquivo v�lido.
	 */
	private static String userInDir(File dir) {
		File[] files = dir.listFiles();
		for (File file : files) {
			String user = isValidFile(file);
			if (user != null) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Verifica se um diret�rio cont�m subdiret�rios.
	 * 
	 * @param dir
	 *            Diret�rio a ser analisado.
	 * @return <code>true</code> se o diret�rio cont�m subdiret�rios.
	 *         <code>false</code> se o diret�rio n�o cont�m subdiret�rios
	 *         ou se n�o for um diret�rio
	 */
	private static boolean containsDir(File dir) {
		if (!dir.isDirectory()) {
			return false;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				return true;
			}
		}
		return false;
	}
}
