/*
 * SpyReport.java 
 * Versão 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import util.CalendarComboBox;
import util.WindowUtilities;

import com.tomtessier.scrollabledesktop.JScrollableDesktopPane;

import controller.ChartBuilder;
import controller.ReportBuilder;
import controller.ViewBuilder;
import data.DirValidation;
import domain.SpyProperties;

/**
 * Interface principal da aplicação.
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class SpyReport extends JFrame {

	public static final String CONFIG_FILE = "spyreport.config";
	
	private static final long serialVersionUID = 1093931482774188782L;

	private JScrollableDesktopPane desktop;
	private JPanel mainPanel;
	private JButton makeChartButton;
	private JTextField dirField;
	private JFileChooser fc;
	private JButton searchButton;
	private JLabel periodLabel;
	private JComboBox intervalCombo;
	private JLabel dirLabel;
	private JSeparator jSeparator2;
	private JLabel dateLabel;
	private JLabel userLabel;
	private JComboBox userCombo;
	private JSeparator jSeparator1;
	private JButton makeReportButton;
	private JComboBox ccb;

	public static void main(String[] args) {
		SpyReport mainFrame = new SpyReport();
		mainFrame.init();
	}

	/**
	 * Cria uma nova janela da aplicação.
	 */
	public SpyReport() {
		super();
	}

	/**
	 * Inicializa a GUI.
	 */
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle(SpyProperties.MAIN_APPLICATION_TITLE);
		setResizable(true);

		Toolkit kit = getToolkit();
		setIconImage(kit.getImage("icon/icon.png"));

		WindowUtilities.setNativeLookAndFeel();
		WindowUtilities.setLanguage();

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(384, 204));

		makeChartButton = new JButton();
		mainPanel.add(makeChartButton);
		makeChartButton.setText(SpyProperties.MAKE_CHART_BUTTON_TEXT);
		makeChartButton.setBounds(300, 75, 153, 22);
		makeChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				makeChart();
			}
		});

		makeReportButton = new JButton();
		mainPanel.add(makeReportButton);
		makeReportButton.setText(SpyProperties.MAKE_REPORT_BUTTON_TEXT);
		makeReportButton.setBounds(300, 115, 153, 22);
		makeReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				makeReport();
			}
		});

		jSeparator1 = new JSeparator();
		mainPanel.add(jSeparator1);
		jSeparator1.setBounds(0, 202, 2024, 10);

		userCombo = new JComboBox();
		mainPanel.add(userCombo);
		userCombo.setBounds(115, 75, 127, 23);
		userCombo.setBackground(Color.WHITE);

		userLabel = new JLabel();
		mainPanel.add(userLabel);
		userLabel.setText(SpyProperties.USER_LABEL);
		userLabel.setBounds(15, 80, 80, 16);

		dateLabel = new JLabel();
		mainPanel.add(dateLabel);
		dateLabel.setText(SpyProperties.DATE_LABEL);
		dateLabel.setBounds(15, 160, 80, 16);

		jSeparator2 = new JSeparator();
		mainPanel.add(jSeparator2);
		jSeparator2.setBounds(-2, 63, 769, 1);

		ccb = new CalendarComboBox(true);
		mainPanel.add(ccb);
		ccb.setBounds(115, 155, 94, 23);
		ccb.setBackground(Color.WHITE);

		dirField = new JTextField();
		mainPanel.add(dirField);
		dirField.setBounds(9, 29, 660, 23);
		dirField.setEditable(false);
		dirField.setVerifyInputWhenFocusTarget(false);
		dirField.setBackground(Color.WHITE);

		dirLabel = new JLabel();
		mainPanel.add(dirLabel);
		dirLabel.setText(SpyProperties.DIR_LABEL);
		dirLabel.setBounds(9, 7, 258, 16);

		ComboBoxModel intervaloComboBoxModel = new DefaultComboBoxModel(
				new String[] { SpyProperties.DAILY_OPTION_STRING,
						SpyProperties.WEEKLY_OPTION_STRING,
						SpyProperties.MONTHLY_OPTION_STRING,
						SpyProperties.YEARLY_OPTION_STRING });
		intervalCombo = new JComboBox();
		mainPanel.add(intervalCombo);
		intervalCombo.setModel(intervaloComboBoxModel);
		intervalCombo.setBounds(115, 115, 100, 23);
		intervalCombo.setBackground(Color.WHITE);

		periodLabel = new JLabel();
		mainPanel.add(periodLabel);
		periodLabel.setText(SpyProperties.PERIOD_STRING);
		periodLabel.setBounds(15, 120, 80, 16);

		searchButton = new JButton();
		mainPanel.add(searchButton);
		searchButton.setText(SpyProperties.SEARCH_BUTTON_TEXT);
		searchButton.setBounds(680, 29, 87, 23);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				List<String> users = findDir(null, false);
								
				dirField.setText(SpyProperties.DIR_NAME);
				ComboBoxModel usuarioModel = new DefaultComboBoxModel(users.toArray());
				userCombo.setModel(usuarioModel);
				
				try {
					Properties props = new Properties();
					props.load(new FileInputStream(CONFIG_FILE));
					props.setProperty("dir", SpyProperties.DIR_NAME);
					FileOutputStream out;

					out = new FileOutputStream(CONFIG_FILE);
					props
							.store(
									out,
									" Arquivo de configuração\n"
											+ "# \n"
											+ "# dir = Diretório de usuários\n"
											+ "# language = Idioma utilizado. Deve existir um arquivo com esse nome em /lang. Ex.: /lang/pt-br.lg");
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		ImageIcon image = new ImageIcon("icon/spy.gif");
		JLabel icon = new JLabel(image);
		icon.setBounds(500, 75, image.getIconWidth(), image.getIconHeight());
		mainPanel.add(icon);

		desktop = new JScrollableDesktopPane();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		getContentPane().add(desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setSize(800, 600);
		setLocationRelativeTo(null);
	}

	/**
	 * Inicializa a aplicação solicitando a escolha do diretório de usuários.
	 */
	public void init() {
		// Carrega as propriedades
		Properties props = new Properties();
		String fileLanguage = null;
		String dirUser = null;
		try {
			props.load(new FileInputStream(CONFIG_FILE));
			fileLanguage = props.getProperty("language");
			dirUser = props.getProperty("dir");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		File properties = new File("lang" + File.separator
				+ fileLanguage.trim() + ".lg");
		
		if (properties.exists()) {
			SpyProperties.loadProperties(properties);
		} 

		initGUI();
		
		List<String> users = findDir(dirUser, true);
		
		if (users.size() > 0) {
			dirField.setText(SpyProperties.DIR_NAME);
			ComboBoxModel usuarioModel = new DefaultComboBoxModel(users.toArray());
			userCombo.setModel(usuarioModel);
			
			props.setProperty("dir", dirField.getText());
			FileOutputStream out;
			try {
				out = new FileOutputStream(CONFIG_FILE);
				props
						.store(
								out,
								" Arquivo de configuração\n"
										+ "# \n"
										+ "# dir = Diretório de usuários\n"
										+ "# language = Idioma utilizado. Deve existir um arquivo com esse nome em /lang. Ex.: /lang/pt-br.lg");
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		setVisible(true);
	}

	/**
	 * Exibe uma janela contendo o gráfico gerado de acordo com os dados
	 * estabelecidos. Se não for possí­vel gerar o gráfico solicitado, uma
	 * mensagem de erro é exibida.
	 */
	private void makeChart() {
		makeView(new ChartBuilder());
	}

	/**
	 * Exibe uma janela contendo o relatório gerado de acordo com os dados
	 * estabelecidos. Se não for possível gerar o relatório solicitado, uma
	 * mensagem de erro é exibida.
	 */
	private void makeReport() {
		makeView(new ReportBuilder());
	}

	/**
	 * Gera uma visualização de acordo com os parâmetros fornecidos na
	 * interface.
	 * 
	 * @param builder
	 *            Gerador da visualização
	 */
	private void makeView(ViewBuilder builder) {
		String selectedUser = (String) userCombo.getSelectedItem();
		Calendar targetTime = ((CalendarComboBox) ccb).date();
		String selectedInterval = (String) intervalCombo.getSelectedItem();
		SpyProperties.USER_DIR_NAME = dirField.getText() + File.separator
				+ selectedUser;
		SpyProperties.USER_NAME = selectedUser;
		JPanel view = null;

		if (selectedInterval.equals(SpyProperties.DAILY_OPTION_STRING)) {
			view = builder.makeDailyView(targetTime);
		} else if (selectedInterval.equals(SpyProperties.WEEKLY_OPTION_STRING)) {
			view = builder.makeWeeklyView(targetTime);
		} else if (selectedInterval.equals(SpyProperties.MONTHLY_OPTION_STRING)) {
			view = builder.makeMonthlyView(targetTime);
		} else if (selectedInterval.equals(SpyProperties.YEARLY_OPTION_STRING)) {
			view = builder.makeYearlyView(targetTime);
		}

		if (view == null) {
			JOptionPane.showMessageDialog(null, SpyProperties.ERROR_MESSAGE,
					"Spy007", JOptionPane.ERROR_MESSAGE);
		} else {
			String format = "dd/MM/yyyy";
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			String date = formatter.format(targetTime.getTime());

			desktop.add(selectedUser + " - " + date, view);
		}
	}

	/**
	 * Obtém o diretório de usuários, verificando se este é válido.
	 * 
	 * @return <code>false</code> se um diretório válido não foi encontrado<br>
	 *         <code>true</code> se um diretório válido foi encontrado
	 */
	private List<String> chooseDir() {
		fc = new JFileChooser();
		fc.setDialogTitle(SpyProperties.FILE_CHOOSER_TEXT);
		fc.setFileHidingEnabled(true);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = fc.showOpenDialog(null);
		List<String> users = null;

		if (res == JFileChooser.APPROVE_OPTION) {
			File dir = fc.getSelectedFile();
			if (DirValidation.isEmptyDir(dir)) {
				JOptionPane.showMessageDialog(this,
						SpyProperties.EMPTY_DIR_ERROR_MESSAGE, "Spy007",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

			users = DirValidation.isValidDir(dir);
			
			if (users == null) {
				JOptionPane.showMessageDialog(this,
						SpyProperties.NO_USER_ERROR_MESSAGE, "Spy007",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

			SpyProperties.DIR_NAME = dir.getPath();
			return users;
		} else if (res == JFileChooser.CANCEL_OPTION) {
			if (SpyProperties.DIR_NAME == null) {
				int choice = JOptionPane.showConfirmDialog(fc,
						SpyProperties.EXIT_MESSAGE, "Spy007",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.OK_OPTION) {
					System.exit(0);
				} else {
					return null;
				}
			} else {
				return users = findDir(SpyProperties.DIR_NAME, true);
				//return new ArrayList<String>();
			}
		}
		return users;
	}

	/**
	 * Verifica se o diretório fornecido como parâmetro é válido. Caso
	 * contrário, busca outro diretório válido.
	 * 
	 * @param fileName
	 *            Diretório de usuários
	 */
	private List<String> findDir(String fileName, boolean first) {
		File dir = null;
		List<String> users = null;

		boolean found = true;

		if (fileName == null || fileName.equals("")) {
			found = false;
		} else {
			dir = new File(fileName);
			if (DirValidation.isEmptyDir(dir)) {
				found = false;
			}
			
			users = DirValidation.isValidDir(dir);
			if (users == null) {
				found = false;
			}
		}

		if (!found) {
			
			if (first) {
				JOptionPane.showMessageDialog(this, SpyProperties.START_MESSAGE, 
					"Spy007", JOptionPane.QUESTION_MESSAGE);
			}

			users = chooseDir();
			while (users == null) {
				users = chooseDir();
			}
		} else {
			SpyProperties.DIR_NAME = fileName;
		}
		return users;
	}
}
