package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import domain.Activity;
import domain.Booking;

import java.awt.ScrollPane;

public class ViewBookGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textHouse;
	private JTextField textFrom;
	private JTextField textTo;
	private JList activityList;
	private DefaultListModel<Activity> info = new DefaultListModel<Activity>();
	private JScrollPane scrollPane;
	private JLabel lblTotal;
	private Booking boo;

	/**
	 * Create the frame.
	 */
	public ViewBookGUI(Booking b) {
		boo = b;
		setResizable(false);
		setTitle("Book Data");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 631, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRuralHouse = new JLabel("Rural House:");
		lblRuralHouse.setBounds(17, 31, 83, 16);
		contentPane.add(lblRuralHouse);

		JLabel lblFrom = new JLabel("From: ");
		lblFrom.setBounds(17, 59, 61, 16);
		contentPane.add(lblFrom);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(17, 87, 61, 16);
		contentPane.add(lblTo);

		textHouse = new JTextField();
		textHouse.setEditable(false);
		textHouse.setBounds(112, 25, 198, 28);
		contentPane.add(textHouse);
		textHouse.setColumns(10);

		textFrom = new JTextField();
		textFrom.setEditable(false);
		textFrom.setBounds(112, 53, 198, 28);
		contentPane.add(textFrom);
		textFrom.setColumns(10);

		textTo = new JTextField();
		textTo.setEditable(false);
		textTo.setBounds(112, 81, 198, 28);
		contentPane.add(textTo);
		textTo.setColumns(10);

		lblTotal = new JLabel("TOTAL:");
		lblTotal.setBounds(45, 168, 61, 16);
		contentPane.add(lblTotal);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(112, 168, 61, 16);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 25, 290, 178);
		contentPane.add(scrollPane);
		double total = b.getPrice();
		for (Activity a : boo.getActivities()) {
			info.addElement(a);
			total = total + a.getPrice();
		}

		getActivityList();

		activityList.setModel(info);

		textHouse.setText(boo.getOffer().getRuralHouse().toString());
		textFrom.setText(boo.getOffer().getFirstDay().toString());
		textTo.setText(boo.getOffer().getLastDay().toString());
		lblNewLabel.setText(String.valueOf(total) + "€");
	}

	private JList getActivityList() {

		activityList = new JList();
		scrollPane.setViewportView(activityList);
		activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return activityList;
	}
}
