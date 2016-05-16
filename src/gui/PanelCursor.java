package gui;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class PanelCursor extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel cursorLongitud;
	public JLabel cursorLatitud;

	public PanelCursor() {
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		JLabel latitudLabel = new JLabel("Latitud (N): ");
		this.add(latitudLabel);
		cursorLatitud = new JLabel("-");
		this.add(cursorLatitud);

		JLabel longitudLabel = new JLabel("Longitud (O): ");
		this.add(longitudLabel);
		cursorLongitud = new JLabel("-");
		this.add(cursorLongitud);

	}

}// Fin PanelCursor
