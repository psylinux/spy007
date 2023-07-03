/*
 * BarChart.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package util;

import java.awt.Color;
import java.io.File;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Utilitário para geração de gráficos de barra.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class BarChart {

	/**
	 * Gráfico de barras.
	 */
	private JFreeChart chart;

	/**
	 * Conjunto de valores do gráfico
	 */
	private DefaultCategoryDataset dataSet;

	/**
	 * Cria um novo gráfico de barras
	 * 
	 * @param chartTitle
	 *            tí­tulo do gráfico
	 * @param axisXTitle
	 *            título do eixo X
	 * @param axisYTitle
	 *            tÃítulo do eixo Y
	 * @param is3D
	 *            se é um gráfico 3D
	 */
	public BarChart(String chartTitle, String axisXTitle, String axisYTitle,
			boolean is3D) {
		dataSet = new DefaultCategoryDataset();

		if (is3D) {
			makeChart3D(chartTitle, axisXTitle, axisYTitle);
		} else {
			makeChart2D(chartTitle, axisXTitle, axisYTitle);
		}
	}

	/**
	 * Salva o gráfico como um arquivo PNG
	 * 
	 * @param filename
	 *            nome do arquivo a ser gerado
	 */
	public void saveAsPNG(String filename) {
		try {
			ChartUtilities.saveChartAsPNG(new File(filename), chart, 800, 600);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Obtém o gráfico como um painel
	 * 
	 * @return Painel representando o gráfico
	 */
	public JPanel getAsPanel() {
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}

	/**
	 * Cria uma instância do gráfico 3D sem nenhum valor plotado
	 * 
	 * @param title
	 *            tí­tulo do gráfico
	 * @param axisXTitle
	 *            tí­tulo do eixo X
	 * @param axisYTitle
	 *            tí­tulo do eixo Y
	 */
	private void makeChart3D(String title, String axisXTitle, String axisYTitle) {
		chart = ChartFactory.createBarChart3D(title, axisXTitle, axisYTitle,
				dataSet, PlotOrientation.VERTICAL, true, true, false);
		chart.setBorderVisible(true);
		chart.setBorderPaint(Color.black);
		CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	}

	/**
	 * Cria uma instância do gráfico 2D sem nenhum valor plotado
	 * 
	 * @param title
	 *            tí­tulo do gráfico
	 * @param axisXTitle
	 *            tí­tulo do eixo X
	 * @param axisYTitle
	 *            tí­tulo do eixo Y
	 */
	private void makeChart2D(String title, String axisXTitle, String axisYTitle) {
		chart = ChartFactory.createBarChart(title, axisXTitle, axisYTitle,
				dataSet, PlotOrientation.VERTICAL, true, true, false);
		chart.setBorderVisible(true);
		chart.setBorderPaint(Color.black);
		CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	}

	/**
	 * Plota um valor no gráfico
	 * 
	 * @param y
	 *            valor no eixo Y
	 * @param categoryName
	 *            nome da categoria a que se refere o valor plotado
	 * @param x
	 *            valor no eixo X
	 */
	@SuppressWarnings("unchecked")
	public void addItem(Number y, Comparable categoryName, Comparable x) {
		dataSet.setValue(y, categoryName, x);
	}
}
