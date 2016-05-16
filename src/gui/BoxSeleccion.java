package gui;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class BoxSeleccion extends JPanel {

	private static final long serialVersionUID = 1L;
	public JComboBox combo;

	public BoxSeleccion(DefaultListModel lista) {
		GridLayout layout = new GridLayout();
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		// Crea un JComboBox con una DefaultListModel
		combo = new JComboBox((ComboBoxModel) lista);
		this.add(combo);
	}

}// Fin BoxSeleccion