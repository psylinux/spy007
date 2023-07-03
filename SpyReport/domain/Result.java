/*
 * Result.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package domain;

/**
 * Resultado obtido pela análise de um conjunto de medidas. Encapsula os valores
 * das medidas.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 * @see domain.Measurement
 */
public class Result {

	/**
	 * Chave referente ao resultado. Hora em análises diárias, dia da semana em
	 * análises semanais, etc.
	 */
	private int key;

	/**
	 * Quantidade de ações em teclado durante o perí­odo.
	 */
	private long keyPressed;

	/**
	 * Quantidade de ações em mouse durante o perí­odo.
	 */
	private long mousePressed;

	/**
	 * Quantidade de minutos em que foi feita a coleta de dados.
	 */
	private long minutes;

	/**
	 * @return Chave referente ao resultado
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key
	 *            Chave referente ao resultado
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return Quantidade de ações em teclado durante o perí­odo.
	 */
	public long getKeyPressed() {
		return keyPressed;
	}

	/**
	 * @param keyPressed
	 *            Quantidade de ações em teclado durante o perí­odo.
	 */
	public void setKeyPressed(long keyPressed) {
		this.keyPressed = keyPressed;
	}

	/**
	 * @return Quantidade de ações em mouse durante o perí­odo.
	 */
	public long getMousePressed() {
		return mousePressed;
	}

	/**
	 * @param mousePressed
	 *            Quantidade de ações em mouse durante o perí­odo.
	 */
	public void setMousePressed(long mousePressed) {
		this.mousePressed = mousePressed;
	}

	/**
	 * @return Quantidade de minutos em que foi feita a coleta de dados.
	 */
	public long getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes
	 *            Quantidade de minutos em que foi feita a coleta de dados.
	 */
	public void setMinutes(long minutes) {
		this.minutes = minutes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Result: key=" + getKey() + ", minutes=" + getMinutes();
	}
}
