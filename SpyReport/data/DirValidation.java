/*
 * DirValidation.java 
 * Versão 1.0
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
	 * Verifica se um diretório está vazio
	 * 
	 * @param dir
	 *            Diretório a ser analisado.
	 * @return <code>true</code> se for um diretório vazio<br>
	 *         <code>false</code> se o parâmetro for um arquivo ou se for um
	 *         diretório que contém algum conteúdo
	 */
	public static boolean isEmptyDir(File dir) {
		if(!dir.exists()) {
			return false;
		}
		
		// Verifica se é um arquivo
		if (dir.isFile()) {
			return false;
		}

		// Verifica se está vazio
		File files[] = dir.listFiles();
		if (files.length > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Verifica se um arquivo é válido<br>
	 * Um arquivo válido é um arquivo XML cujo nome segue o seguinte padrão:
	 * <code>yyyymmdd</code>, onde:
	 * <li> <code>yyyy</code> = ano
	 * <li> <code>mm</code> = mês
	 * <li> <code>dd</code> = dia<br>
	 * Além das condições acima, a tag raiz do XML deve ter o nome do usuário
	 * especificado.
	 * 
	 * @param file
	 *            Arquivo a ser analisado.
	 * @return Nome do usuário que consta na tag principal do arquivo.<br>
	 *         <code>null</code> se for um arquivo inválido.
	 */
	public static String isValidFile(File file) {
		// Verifica se é um arquivo XML
		String fileName = file.getName().toLowerCase();
		if (!fileName.endsWith(".xml")) {
			return null;
		}

		// Verifica se segue o padrão de nome
		Pattern pattern = Pattern.compile("\\d{4}\\d{2}\\d{2}");
		String correctFileName = file.getName().substring(0,
				file.getName().indexOf(".xml"));
		Matcher search = pattern.matcher(correctFileName);
		if (!search.matches()) {
			return null;
		}

		// Verifica se contém o nome do usuário na tag principal
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
	 * Verifica se é um diretório válido.<br>
	 * Um diretório válido deve conter subdiretórios de usuários com cada
	 * subdiretório contendo pelo menos um arquivo válido
	 * 
	 * @param dir
	 *            Diretório a ser analisado.
	 * @return Uma lista de nomes de usuários contidos no diretório.<br>
	 *         <code>null</code> se for um diretório inválido ou se não
	 *         existir nenhum arquivo de usuário no diretório.
	 */
	public static List<String> isValidDir(File dir) {
		List<String> users = new ArrayList<String>();

		// Verifica se é um arquivo
		if (dir.isFile()) {
			return null;
		}

		// Verifica se contém subdiretórios
		if (!containsDir(dir)) {
			return null;
		}

		// Obtém os usuários no diretório
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
	 * Obtém o usuário cujos arquivos estão no diretório fornecido.
	 * 
	 * @param dir
	 *            Diretório a ser analisado.
	 * @return Usuário cujos arquivos estão no diretório<br>
	 *         <code>null</code> se for um diretório inválido ou se não
	 *         existir nenhum arquivo válido.
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
	 * Verifica se um diretório contém subdiretórios.
	 * 
	 * @param dir
	 *            Diretório a ser analisado.
	 * @return <code>true</code> se o diretório contém subdiretórios.
	 *         <code>false</code> se o diretório não contém subdiretórios
	 *         ou se não for um diretório
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
