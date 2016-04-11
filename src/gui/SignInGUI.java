package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;
import domain.User;
import exceptions.OverlappingUserExists;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SignInGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textUser;
	private JTextField textPass;
	private JTextField textAccount;
	private JButton btnSign;

	private String name;
	private String login;
	private String password;
	private String bankAccount;
	private boolean ownerType;
	private final ButtonGroup userType = new ButtonGroup();
	private JRadioButton rdbtnClient;
	private JRadioButton rdbtnOwner;

	/**
	 * Create the frame.
	 */
	public SignInGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Fill Data");
		setBounds(100, 100, 326, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 22, 55, 27);
		contentPane.add(lblName);

		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(10, 61, 55, 27);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 100, 81, 27);
		contentPane.add(lblPassword);

		JLabel lblBankAccount = new JLabel("Bank Account:");
		lblBankAccount.setBounds(10, 139, 101, 27);
		contentPane.add(lblBankAccount);

		textName = new JTextField();
		textName.setBounds(120, 21, 190, 28);
		contentPane.add(textName);
		textName.setColumns(10);

		textUser = new JTextField();
		textUser.setBounds(120, 60, 190, 28);
		contentPane.add(textUser);
		textUser.setColumns(10);

		textPass = new JTextField();
		textPass.setBounds(120, 99, 190, 28);
		contentPane.add(textPass);
		textPass.setColumns(10);

		textAccount = new JTextField();
		textAccount.setBounds(120, 138, 190, 28);
		contentPane.add(textAccount);
		textAccount.setColumns(10);

		contentPane.add(getJButton(), null);
		
		rdbtnClient = new JRadioButton("Client");
		userType.add(rdbtnClient);
		rdbtnClient.setBounds(194, 178, 81, 23);
		contentPane.add(rdbtnClient);
		
		rdbtnOwner = new JRadioButton("Owner");
		rdbtnOwner.setSelected(true);
		userType.add(rdbtnOwner);
		rdbtnOwner.setBounds(53, 178, 81, 23);
		contentPane.add(rdbtnOwner);
	}

	private JButton getJButton() {
		btnSign = new JButton("Sign-in");
		btnSign.setBounds(99, 228, 155, 29);
		btnSign.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					ApplicationFacadeInterface facade = StartWindow
							.getBusinessLogic();
					
					ownerType = rdbtnOwner.isSelected();
					name = textName.getText();
					login = textUser.getText();
					password = textPass.getText();
					bankAccount = textAccount.getText();
					if (!checkEmptyFields()) {
						if (showConfirmDialog()) {
							
							if(ownerType){
								User u = facade.createOwner(name, login, password,
										bankAccount);

								System.out.println(u.toString()
										+ " registered as an owner.");
							}else{
								User u = facade.createClient(name, login, password, bankAccount);
								System.out.println(u.toString()
										+ " registered as a client.");
							}
							
							

							showRegistrationDialog();
							dispose();

						}
					}

				} catch (OverlappingUserExists ooe) {
					showErrorDialog();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		return btnSign;
	}

	private boolean checkEmptyFields() {
		String message = "Please fill in all the fields";
		if (bankAccount.trim().equals("") || password.trim().equals("")
				|| login.trim().equals("") || name.trim().equals("")) {
			JOptionPane.showMessageDialog(null, message, "Error",
					JOptionPane.WARNING_MESSAGE);
			return true;
		} else {
			return false;
		}

	}

	private void showErrorDialog() {
		String message = "User already registered";
		JOptionPane.showMessageDialog(null, message, "Error",
				JOptionPane.WARNING_MESSAGE);
		textName.setText("");
		textUser.setText("");
		textPass.setText("");
		textAccount.setText("");

	}

	private boolean showConfirmDialog() {

		String nl = System.getProperty("line.separator");

		String message = "Please confirm the following data:" + nl + "Name: "
				+ name + nl + "User: " + login + nl + "Password: " + password
				+ nl + "Bank Account: " + bankAccount;

		int selection = JOptionPane.showConfirmDialog(null, message,
				"Confirmation", JOptionPane.YES_NO_OPTION);

		return selection == 0;
	}

	private void showRegistrationDialog() {

		String message = "User " + login + " registered";
		JOptionPane.showMessageDialog(null, message, "Succesfull Registration",
				JOptionPane.PLAIN_MESSAGE);

		textName.setText("");
		textUser.setText("");
		textPass.setText("");
		textAccount.setText("");
	}
}
