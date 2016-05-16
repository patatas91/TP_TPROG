package gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.InterfazBuscarElementos;
import common.InterfazGuardarBusqueda;
import common.NoObjectException;
import common.Position;
import common.SiguienteException;
import common.StructureException;
import common.User;
import db.DatabaseConnection;
import java.sql.SQLException;
import java.text.*;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final String mapname = "images/Iberian_Peninsula.jpg";
	public static final String mapPolitico = "images/politico.jpg";
	public static final String mapFisico = "images/fisico.jpg";
	public static final String mapnameCuad = "images/Iberian_Peninsula_cuadricula.jpg";
	public static final String mapPoliticoCuad = "images/politico_cuadricula.jpg";
	public static final String mapFisicoCuad = "images/fisico_cuadricula.jpg";
	public static final String rutaIcono = "images/r2d2.png";
	// Los limites geograficos del mapa son: N: 44.4º N; S: 34.7º N; W: 9.9º; E:
	// 4.8ºE
	public static final double minlongitude = -4.19;
	public static final double maxlongitude = 9.18;
	public static final double minlatitude = 34.7;
	public static final double maxlatitude = 44.4;

	PanelPosition panelposition;

	Map estandar;
	Map fisico;
	Map politico;
	Map estandarCuad;
	Map fisicoCuad;
	Map politicoCuad;
	Map activo;
	PanelCursor panelcursor;
	JButton button;
	JRadioButton MEstandar;
	JRadioButton MFisico;
	JRadioButton MPolitico;
	JCheckBoxMenuItem CEstandar;
	JCheckBoxMenuItem CPolitico;
	JCheckBoxMenuItem CFisico;
	JComboBox seleccionUsuario;
	JMenuBar barra;
	JColorChooser colorChooser;
	// Color del usuario base
	Color colorUsuario = Color.RED;
	// Color de los restantes usuarios
	Color color = Color.BLUE;
	static DatabaseConnection database;
	String username;
	PanelCercanos panelCercanos;
	PanelNUsuarios panelNUsuarios;
	DefaultListModel modeloLista;
	ButtonGroup grupoBotones;
	JPanel viewportPanel;
	JPanel panelEstandar;
	JPanel panelFisico;
	JPanel panelPolitico;
	JPanel panelEstandarCuad;
	JPanel panelFisicoCuad;
	JPanel panelPoliticoCuad;
	Icon icono;
	static InterfazBuscarElementos<User> datos;

	public MainWindow(db.DatabaseConnection db) throws StructureException,
			SQLException, NoObjectException, SiguienteException {
		database = db;
		estandar = new Map(mapname, minlatitude, minlongitude, maxlatitude,
				maxlongitude);
		fisico = new Map(mapFisico, minlatitude, minlongitude, maxlatitude,
				maxlongitude);
		politico = new Map(mapPolitico, minlatitude, minlongitude, maxlatitude,
				maxlongitude);
		estandarCuad = new Map(mapnameCuad, minlatitude, minlongitude,
				maxlatitude, maxlongitude);
		fisicoCuad = new Map(mapFisicoCuad, minlatitude, minlongitude,
				maxlatitude, maxlongitude);
		politicoCuad = new Map(mapPoliticoCuad, minlatitude, minlongitude,
				maxlatitude, maxlongitude);
		activo = null;
		icono = new ImageIcon(rutaIcono);
		panelposition = new PanelPosition();
		panelcursor = new PanelCursor();
		panelNUsuarios = new PanelNUsuarios();
	}

	public void start() throws SQLException, NoObjectException,
			StructureException, SiguienteException {
		String s = (String) JOptionPane.showInputDialog(this,
				"Nombre de usuario:", "Login", JOptionPane.PLAIN_MESSAGE);

		this.setTitle("Mapa de Localizaciones");

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			this.setTitle("MyLatitude - " + s);
			if (!database.loginUser(s))
				database.addUser(s, new common.Position(41.39, 0.53));
			username = s;
			distribute();
		} else
			this.dispose();
	}// Fin start

	/**
	 * Devuelve el numero de usuarios disponibles
	 * 
	 * @return
	 * @throws StructureException
	 * @throws SQLException
	 * @throws NoObjectException
	 * @throws SiguienteException
	 */
	public static int numeroUsuarios() throws StructureException, SQLException,
			NoObjectException, SiguienteException {
		int n = 0;
		datos = database.getUsers();
		datos.inicializarIterador();
		while (datos.haySiguiente()) {
			datos.siguiente();
			n++;
		}
		return n;
	}// Fin numeroUsuarios

	private void distribute() throws SQLException, NoObjectException,
			StructureException, SiguienteException {

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		panelposition.setPosition(database.getPosition(username));
		DefaultListModel list = llenarLista(
				database.getClosest(username, numeroUsuarios()), 0);
		panelCercanos = new PanelCercanos(list);
		datos = database.getUsers();
		colorChooser = new JColorChooser();
		activo = estandar;
		updateImage(activo, color, colorUsuario);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creamos un panel para la derecha, en el centro esta el mapa
		BorderLayout layout = new BorderLayout();

		JPanel right = new JPanel();
		JPanel data = new JPanel();
		right.add(data, BorderLayout.CENTER);

		// En el panel de la derecha, donde estan los datos, vamos a ponerlos
		// uno debajo de
		// otro (BoxLayout)
		BoxLayout dataLayout = new BoxLayout(data, BoxLayout.Y_AXIS);
		data.setLayout(dataLayout);

		viewportPanel = new JPanel();
		viewportPanel.setLayout(new BorderLayout());

		// Crea la barra de menus
		barra = new JMenuBar();
		this.setJMenuBar(barra);

		// establece el menu archivo y sus elementos de menu
		JMenu menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');

		JMenuItem añadirUser = new JMenuItem("Añadir Usuario");
		añadirUser.setMnemonic('A');
		menuArchivo.add(añadirUser);
		añadirUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					añadirUsuario();
					updateImage(activo, color, colorUsuario);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JMenuItem elementoSalir = new JMenuItem("Salir");
		elementoSalir.setMnemonic('S');
		menuArchivo.add(elementoSalir);
		elementoSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		barra.add(menuArchivo);

		// establece el menu colores y sus elementos de menu
		JMenu herramientas = new JMenu("Herramientas");
		herramientas.setMnemonic('H');

		JMenuItem menuColorUsuario = new JMenuItem("Color para el usuario");
		menuColorUsuario.setMnemonic('1');
		herramientas.add(menuColorUsuario);
		menuColorUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorUsuario = JColorChooser.showDialog(colorChooser,
						"Seleccione un color para el usuario", color);
				if (colorUsuario == null) {
					colorUsuario = Color.RED;
				}
				try {
					updateImage(activo, color, colorUsuario);
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JMenuItem menuColorRestantes = new JMenuItem(
				"Color para los otros usuarios");
		menuColorRestantes.setMnemonic('2');
		herramientas.add(menuColorRestantes);
		menuColorRestantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(colorChooser,
						"Seleccione un color", color);
				if (color == null) {
					color = Color.BLUE;
				}
				try {
					updateImage(activo, color, colorUsuario);
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// añade al menu herramientas una opcion para añadir cuadriculas
		herramientas.addSeparator();
		JMenu cuadriculas = new JMenu("Añadir cuadriculas");
		cuadriculas.setMnemonic('3');
		herramientas.add(cuadriculas);

		// JCHECKBOXMENUITEMS
		CEstandar = new JCheckBoxMenuItem("Estandar");
		CEstandar.setMnemonic(KeyEvent.VK_E);
		cuadriculas.add(CEstandar);

		CPolitico = new JCheckBoxMenuItem("Politico");
		CPolitico.setMnemonic(KeyEvent.VK_P);
		cuadriculas.add(CPolitico);

		CFisico = new JCheckBoxMenuItem("Fisico");
		CFisico.setMnemonic(KeyEvent.VK_F);
		cuadriculas.add(CFisico);

		barra.add(herramientas);

		// establece el menu Acerca de...
		JMenu menuAbout = new JMenu("Acerca de...");
		menuAbout.setMnemonic('A');
		JMenuItem autor = new JMenuItem("Autor");
		autor.setMnemonic('1');
		menuAbout.add(autor);
		autor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Cristian Simon Moreno - NIP: 611487  ",
						"Acerca de...", JOptionPane.INFORMATION_MESSAGE, icono);
			}
		});
		barra.add(menuAbout);

		// crea los paneles de contenido
		panelEstandar = new JPanel();
		panelEstandar.add(estandar, BorderLayout.CENTER);
		panelEstandar.setLayout(new FlowLayout());

		panelFisico = new JPanel();
		panelFisico.add(fisico, BorderLayout.CENTER);
		panelFisico.setLayout(new FlowLayout());

		panelPolitico = new JPanel();
		panelPolitico.add(politico, BorderLayout.CENTER);
		panelPolitico.setLayout(new FlowLayout());

		panelPoliticoCuad = new JPanel();
		panelPoliticoCuad.add(politicoCuad, BorderLayout.CENTER);
		panelPoliticoCuad.setLayout(new FlowLayout());

		panelFisicoCuad = new JPanel();
		panelFisicoCuad.add(fisicoCuad, BorderLayout.CENTER);
		panelFisicoCuad.setLayout(new FlowLayout());

		panelEstandarCuad = new JPanel();
		panelEstandarCuad.add(estandarCuad, BorderLayout.CENTER);
		panelEstandarCuad.setLayout(new FlowLayout());

		// agregar el panelEstandar como panel incial
		viewportPanel.add(panelEstandar, BorderLayout.CENTER);
		// colocar el contenedor en el centro del frame
		this.getContentPane().add(viewportPanel, BorderLayout.CENTER);
		this.getContentPane().setLayout(layout);
		this.getContentPane().add(new JScrollPane(viewportPanel),
				BorderLayout.CENTER);
		this.getContentPane().add(right, BorderLayout.EAST);

		// PANELPOSITION
		data.add(panelposition);
		TitledBorder titleCoordenadas = BorderFactory
				.createTitledBorder("Coordenadas locales");
		panelposition.setBorder(titleCoordenadas);

		// PANELNUSUARIOS
		data.add(panelNUsuarios);
		TitledBorder titleNUsuarios = BorderFactory
				.createTitledBorder("Usuarios en la lista");
		panelNUsuarios.setBorder(titleNUsuarios);

		// SCROLLPANE
		data.add(panelCercanos);
		TitledBorder titleBD = BorderFactory
				.createTitledBorder("Coordenadas en base de datos");
		panelCercanos.setBorder(titleBD);

		// JBUTTON
		JButton boton = new JButton("Actualizar");
		data.add(boton, BorderLayout.EAST);

		// PANELCURSOR
		data.add(panelcursor);
		TitledBorder titleCursor = BorderFactory
				.createTitledBorder("Coordenadas del cursor");
		panelcursor.setBorder(titleCursor);

		// COMBOBOX
		seleccionUsuario = new JComboBox();
		datos.inicializarIterador();
		while (datos.haySiguiente()) {
			User aux = datos.siguiente();
			if (!aux.getClave().equalsIgnoreCase(username))
				seleccionUsuario.addItem(aux.getClave());
		}
		data.add(seleccionUsuario);
		seleccionUsuario.setSelectedIndex(-1);
		TitledBorder titleCO = BorderFactory
				.createTitledBorder("Encontrar un usuario");
		seleccionUsuario.setBorder(titleCO);

		// JRADIOBUTTONS
		// estandar
		MEstandar = new JRadioButton("Estandar");
		MEstandar.setMnemonic(KeyEvent.VK_B);
		MEstandar.setActionCommand("Estandar");
		MEstandar.setSelected(true);
		data.add(MEstandar);

		// politico
		MPolitico = new JRadioButton("Politico");
		MPolitico.setMnemonic(KeyEvent.VK_C);
		MPolitico.setActionCommand("Politico");
		data.add(MPolitico);

		// fisico
		MFisico = new JRadioButton("Fisico");
		MFisico.setMnemonic(KeyEvent.VK_B);
		MFisico.setActionCommand("Fisico");
		data.add(MFisico);

		ButtonGroup grupoBotones = new ButtonGroup();
		grupoBotones.add(MEstandar);
		grupoBotones.add(MPolitico);
		grupoBotones.add(MFisico);

		// LISTENERS
		// Mapa estandar
		estandar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), estandar);
			}
		});

		estandar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), estandar);
			}
		});// Fin listener estandar

		estandarCuad.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), estandarCuad);
			}
		});

		estandarCuad.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), estandarCuad);
			}
		});// Fin listener estandarCuad

		// Mapa politico
		politico.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), politico);
			}
		});

		politico.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), politico);

			}
		});// Fin listener politico

		politicoCuad.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), politicoCuad);
			}
		});

		politicoCuad.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), politicoCuad);
			}
		});// Fin listener politicoCuad

		// Mapa fisico
		fisico.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), fisico);
			}
		});

		fisico.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), fisico);
			}
		});// Fin listener fisico

		fisicoCuad.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateCoordinatesFromMap(e.getX(), e.getY(), fisicoCuad);
			}
		});

		fisicoCuad.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				updateStatusPanel(e.getX(), e.getY(), fisicoCuad);
			}
		});// Fin listener fisicoCuad

		// panelposition
		panelposition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					updateImage(activo, color, colorUsuario);					
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateDatabaseFromCoordinates();
			}
		});// Fin listener panelposition

		// JRadioButtons
		// MEstandar
		MEstandar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarBotones();
				activo = estandar;
				if (viewportPanel != null) {
					viewportPanel.removeAll();
					viewportPanel.add(panelEstandar, BorderLayout.CENTER);
				}
				viewportPanel.validate();
				viewportPanel.repaint();
				try {
					updateImage(activo, color, colorUsuario);
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});// Fin listener MEstandar

		// MPolitico
		MPolitico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarBotones();
				activo = politico;
				if (viewportPanel != null) {
					viewportPanel.removeAll();
					viewportPanel.add(panelPolitico, BorderLayout.CENTER);
				}
				viewportPanel.validate();
				viewportPanel.repaint();
				try {
					updateImage(activo, color, colorUsuario);
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});// Fin listener MPolitico

		// MFisico
		MFisico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarBotones();
				activo = fisico;
				if (viewportPanel != null) {
					viewportPanel.removeAll();
					viewportPanel.add(panelFisico, BorderLayout.CENTER);
				}
				viewportPanel.validate();
				viewportPanel.repaint();
				try {
					updateImage(activo, color, colorUsuario);
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});// Fin listener MFisico

		// JCheckBoxItems
		// CEstandar
		CEstandar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activo == estandar || activo == estandarCuad) {
					activo = estandarCuad;
					AbstractButton aButton = (AbstractButton) e.getSource();
					boolean selected = aButton.getModel().isSelected();
					if (selected) {
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelEstandarCuad,
									BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						activo = estandar;
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelEstandar,
									BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Debe seleccionar primero el tipo de mapa estandar",
									"Error", JOptionPane.ERROR_MESSAGE);
					actualizarBotones();
				}
			}
		});

		// CPolitico
		CPolitico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activo == politico || activo == politicoCuad) {
					activo = politicoCuad;

					AbstractButton aButton = (AbstractButton) e.getSource();
					boolean selected = aButton.getModel().isSelected();
					if (selected) {
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelPoliticoCuad,
									BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						activo = politico;
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelPolitico,
									BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Debe seleccionar primero el tipo de mapa politico",
									"Error", JOptionPane.ERROR_MESSAGE);
					actualizarBotones();
				}
			}
		});

		// CFisico
		CFisico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activo == fisico || activo == fisicoCuad) {
					activo = fisicoCuad;
					AbstractButton aButton = (AbstractButton) e.getSource();
					boolean selected = aButton.getModel().isSelected();
					if (selected) {
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelFisicoCuad,
									BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						activo = fisico;
						if (viewportPanel != null) {
							viewportPanel.removeAll();
							viewportPanel.add(panelFisico, BorderLayout.CENTER);
						}
						viewportPanel.validate();
						viewportPanel.repaint();
						try {
							updateImage(activo, color, colorUsuario);
						} catch (SiguienteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (StructureException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Debe seleccionar primero el tipo de mapa fisico",
							"Error", JOptionPane.ERROR_MESSAGE);
					actualizarBotones();
				}
			}
		});

		// seleccionUsuario
		seleccionUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					usuarioSeleccionado(seleccionUsuario.getSelectedItem()
							.toString(), activo, color);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});// Fin listener seleccionUsuario

		// boton ACTUALIZAR
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actualizarLista();
					JOptionPane.showMessageDialog(null,
							"Lista actualizada correctamente", "",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (StructureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoObjectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SiguienteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});// Fin listener boton ACTUALIZAR

		this.pack();
		this.setVisible(true);

		// Redibujar mapa y circulo
		updateImage(activo, color, colorUsuario);
	}// Fin distribute

	/**
	 * Marca en el mapa [m], al usuario con nombre [clave]
	 * 
	 * @param clave
	 * @param m
	 * @param color
	 * @throws SQLException
	 * @throws StructureException
	 * @throws SiguienteException
	 */
	public void usuarioSeleccionado(String clave, Map m, Color color)
			throws SQLException, StructureException, SiguienteException {
		m.reset();		
		datos = database.getUsers();
		datos.inicializarIterador();
		while (datos.haySiguiente()) {
			User aux = datos.siguiente();
			if(!aux.getNombre().equalsIgnoreCase(username)){
			if (!aux.getNombre().equalsIgnoreCase(clave)) {
				m.mark(aux.getPosition(), color, aux.getNombre());
			} else {
				m.markSeleccionado(aux.getPosition(), Color.GREEN,
						aux.getNombre());
			}}
		}
		m.markIcono(panelposition.getPosition(), colorUsuario, username);
		m.repaint();
	}// Fin usuarioSeleccionado

	/**
	 * Añade un usuario a la base de datos pidiendo los datos por medio de
	 * ventanas
	 * 
	 * @throws SQLException
	 * @throws StructureException
	 * @throws SiguienteException
	 */
	public void añadirUsuario() throws SQLException, StructureException,
			SiguienteException {
		try {
			String nombre = (String) JOptionPane.showInputDialog(null,
					"Nombre del usuario:", "Usuario a añadir",
					JOptionPane.PLAIN_MESSAGE);
			double latitud = Double.valueOf(JOptionPane.showInputDialog(null,
					"latitud (44,4º - 34,7º):", "Usuario a añadir",
					JOptionPane.PLAIN_MESSAGE));
			double longitud = Double.valueOf(JOptionPane.showInputDialog(null,
					"longitud (9,9º - 4,8º):", "Usuario a añadir",
					JOptionPane.PLAIN_MESSAGE));
			Position p = new Position(latitud, longitud);
			boolean encontrado = false;
			datos = database.getUsers();
			datos.inicializarIterador();
			while (datos.haySiguiente() && !encontrado) {
				if (datos.siguiente().getNombre().equalsIgnoreCase(nombre)) {
					encontrado = true;
				}
			}
			if (!encontrado) {
				if (p.latitude <= 44.4 && p.latitude >= 34.7
						&& p.longitude <= 9.9 && p.longitude >= 4.8) {
					database.addUser(nombre, p);
					seleccionUsuario.addItem(nombre);
					JOptionPane.showMessageDialog(new JFrame(),
							"Usuario añadido correctamente, actualice para ver "
									+ "los cambios", "",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Limites del mapa sobrepasados", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"El usuario ya se encuentra en la base de datos",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Datos erroneos",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}// Fin añadirUsuarios

	/**
	 * Desmarca los JCheckBoxMenuItems que estuvieran marcados
	 */
	public void actualizarBotones() {
		CEstandar.setSelected(false);
		CFisico.setSelected(false);
		CPolitico.setSelected(false);
	}// Fin actualizarBotones

	public void updateStatusPanel(int x, int y, Map m) {
		Position position = m.positionFromPixelCoordinates(x, y);
		DecimalFormat df = new DecimalFormat("#.###");
		panelcursor.cursorLatitud.setText(df.format(position.latitude));
		panelcursor.cursorLongitud.setText(df.format(position.longitude));
	}// Fin updateStatusPanel

	/**
	 * Dada una estructura, llena una DefaultListModel con los [n] datos
	 * contenidos en dicha estructura
	 * 
	 * @param estructura
	 * @param n
	 * @return
	 * @throws StructureException
	 * @throws SQLException
	 * @throws NoObjectException
	 * @throws SiguienteException
	 */
	public DefaultListModel llenarLista(
			InterfazGuardarBusqueda<User> estructura, int n)
			throws StructureException, SQLException, NoObjectException,
			SiguienteException {
		n = panelNUsuarios.numUsuarios();
		int iteraciones = 1;
		DefaultListModel lista = new DefaultListModel();
		estructura.inicializarIterador();
		while (estructura.haySiguiente() && iteraciones <= n) {
			lista.addElement(estructura.siguiente().toString());
			iteraciones++;
		}
		return lista;
	}// Fin llenarLista

	/**
	 * Actualiza la lista contenida en panelCercanos
	 * 
	 * @throws StructureException
	 * @throws SQLException
	 * @throws NoObjectException
	 * @throws SiguienteException
	 */
	public void actualizarLista() throws StructureException, SQLException,
			NoObjectException, SiguienteException {
		DefaultListModel listaNueva = llenarLista(
				database.getClosest(username, numeroUsuarios()), 0);
		panelCercanos.scrollpane.setViewportView(new JList(listaNueva));
	}// Fin actualizarLista

	public void updateDatabaseFromMap(int x, int y, Map m) {
		try {
			database.updatePosition(username,
					m.positionFromPixelCoordinates(x, y));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}// Fin updateDatabaseFromMap

	public void updateDatabaseFromCoordinates() {
		try {
			database.updatePosition(username, panelposition.getPosition());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}// Fin updateDatabaseFromCoordinates

	public void updateCoordinatesFromMap(int x, int y, Map m) {
		panelposition.setPosition(m.positionFromPixelCoordinates(x, y));
	}// Fin updateCoordinatesFromMap

	public void updateImage(Map m, Color color, Color colorUsuario)
			throws SiguienteException, SQLException, StructureException {
		m.reset();
		datos = database.getUsers();
		datos.inicializarIterador();

		while (datos.haySiguiente()) {
			User aux = datos.siguiente();
			if (!aux.getClave().equalsIgnoreCase(username)) {
				m.mark(aux.getPosition(), color, aux.getNombre());
			}
		}
		m.markIcono(panelposition.getPosition(), colorUsuario, username);
		m.repaint();
	}// Fin updateImagen	

}// Fin MainWindow
