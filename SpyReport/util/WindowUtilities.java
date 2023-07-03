/*
 * WindowUtilities.java 
 * VersÃo 1.0
 * Data: 27/11/2007
 * Copyright (c) 2007 SpyCorp
 */

package util;

import javax.swing.UIManager;

/**
 * UtilitÁrio de GUI
 * 
 * @author <a href="http://spycorp.org">SpyCorp</a>
 * @version 1.0
 */
public class WindowUtilities {

	/**
	 * Configura o sistema para usar o <i>look and fell</i> nativo, usando o
	 * Metal (Java) LAF como default.
	 */
	public static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
	}

	/**
	 * Configura o idioma dos dados da interface como Português.
	 */
	public static void setLanguage() {
		UIManager.put("FileChooser.lookInLabelMnemonic", new Integer('E'));
		UIManager.put("FileChooser.lookInLabelText", "Examinar");
		UIManager.put("FileChooser.saveInLabelText", "Salvar em");
		UIManager.put("FileChooser.saveButtonText", "Salvar");
		UIManager.put("FileChooser.openButtonText", "Abrir");
		UIManager.put("FileChooser.cancelButtonText", "Cancelar");
		UIManager.put("FileChooser.saveButtonToolTipText", "Salvar arquivo");
		UIManager.put("FileChooser.openButtonToolTipText",
				"Abrir arquivo selecionado");
		UIManager.put("FileChooser.cancelButtonToolTipText",
				"Cancelar operação");
		UIManager.put("FileChooser.fileNameLabelMnemonic", new Integer('N'));
		UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo");
		UIManager.put("FileChooser.filesOfTypeLabelMnemonic", new Integer('T'));
		UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo");
		UIManager.put("FileChooser.upFolderToolTipText", "Um nível acima");
		UIManager.put("FileChooser.upFolderAccessibleName", "Um ní­vel acima");
		UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
		UIManager.put("FileChooser.homeFolderAccessibleName", "Desktop");
		UIManager.put("FileChooser.newFolderToolTipText", "Criar nova pasta");
		UIManager
				.put("FileChooser.newFolderAccessibleName", "Criar nova pasta");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
		UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
		UIManager
				.put("FileChooser.detailsViewButtonAccessibleName", "Detalhes");
		UIManager.put("FileChooser.fileNameHeaderText", "Nome");
		UIManager.put("FileChooser.fileSizeHeaderText", "Tamanho");
		UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
		UIManager.put("FileChooser.fileDateHeaderText", "Data");
		UIManager.put("FileChooser.fileAttrHeaderText", "Atributos");
	}

	/**
	 * Configura o sistema para usar o <i>look and fell</i> Metal (Java) LAF.
	 */
	public static void setJavaLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting Java LAF: " + e);
		}
	}

	/**
	 * Configura o sistema para usar o <i>look and fell</i> Motif.
	 */
	public static void setMotifLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println("Error setting Motif LAF: " + e);
		}
	}
}
