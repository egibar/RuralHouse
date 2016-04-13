package gui;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import configuration.ConfigXML;
import domain.Offer;
import domain.RuralHouse;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ParametrizedSearchGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextField2 = new JTextField();
	private JLabel jLabel3 = new JLabel();
	private JTextField jTextField3 = new JTextField();
	private JButton jButton1 = new JButton();
	private JButton jButton2 = new JButton();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JLabel jLabel4 = new JLabel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private DefaultTableModel tableModel;
	private final JLabel labelNoOffers = new JLabel("");
	private String[] columnNames = new String[] { "Offer#", "Rural House",
			"First Day", "Last Day", "Price" };

	private static configuration.ConfigXML c;
	private JCheckBox chk0;
	private JCheckBox chk1;
	private JCheckBox chk2;
	private JCheckBox chk3;
	private JCheckBox chk4;
	private JCheckBox chk5;
	private JCheckBox chk6;
	private JCheckBox chk7;
	private JCheckBox chk8;

	private Vector<RuralHouse> rhs;
	private JTextField txtCity;

	public ParametrizedSearchGUI() {
		setResizable(false);

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

		rhs = facade.getAllRuralHouses();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(678, 652));
		this.setTitle("Search");
		jLabel2.setText("First day:");
		jLabel2.setBounds(new Rectangle(40, 20, 75, 25));
		jTextField2.setBounds(new Rectangle(167, 213, 155, 25));
		jTextField2.setEditable(false);
		jLabel3.setText("Number of nights:");
		jLabel3.setBounds(new Rectangle(40, 251, 115, 25));
		jTextField3.setBounds(new Rectangle(167, 250, 155, 25));
		jTextField3.setText("0");
		jButton1.setText("Accept");
		jButton1.setBounds(new Rectangle(530, 510, 130, 30));
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton1_actionPerformed(e);
			}
		});
		jButton2.setText("Close");
		jButton2.setBounds(new Rectangle(530, 552, 130, 30));

		jTextField3.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				jTextField3_focusLost();
			}
		});
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		jLabel4.setBounds(new Rectangle(332, 405, 328, 30));
		jLabel4.setForeground(Color.red);
		jCalendar1.setBounds(new Rectangle(97, 57, 225, 150));
		scrollPane.setBounds(new Rectangle(332, 57, 328, 322));

		this.getContentPane().add(scrollPane, null);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int i = table.getSelectedRow();
					int houseNumber = (int) tableModel.getValueAt(i, 1);

					// Dates are represented as strings in the table model
					// They have to be converted to Dates "dd/mm/aa", removing
					// hh:mm:ss:ms with trim
					DateFormat df = DateFormat
							.getDateInstance(DateFormat.SHORT);
					// Date firstDate=
					// trim(df.parse((String)tableModel.getValueAt(i,2)));
					// Date lastDate=
					// trim(df.parse((String)tableModel.getValueAt(i,3)));
					Date firstDate = df.parse((String) tableModel.getValueAt(i,
							2));
					// firstDate=new Date(firstDate.getTime()+12*60*60*1000); //
					// to add 12 hours because that is how they are stored
					Date lastDate = df.parse((String) tableModel.getValueAt(i,
							3));
					// lastDate=new Date(lastDate.getTime()+12*60*60*1000); //
					// to add 12 hours because that is how they are stored

					BookRuralHouseGUI b = new BookRuralHouseGUI(houseNumber,
							firstDate, lastDate);
					b.setVisible(true);
				} catch (Exception ex) {
					System.out
							.println("Error trying to call BookRuralHouseGUI: "
									+ ex.getMessage());
				}
			}
		});

		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);

		table.setModel(tableModel);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jLabel4, null);
		this.getContentPane().add(jButton2, null);
		this.getContentPane().add(jButton1, null);
		this.getContentPane().add(jTextField3, null);
		this.getContentPane().add(jLabel3, null);
		this.getContentPane().add(jTextField2, null);
		this.getContentPane().add(jLabel2, null);
		labelNoOffers.setBounds(332, 441, 328, 14);

		getContentPane().add(labelNoOffers);

		Panel panel = new Panel();
		panel.setBounds(40, 343, 282, 277);
		getContentPane().add(panel);

		chk0 = new JCheckBox("Children's Playground");

		chk1 = new JCheckBox("Climatized Pool");

		chk2 = new JCheckBox("Terrace");

		chk3 = new JCheckBox("Jacuzzi");

		chk4 = new JCheckBox("Spa");

		chk5 = new JCheckBox("Near to Mountains");

		chk6 = new JCheckBox("Near to Ski Stations");

		chk7 = new JCheckBox("Near to Commercial Area");

		chk8 = new JCheckBox("Animals Allowed");

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(chk0).addComponent(chk1)
										.addComponent(chk2).addComponent(chk3)
										.addComponent(chk4).addComponent(chk5)
										.addComponent(chk6).addComponent(chk7)
										.addComponent(chk8))
						.addContainerGap(151, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup().addComponent(chk0)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk3)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk4)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk5)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk6)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk7)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chk8)
						.addContainerGap(22, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(40, 293, 61, 16);
		getContentPane().add(lblCity);

		txtCity = new JTextField();
		txtCity.setBounds(167, 287, 153, 28);
		getContentPane().add(txtCity);
		txtCity.setColumns(10);

		// Codigo para el JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent
							.getNewValue());
					DateFormat dateformat = DateFormat.getDateInstance(1,
							jCalendar1.getLocale());
					jTextField2.setText(dateformat.format(calendarMio.getTime()));
				} else if (propertychangeevent.getPropertyName().equals(
						"calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1,
							jCalendar1.getLocale());
					jTextField2.setText(dateformat1.format(calendarMio
							.getTime()));
					jCalendar1.setCalendar(calendarMio);
				}
			}
		});

	}

	private Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void jTextField3_focusLost() {
		try {
			new Integer(jTextField3.getText());
			jLabel4.setText("");
		} catch (NumberFormatException ex) {
			jLabel4.setText("Error: Introduce a number");
		}
	}

	private void jButton1_actionPerformed(ActionEvent e) {
		// House object
		// First day removing the hour:minute:second:ms from the date

		Date firstDay = trim(new Date(jCalendar1.getCalendar().getTime()
				.getTime()));

		final long diams = 1000 * 60 * 60 * 24;
		long nights = diams * Integer.parseInt(jTextField3.getText());
		// Last day
		Date lastDay = new Date((long) (firstDay.getTime() + nights));

		try {

			ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
			tableModel.setDataVector(null, columnNames);
			for (RuralHouse rh : rhs) {
				boolean valid;
				String city = txtCity.getText();
				if (!city.trim().equals("")) {
					valid = satisfiesRestrictions(getMinServices(),
							rh.getServices(), txtCity.getText(), rh.getCity());
				} else {
					valid = satisfiesRestrictions(getMinServices(),
							rh.getServices());
				}

				if (valid) {
					Vector<Offer> v = rh.getOffers(firstDay, lastDay);

					Enumeration<Offer> en = v.elements();
					Offer of;
					if (!en.hasMoreElements())
						labelNoOffers
								.setText("There are no offers at these dates");
					else {
						labelNoOffers
								.setText("Select an offer if you want to book");

						while (en.hasMoreElements()) {
							of = en.nextElement();
							System.out.println("Offer retrieved: "
									+ of.toString());
							Vector row = new Vector();
							row.add(of.getOfferNumber());
							row.add(of.getRuralHouse().getHouseNumber());

							// Dates are stored in db4o as
							// java.util.Date
							// objects
							// instead of java.sql.Date objects
							// They are converted to strings "dd/mm/aa"
							DateFormat df = DateFormat
									.getDateInstance(DateFormat.SHORT);
							String firstDayString = df.format(of.getFirstDay());
							String lastDayString = df.format(of.getLastDay());
							row.add(firstDayString);
							row.add(lastDayString);
							row.add(of.getPrice());

							tableModel.addRow(row);
						}
					}
				}
			}

		} catch (Exception e1) {

			labelNoOffers.setText(e1.getMessage());
		}
	}

	private boolean[] getMinServices() {
		boolean[] serv = new boolean[9];

		serv[0] = chk0.isSelected();
		serv[1] = chk1.isSelected();
		serv[2] = chk2.isSelected();
		serv[3] = chk3.isSelected();
		serv[4] = chk4.isSelected();
		serv[5] = chk5.isSelected();
		serv[6] = chk6.isSelected();
		serv[7] = chk7.isSelected();
		serv[8] = chk8.isSelected();

		return serv;
	}

	private boolean satisfiesRestrictions(boolean[] min, boolean[] actual) {
		int matches = 0;
		for (boolean b : min) {
			if (b)
				matches++;
		}
		for (int i = 0; i < min.length; i++) {
			if (min[i])
				if (min[i] == actual[i] && matches != 0)
					matches--;
		}

		return matches == 0;
	}

	private boolean satisfiesRestrictions(boolean[] min, boolean[] actual,
			String city, String actualCity) {
		int matches = 0;
		for (boolean b : min) {
			if (b)
				matches++;
		}
		for (int i = 0; i < min.length; i++) {
			if (min[i])
				if (min[i] == actual[i] && matches != 0)
					matches--;
		}

		return matches == 0 && city.equalsIgnoreCase(actualCity);
	}
}