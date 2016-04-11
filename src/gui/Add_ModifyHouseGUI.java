package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;
import domain.RuralHouse;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Panel;
import java.awt.Checkbox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Add_ModifyHouseGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textCity;
	private JTextArea textDescript;

	private String city;
	private String description;
	private String address;
	private boolean[] services;

	private JTextField textAdress;
	private JButton btnAddHouse;
	private JCheckBox chk0;
	private JCheckBox chk1;
	private JCheckBox chk2;
	private JCheckBox chk3;
	private JCheckBox chk4;
	private JCheckBox chk5;
	private JCheckBox chk6;
	private JCheckBox chk7;
	private JCheckBox chk8;
	private JButton btnDelete;
	private RuralHouse rh;

	/**
	 * Create the frame.
	 */
	public Add_ModifyHouseGUI(boolean modify, RuralHouse h) {
		
		setTitle("House Information");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(26, 35, 101, 16);

		textCity = new JTextField();
		textCity.setBounds(97, 29, 221, 28);
		textCity.setColumns(10);

		JLabel lblExtras = new JLabel("Extras:");
		lblExtras.setBounds(332, 29, 152, 28);

		textAdress = new JTextField();
		textAdress.setBounds(97, 69, 221, 28);
		textAdress.setColumns(10);

		JLabel lblDescription = new JLabel("Description: ");
		lblDescription.setBounds(26, 117, 101, 16);

		textDescript = new JTextArea();
		textDescript.setBounds(26, 145, 292, 195);

		JLabel lblAddress_1 = new JLabel("Address:");
		lblAddress_1.setBounds(26, 76, 110, 16);
		contentPane.setLayout(null);
		contentPane.add(lblCity);
		contentPane.add(textCity);
		contentPane.add(lblExtras);
		contentPane.add(textAdress);
		contentPane.add(lblDescription);

		contentPane.add(getAddButton(modify));
		contentPane.add(textDescript);
		contentPane.add(lblAddress_1);

		Panel panel = new Panel();
		panel.setBounds(332, 69, 207, 272);
		contentPane.add(panel);

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
						.addContainerGap(18, Short.MAX_VALUE)));
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
						.addContainerGap(17, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		if (modify) {
			rh = h;
			textCity.setText(h.getCity());
			textDescript.setText(h.getDescription());
			textAdress.setText(h.getAddress());
			setServices(h.getServices());
			contentPane.add(getDeleteButton(), null);

		}
	}

	private JButton getDeleteButton() {
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(309, 355, 117, 29);
		contentPane.add(btnDelete);

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					ApplicationFacadeInterface facade = StartWindow
							.getBusinessLogic();

					if (showConfirmDelete()) {
						if (facade.deleteRequestedHouse(rh,
								(Owner) StartWindow.getLogin())) {
							JOptionPane.showMessageDialog(null,
									"Data has been deleted");

							dispose();
						} else {
							showDeleteDialog();
							dispose();
						}

					}
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});

		return btnDelete;

	}

	private JButton getAddButton(boolean modify) {
		btnAddHouse = new JButton("Add");
		btnAddHouse.setBounds(426, 355, 117, 29);

		if (modify) {
			btnAddHouse.setText("Update");
			btnAddHouse.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					ApplicationFacadeInterface facade = StartWindow
							.getBusinessLogic();

					try {
						city = textCity.getText();
						description = textDescript.getText();
						address = textAdress.getText();
						services = getServices();

						if (!checkEmptyFields())
							if (showConfirmDialog()) {
								if (facade.updateRequestedHouse(rh, city,
										description, address, services)) {
									JOptionPane.showMessageDialog(null,
											"Data has been updated");
									dispose();
								}
							} else {
							}
						else {
							JOptionPane.showMessageDialog(null,
									"The data has not been updated");
							dispose();
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
		} else {
			btnAddHouse.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					ApplicationFacadeInterface facade = StartWindow
							.getBusinessLogic();

					try {
						city = textCity.getText();
						description = textDescript.getText();
						address = textAdress.getText();
						services = getServices();
						if (!checkEmptyFields())
							if (showConfirmDialog())
								if (facade.createRuralHouse(
										(Owner) StartWindow.getLogin(), city,
										description, address, services) != null) {
									JOptionPane.showMessageDialog(null,
											"House sucessfully added");
									dispose();
								}

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
		}

		return btnAddHouse;
	}

	private boolean showConfirmDialog() {

		String nl = System.getProperty("line.separator");

		String message = "Please confirm the following data:" + nl
				+ "City name: " + city + nl + "Description: " + description
				+ nl + "Address: " + address;

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}

	private boolean showConfirmDelete() {

		String nl = System.getProperty("line.separator");

		String message = "Please confirm that you want to delete";

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}

	private boolean showDeleteDialog() {

		String nl = System.getProperty("line.separator");

		String message = "The data has not been deleted due to" + nl
				+ "some of the following reasons: " + nl + nl
				+ "The house actually has offers " + nl
				+ "There was an exception";
		int selection = JOptionPane.showConfirmDialog(null, message,
				"Delete Failed", JOptionPane.WARNING_MESSAGE);

		return selection == 0;
	}

	private boolean checkEmptyFields() {
		String message = "Please fill in all the fields";
		if (city.trim().equals("") || description.trim().equals("")
				|| address.trim().equals("")) {
			JOptionPane.showMessageDialog(null, message, "Error",
					JOptionPane.WARNING_MESSAGE);
			return true;
		} else {
			return false;
		}

	}

	private boolean[] getServices() {
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

	private void setServices(boolean[] serv) {

		chk0.setSelected(serv[0]);
		chk1.setSelected(serv[1]);
		chk2.setSelected(serv[2]);
		chk3.setSelected(serv[3]);
		chk4.setSelected(serv[4]);
		chk5.setSelected(serv[5]);
		chk6.setSelected(serv[6]);
		chk7.setSelected(serv[7]);
		chk8.setSelected(serv[8]);

	}
}
