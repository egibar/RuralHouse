package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import businessLogic.ApplicationFacadeInterface;
import domain.Activity;
import domain.Booking;

public class SearchActivityGUI extends JFrame {

	
	

	private JPanel contentPane;
	private JTextField textKeyword;
	private JButton btnAccept;
	private JList activityList;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private static Booking booking;

	private DefaultListModel<Activity> info = new DefaultListModel<Activity>();
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
	public SearchActivityGUI() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 440, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKeyword = new JLabel("Keyword: ");
		lblKeyword.setBounds(30, 23, 61, 16);
		contentPane.add(lblKeyword);

		textKeyword = new JTextField();
		textKeyword.setBounds(103, 17, 187, 28);
		contentPane.add(textKeyword);
		textKeyword.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 67, 388, 195);
		contentPane.add(scrollPane);
		getOfferList();

		scrollPane.setViewportView(activityList);

		contentPane.add(getBtnAccept());

		contentPane.add(getBtnSearch());
	}

	private JButton getBtnAccept() {
		btnAccept = new JButton("Accept");
		btnAccept.setBounds(301, 274, 117, 29);

		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Activity ac = (Activity) activityList.getSelectedValue();

				try {
					JFrame a = new BookActivityGUI(ac);
					BookActivityGUI.setBooking(getBooking());
					a.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		return btnAccept;
	}

	private JButton getBtnSearch() {
		btnSearch = new JButton("Search");
		btnSearch.setBounds(302, 18, 117, 29);

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String key = textKeyword.getText();
					info.clear();
					for (Activity a : facade.getAllActivities()) {
						if (a.getDescription().contains(key))
							info.addElement(a);
					}

					activityList.setModel(info);
					activityList
							.addListSelectionListener(new ListSelectionListener() {

								@Override
								public void valueChanged(ListSelectionEvent e) {

									btnAccept.setEnabled(true);
								}

							});

				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (info.isEmpty())
					btnAccept.setEnabled(false);

			}
		});
		return btnSearch;
	}

	private JList getOfferList() {

		activityList = new JList();
		scrollPane.setViewportView(activityList);
		activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return activityList;
	}

}
