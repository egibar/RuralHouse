package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import domain.Activity;
import domain.Owner;
import businessLogic.ApplicationFacadeInterface;

public class AddActivityGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textPrice;
	private JTextArea textDescription;
	private JButton btnAccept;

	private String description, name;
	private double price;

	private ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

	/**
	 * Create the frame.
	 */
	public AddActivityGUI() {
		setTitle("Fill data");

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 365, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(45, 39, 61, 16);
		contentPane.add(lblName);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(45, 80, 61, 16);
		contentPane.add(lblPrice);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(45, 122, 83, 16);
		contentPane.add(lblDescription);

		textName = new JTextField();
		textName.setBounds(118, 33, 202, 28);
		contentPane.add(textName);
		textName.setColumns(10);

		textPrice = new JTextField();
		textPrice.setBounds(118, 74, 202, 28);
		contentPane.add(textPrice);
		textPrice.setColumns(10);

		textDescription = new JTextArea();
		textDescription.setBounds(45, 150, 275, 151);
		contentPane.add(textDescription);
		textDescription.setColumns(10);

		contentPane.add(getBtnAccept());
	}

	private JButton getBtnAccept() {
		btnAccept = new JButton("Accept");
		btnAccept.setBounds(122, 311, 117, 29);

		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				description = textDescription.getText();
				name = textName.getText();
				Owner o = (Owner) StartWindow.getLogin();
				if (!checkEmptyFields()) {
					try {
						price = Double.parseDouble(textPrice.getText());

						if (showConfirmDialog()) {
							Activity a = facade.createActivity(o, description,
									name, price);
							if (a != null) {
								JOptionPane.showMessageDialog(null,
										"Activity sucessfully added");

								dispose();
							}
						}

					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,
								"Please insert a correct price", "Error",
								JOptionPane.WARNING_MESSAGE);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		return btnAccept;
	}

	private boolean showConfirmDialog() {

		String nl = System.getProperty("line.separator");

		String message = "Please confirm the following data:" + nl
				+ "Activity name: " + name + nl + "Description: " + description
				+ nl + "Price: " + price;

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}

	private boolean checkEmptyFields() {
		String message = "Please fill in all the fields";
		if (name.trim().equals("") || description.trim().equals("")
				|| textPrice.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, message, "Error",
					JOptionPane.WARNING_MESSAGE);
			return true;
		} else {
			return false;
		}
	}

}
