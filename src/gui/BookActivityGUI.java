package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Activity;
import domain.Booking;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import businessLogic.ApplicationFacadeInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BookActivityGUI extends JFrame {

	private JPanel contentPane;
	private static Booking booking;
	private JTextField txtPrice;
	private JButton btnAccept;
	private JComboBox comboPeople;
	private JComboBox comboActivity;
	private Activity ac;
	private DefaultComboBoxModel<Activity> model = new DefaultComboBoxModel<Activity>();

	private ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

	public static Booking getBooking() {
		return booking;
	}

	public static void setBooking(Booking b) {
		booking = b;
	}

	/**
	 * Create the frame.
	 */
	public BookActivityGUI(Activity a) {
		this.ac = a;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setBounds(96, 104, 134, 28);
		contentPane.add(txtPrice);

		txtPrice.setColumns(10);

		comboActivity = new JComboBox();

		comboActivity.setBounds(97, 47, 320, 27);

		try {
			for (Activity act : facade.getAllActivities()) {
				model.addElement(act);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		comboActivity.setModel(model);

		contentPane.add(comboActivity);

		comboActivity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboActivity.getSelectedItem() != null) {
					ac = (Activity) comboActivity.getSelectedItem();

					String total = String.valueOf((ac.getPrice()));
					txtPrice.setText(total);
				}
			}
		});

		if (ac != null) {
			comboActivity.setSelectedItem(ac);

		} else {
			if (model.getSize() != 0) {
				comboActivity.setSelectedIndex(0);
				ac = (Activity) comboActivity.getSelectedItem();
				String total = String.valueOf((ac.getPrice()));
				txtPrice.setText(total);
			}
		}
		JLabel lblActivity = new JLabel("Activity: ");
		lblActivity.setBounds(24, 51, 61, 16);
		contentPane.add(lblActivity);


		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(24, 110, 61, 16);
		contentPane.add(lblPrice);

		contentPane.add(getBtnAccept());

	}

	private JButton getBtnAccept() {

		btnAccept = new JButton("Accept");

		btnAccept.setBounds(300, 141, 117, 29);

		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (showConfirmDialog())
					try {
						if (getBooking().getActivities().contains(ac)) {
							JOptionPane.showMessageDialog(null,
									"You can't book an Activity twice");
						} else {
							facade.createActivityBooking(getBooking(), ac);
							JOptionPane.showMessageDialog(null,
									"Activity Booked");
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});

		return btnAccept;
	}

	private boolean showConfirmDialog() {

		String nl = System.getProperty("line.separator");

		String message = "Confirm the booking:" + nl + "Activity: "
				+ ac.getName() + nl + "Description: " + ac.getDescription()
				+ nl + "Total price:" + txtPrice.getText();

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}
}
