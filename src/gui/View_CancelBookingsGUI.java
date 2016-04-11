package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.ApplicationFacadeInterface;
import domain.Booking;

public class View_CancelBookingsGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JList offerList;
	private JScrollPane scrollPane;
	private JButton btnSearch;
	private JButton btnCancel;
	private DefaultListModel<Booking> info = new DefaultListModel<Booking>();
	private ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
	private boolean modify;
	private int option;
	private JButton btnView;

	/**
	 * Create the frame.
	 */
	public View_CancelBookingsGUI(boolean modify, int i) {
		this.modify = modify;
		this.option = i;
		setResizable(false);
		setTitle("Bookings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 47, 496, 188);
		contentPane.add(scrollPane);
		getOfferList();

		contentPane.add(getBtnCancel(), null);
		contentPane.add(getBtnSearch(), null);

		contentPane.add(getBtnView(), null);

		btnSearch.setVisible(false);

		if (modify) {
			btnCancel.setText("Select");
		}

		btnSearch.doClick();

	}

	private JList getOfferList() {

		offerList = new JList();
		scrollPane.setViewportView(offerList);
		offerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return offerList;

	}

	private JButton getBtnSearch() {
		btnSearch = new JButton("Search");
		btnSearch.setBounds(391, 6, 117, 29);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					info.clear();
					for (Booking b : StartWindow.getLogin().getBookings()) {
						info.addElement(b);
					}
					offerList.setModel(info);
					offerList
							.addListSelectionListener(new ListSelectionListener() {

								@Override
								public void valueChanged(ListSelectionEvent e) {

									btnCancel.setEnabled(true);
									btnView.setEnabled(true);
								}

							});

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (info.isEmpty())
					btnCancel.setEnabled(false);

			}
		});
		return btnSearch;
	}

	private JButton getBtnCancel() {

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(401, 243, 117, 29);
		btnCancel.setEnabled(false);

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Booking b = (Booking) offerList.getSelectedValue();
				try {
					if (modify) {
						JFrame a = null;
						if (!facade.getAllActivities().isEmpty()) {
							if (option == 0) {
								a = new BookActivityGUI(null);
								BookActivityGUI.setBooking(b);
							} else if (option == 1) {
								a = new SearchActivityGUI();
								SearchActivityGUI.setBooking(b);
							}
							a.setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Currently there are not activities");
						}
					} else {
						if (showConfirmDialog()) {
							if (facade.cancelRequestedBooking(b,
									StartWindow.getLogin())) {
								JOptionPane.showMessageDialog(null,
										"Deleted correctly");
								btnSearch.doClick();
							}
						}

					}

				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		return btnCancel;
	}

	private JButton getBtnView() {

		btnView = new JButton("View");
		btnView.setEnabled(false);
		btnView.setBounds(272, 243, 117, 29);

		btnView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Booking b = (Booking) offerList.getSelectedValue();
				try {
					JFrame a = new ViewBookGUI(b);
					a.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		return btnView;
	}

	private boolean showConfirmDialog() {

		String nl = System.getProperty("line.separator");

		String message = "Do you realy want to confirm the cancelation?";

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}
}
