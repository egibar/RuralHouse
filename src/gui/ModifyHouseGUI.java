package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import domain.RuralHouse;

public class ModifyHouseGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBoxHouses;
	private JButton btnUpdate;

	/**
	 * Create the frame.
	 */
	public ModifyHouseGUI(Vector<RuralHouse> v) {
		setTitle("House modification");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 464, 145);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBoxHouses = new JComboBox(v);
		comboBoxHouses.setBounds(195, 23, 226, 27);
		contentPane.add(comboBoxHouses);

		JLabel lblSelectAHouse = new JLabel("Select a house:");
		lblSelectAHouse.setBounds(31, 27, 115, 16);
		contentPane.add(lblSelectAHouse);

		contentPane.add(getUpdateButton(), null);
	}

	private JButton getUpdateButton() {
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(168, 62, 117, 29);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RuralHouse h = (RuralHouse) comboBoxHouses.getSelectedItem();
				JFrame a = new Add_ModifyHouseGUI(true, h);
				a.setVisible(true);
				dispose();
			}
		});
		return btnUpdate;

	}
	
	

}
