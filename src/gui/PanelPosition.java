package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class PanelPosition extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner spinnerLongitude;
	private JSpinner spinnerLatitude;

	public void addChangeListener(ChangeListener l) {
		spinnerLongitude.addChangeListener(l);
		spinnerLatitude.addChangeListener(l);
	}

	public double longitud() {
		return ((SpinnerNumberModel) spinnerLongitude.getModel()).getNumber()
				.doubleValue();
	}

	public double latitud() {
		return ((SpinnerNumberModel) spinnerLatitude.getModel()).getNumber()
				.doubleValue();
	}

	public void setLatitude(double latitude) {
		((SpinnerNumberModel) spinnerLatitude.getModel()).setValue(latitude);
	}

	public void setLongitude(double longitude) {
		((SpinnerNumberModel) spinnerLongitude.getModel()).setValue(longitude);
	}

	public void setPosition(common.Position position) {
		setLatitude(position.latitude);
		setLongitude(position.longitude);
	}

	public common.Position getPosition() {
		return new common.Position(latitud(), longitud());
	}

	public PanelPosition() {
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		SpinnerModel modelLatitude = new SpinnerNumberModel(40.0, // initial
																	// value
				MainWindow.minlatitude, // min
				MainWindow.maxlatitude, // max
				0.2); // step
		JLabel latitudLabel = new JLabel("Latitud (N)");
		this.add(latitudLabel);
		spinnerLatitude = new JSpinner(modelLatitude);
		latitudLabel.setLabelFor(spinnerLatitude);
		this.add(spinnerLatitude);

		SpinnerModel modelLongitude = new SpinnerNumberModel(1.0, // initial
																	// value
				MainWindow.minlongitude, // min
				MainWindow.maxlongitude, // max
				0.2); // step
		JLabel longitudLabel = new JLabel("Longitud (O)");
		this.add(longitudLabel);
		spinnerLongitude = new JSpinner(modelLongitude);
		longitudLabel.setLabelFor(spinnerLongitude);
		this.add(spinnerLongitude);
	}

}// Fin PanelPosition
