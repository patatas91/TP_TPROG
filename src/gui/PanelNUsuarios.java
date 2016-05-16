package gui;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import common.NoObjectException;
import common.SiguienteException;
import common.StructureException;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class PanelNUsuarios extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner spinnerUsuarios;

	public void addChangeListener(ChangeListener l) {
		spinnerUsuarios.addChangeListener(l);

	}

	public int numUsuarios() {
		return ((SpinnerNumberModel) spinnerUsuarios.getModel()).getNumber()
				.intValue();
	}

	public void setnumUsuarios(int n) {
		((SpinnerNumberModel) spinnerUsuarios.getModel()).setValue(n);
	}

	public PanelNUsuarios() throws StructureException, SQLException,
			NoObjectException, SiguienteException {
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		SpinnerModel modelUsuarios = new SpinnerNumberModel(10, // initial value
				1, // min
				MainWindow.numeroUsuarios(), // max
				1); // step
		JLabel usuarioLabel = new JLabel("Seleccione un numero");
		this.add(usuarioLabel);
		spinnerUsuarios = new JSpinner(modelUsuarios);
		usuarioLabel.setLabelFor(spinnerUsuarios);
		this.add(spinnerUsuarios);
	}

}// Fin PanelNUsuarios
