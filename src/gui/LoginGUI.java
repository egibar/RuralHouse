package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private String login;

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Login");
		setBounds(100, 100, 378, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(36, 41, 61, 16);
		contentPane.add(lblUser);

		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setBounds(36, 75, 70, 16);
		contentPane.add(lblNewLabel);

		textUser = new JTextField();
		textUser.setBounds(122, 35, 207, 28);
		contentPane.add(textUser);
		textUser.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(122, 69, 207, 28);
		contentPane.add(passwordField);

		contentPane.add(getbtnLogin());
	}

	private JButton getbtnLogin() {
		btnLogin = new JButton("Login");
		btnLogin.setBounds(67, 115, 248, 29);
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					ApplicationFacadeInterface facade = StartWindow
							.getBusinessLogic();

					login = textUser.getText();
					String password = new String(passwordField.getPassword());

					StartWindow.setLogin(facade.checkLogin(login, password));

					boolean b = StartWindow.getLogin() != null;
					showLoginMessage(b);

					textUser.setText("");
					passwordField.setText("");

					if (b){
						StartWindow.setRegisteredMode();
						dispose();
						}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		return btnLogin;
	}

	private void showLoginMessage(boolean access) {
		String message;
		if (access) {
			message = "Welcome " + login;
		} else {
			message = "The user does't exist or the password is incorrect";
		}
		JOptionPane.showMessageDialog(null, message, "Access Granted",
				JOptionPane.PLAIN_MESSAGE);

	}
}
