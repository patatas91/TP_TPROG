package gui;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class PanelCercanos extends JPanel {

	private static final long serialVersionUID = 1L;
	public JScrollPane scrollpane;
	
	public void setLista (DefaultListModel lista) {
		scrollpane.setViewportView(new JList(lista));
	}

	public PanelCercanos(DefaultListModel lista) {
		GridLayout layout = new GridLayout();
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		// Crea un nuevo JScrollPane con una JList
		scrollpane = new JScrollPane(new JList(lista));
		this.add(scrollpane);
	}

}// Fin PanelCercanos