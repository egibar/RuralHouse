package gui;

/**
 * @author Software Engineering teachers
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

//import businessLogic.FacadeImplementation;
import businessLogic.ApplicationFacadeInterface;
import businessLogic.FacadeImplementation;
import configuration.ConfigXML;
import domain.Client;
import domain.Owner;
import domain.RuralHouse;
import domain.User;
import exceptions.DB4oManagerCreationException;

public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private static configuration.ConfigXML c;

	public static ApplicationFacadeInterface facadeInterface;
	private JLabel lblNewLabel;

	private static User login;
	private static ActionListener loginListener;
	private static JMenuBar menuBar;
	private static JMenu mnUser;
	private static JMenuItem mntmLogIn;
	private static JMenuItem mntmSignIn;
	private static JMenu mnQuerys;
	private static JMenuItem mntmAvailability;
	private static JMenuItem mntmParametricedSearch;
	private static JMenuItem mntmActivities;
	private static JMenu mnBookings;
	private static JMenu mnOwner;
	private static JMenuItem mntmBookRuralHouse;
	private static JMenuItem mntmBookActivity;
	private static JMenuItem mntmViewBookings;
	private static JSeparator separator;
	private static JMenuItem mntmAddHouse;
	private static JMenuItem mntmAddActivity;
	private static JMenuItem mntmModifyHouse;
	private static JMenuItem mntmSetAvailavility;
	private static JSeparator separator_1;
	private static JSeparator separator_2;
	private static JSeparator separator_3;
	private static JMenu mnMode;
	private static JMenuItem mntmOwner;
	private static JMenuItem mntmClient;

	private static JButton btnmode1 = null;
	private static JButton btnmode2 = null;
	private static JButton btnmode3 = null;
	private static JButton btnmode4 = null;

	private static JButton btnmode1o = null;
	private static JButton btnmode2o = null;
	private static JButton btnmode3o = null;
	private static JButton btnmode4o = null;

	/**
	 * This is the default constructor
	 */
	public StartWindow() {
		super();
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ApplicationFacadeInterface facade = StartWindow.facadeInterface;
				try {
					if (c.isBusinessLogicLocal())
						facade.close();
				} catch (Exception e1) {

					System.out
							.println("Error: "
									+ e1.toString()
									+ " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static ApplicationFacadeInterface getBusinessLogic() {
		return facadeInterface;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(364, 371);
		setJMenuBar(getMenuBar_1());
		this.setContentPane(getJContentPane());
		this.setTitle("Rural Houses");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());

			JButton btnNewButton = new JButton("Query Availability");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mntmAvailability.doClick();
				}
			});
			btnNewButton.setBounds(97, 178, 167, 25);
			jContentPane.add(btnNewButton);

			JButton btnNewButton_1 = new JButton("Parametrized Search");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mntmParametricedSearch.doClick();
				}
			});
			btnNewButton_1.setBounds(97, 115, 167, 25);
			jContentPane.add(btnNewButton_1);

			btnmode1 = new JButton("View Booking");
			btnmode1.setBounds(97, 152, 167, 25);
			btnmode1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmViewBookings.doClick();
				}
			});
			jContentPane.add(btnmode1);

			btnmode2 = new JButton("Book Rural House");
			btnmode2.setBounds(97, 189, 167, 25);
			btnmode2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmBookRuralHouse.doClick();
				}
			});
			jContentPane.add(btnmode2);

			btnmode3 = new JButton("Book Activity");
			btnmode3.setBounds(97, 226, 167, 25);
			btnmode3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmBookActivity.doClick();
				}
			});
			jContentPane.add(btnmode3);

			btnmode4 = new JButton("Search Activity");
			btnmode4.setBounds(97, 263, 167, 25);
			btnmode4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmActivities.doClick();
				}
			});
			jContentPane.add(btnmode4);

			btnmode1.setVisible(false);
			btnmode2.setVisible(false);
			btnmode3.setVisible(false);
			btnmode4.setVisible(false);

			btnmode1o = new JButton("Add House");
			btnmode1o.setBounds(97, 152, 167, 25);
			btnmode1o.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmAddHouse.doClick();
				}
			});
			jContentPane.add(btnmode1o);

			btnmode2o = new JButton("Modify House");
			btnmode2o.setBounds(97, 189, 167, 25);
			btnmode2o.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmModifyHouse.doClick();
				}
			});
			jContentPane.add(btnmode2o);

			btnmode3o = new JButton("Add Activity");
			btnmode3o.setBounds(97, 226, 167, 25);
			btnmode3o.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmAddActivity.doClick();
				}
			});
			jContentPane.add(btnmode3o);

			btnmode4o = new JButton("Set Availability");
			btnmode4o.setBounds(97, 263, 167, 25);
			btnmode4o.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mntmSetAvailavility.doClick();
				}
			});
			jContentPane.add(btnmode4o);

			btnmode1o.setVisible(false);
			btnmode2o.setVisible(false);
			btnmode3o.setVisible(false);
			btnmode4o.setVisible(false);
		}
		return jContentPane;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		StartWindow a = new StartWindow();
		a.setVisible(true);

		try {

			c = ConfigXML.getInstance();

			System.setProperty("java.security.policy", c.getJavaPolicyPath());

			System.setSecurityManager(new RMISecurityManager());

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			c = configuration.ConfigXML.getInstance();
			if (c.isBusinessLogicLocal())
				facadeInterface = new FacadeImplementation();
			else {

				final String businessLogicNode = c.getBusinessLogicNode();
				// Remote service name
				String serviceName = "/" + c.getServiceRMI();
				// RMI server port number
				int portNumber = Integer.parseInt(c.getPortRMI());
				// RMI server host IP IP
				facadeInterface = (ApplicationFacadeInterface) Naming.lookup("rmi://" + businessLogicNode + ":" + portNumber + serviceName);
			}

	//		setClientMode();

		} catch (java.rmi.ConnectException e) {
			a.lblNewLabel.setText("No business logic: Run BusinessLogicServer first!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (java.rmi.NotBoundException e) {
			a.lblNewLabel.setText("No business logic: Maybe problems running BusinessLogicServer");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (com.db4o.ext.DatabaseFileLockedException e) {
			a.lblNewLabel.setText("Database locked: Do not run BusinessLogicServer or BusinessLogicServer!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (DB4oManagerCreationException e) {
			a.lblNewLabel.setText("No database: Run DB4oManagerServer first!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());

		} catch (Exception e) {
			a.lblNewLabel.setText("Error: " + e.toString());
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		}
		// a.pack();

	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Select option:");
			lblNewLabel.setBounds(0, 0, 175, 78);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}

	public static void setRegisteredMode() {
		mntmLogIn.setText("Log out");

		if (getLogin() instanceof Owner) {
			Owner o = (Owner) getLogin();

			mntmBookRuralHouse.setEnabled(true);
			mntmSetAvailavility.setEnabled(true);
			mntmSignIn.setEnabled(false);
			mntmAddHouse.setEnabled(true);
			mntmModifyHouse.setEnabled(true);
			mntmBookActivity.setEnabled(true);
			mntmViewBookings.setEnabled(true);
			mntmAddActivity.setEnabled(true);
			mntmActivities.setEnabled(true);
			mntmOwner.setEnabled(true);
			mntmClient.setEnabled(true);
			mntmOwner.doClick();
		} else {

			Client c = (Client) getLogin();
			mntmBookRuralHouse.setEnabled(true);
			mntmSignIn.setEnabled(false);
			mntmBookActivity.setEnabled(true);
			mntmViewBookings.setEnabled(true);
			mntmActivities.setEnabled(true);
			mntmClient.setEnabled(true);
			mntmClient.doClick();

		}
		mntmLogIn.removeActionListener(loginListener);

		loginListener = new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLogin(null);

				JOptionPane.showMessageDialog(null, "You logged off correctly",
						"", JOptionPane.PLAIN_MESSAGE);
				setClientMode();

			}

		};

		mntmLogIn.addActionListener(loginListener);
	}

	public static void setClientMode() {
		mntmBookRuralHouse.setEnabled(false);
		mntmSetAvailavility.setEnabled(false);
		mntmSignIn.setEnabled(true);
		mntmAddHouse.setEnabled(false);
		mntmModifyHouse.setEnabled(false);
		mntmAddActivity.setEnabled(false);
		mntmLogIn.setText("Log in");
		mntmViewBookings.setEnabled(false);
		mntmActivities.setEnabled(false);
		mntmBookActivity.setEnabled(false);
		mntmOwner.setEnabled(false);
		mntmClient.setEnabled(false);

		mntmLogIn.removeActionListener(loginListener);

		loginListener = new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
			}
		};

		btnmode1.setVisible(false);
		btnmode2.setVisible(false);
		btnmode3.setVisible(false);
		btnmode4.setVisible(false);

		btnmode1o.setVisible(false);
		btnmode2o.setVisible(false);
		btnmode3o.setVisible(false);
		btnmode4o.setVisible(false);
		mntmLogIn.addActionListener(loginListener);
	}

	/**
	 * @return the login
	 */
	public static User getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public static void setLogin(User login) {
		StartWindow.login = login;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnUser());
			menuBar.add(getMnMode());
			menuBar.add(getMnQuerys());
			menuBar.add(getMnBookings());
			menuBar.add(getMnOwner());
		}
		return menuBar;
	}

	private JMenu getMnUser() {
		if (mnUser == null) {
			mnUser = new JMenu("User");
			mnUser.add(getMntmLogIn());
			mnUser.add(getMntmSignIn());
		}
		return mnUser;
	}

	private JMenuItem getMntmLogIn() {
		if (mntmLogIn == null) {
			mntmLogIn = new JMenuItem("Log in");
		}

		mntmLogIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);

			}
		});
		return mntmLogIn;
	
	}

	private JMenuItem getMntmSignIn() {
		if (mntmSignIn == null) 
			mntmSignIn = new JMenuItem("Sign in");
		
		mntmSignIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SignInGUI();
				a.setVisible(true);

			}
		});
		return mntmSignIn;
	}

	private JMenu getMnQuerys() {
		if (mnQuerys == null) {
			mnQuerys = new JMenu("Querys");
			
			mnQuerys.add(getMntmAvailability());
			mnQuerys.add(getMntmParametricedSearch());
			mnQuerys.add(getSeparator_3());
			mnQuerys.add(getMntmActivities());
		}
		return mnQuerys;
	}

	private JMenuItem getMntmAvailability() {
		if (mntmAvailability == null) {
			mntmAvailability = new JMenuItem("Availability");
		}
		mntmAvailability.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new QueryAvailabilityGUI();
				a.setVisible(true);
			}
		});
		return mntmAvailability;
	}

	private JMenuItem getMntmParametricedSearch() {
		if (mntmParametricedSearch == null) {
			mntmParametricedSearch = new JMenuItem("Parametriced Search");
		}

		mntmParametricedSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ParametrizedSearchGUI();
				a.setVisible(true);

			}
		});
		return mntmParametricedSearch;
	}

	private JMenuItem getMntmActivities() {
		if (mntmActivities == null) {
			mntmActivities = new JMenuItem("Activities");
		}
		mntmActivities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new View_CancelBookingsGUI(true, 1);
				a.setVisible(true);

			}
		});
		return mntmActivities;
	}

	private JMenu getMnBookings() {
		if (mnBookings == null) {
			mnBookings = new JMenu("Bookings");
			mnBookings.add(getMntmBookRuralHouse());
			mnBookings.add(getMntmBookActivity());
			mnBookings.add(getSeparator());
			mnBookings.add(getMntmViewBookings());
		}
		return mnBookings;
	}

	private JMenu getMnOwner() {
		if (mnOwner == null) {
			mnOwner = new JMenu("Owner");
			mnOwner.add(getMntmAddHouse());
			mnOwner.add(getMntmModifyHouse());
			mnOwner.add(getSeparator_2());
			mnOwner.add(getMntmAddActivity());
			mnOwner.add(getSeparator_1());
			mnOwner.add(getMntmSetAvailavility());
		}
		return mnOwner;
	}

	private JMenuItem getMntmBookRuralHouse() {
		if (mntmBookRuralHouse == null) {
			mntmBookRuralHouse = new JMenuItem("Book Rural House");
		}
		mntmBookRuralHouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new BookRuralHouseGUI();
				a.setVisible(true);
			}
		});
		return mntmBookRuralHouse;
	}

	private JMenuItem getMntmBookActivity() {
		if (mntmBookActivity == null) {
			mntmBookActivity = new JMenuItem("Book Activity");
		}
		mntmBookActivity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new View_CancelBookingsGUI(true, 0);
				a.setVisible(true);

			}
		});
		return mntmBookActivity;
	}

	private JMenuItem getMntmViewBookings() {
		if (mntmViewBookings == null) {
			mntmViewBookings = new JMenuItem("View Bookings");
		}
		mntmViewBookings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new View_CancelBookingsGUI(false, 0);
				a.setVisible(true);

			}
		});
		return mntmViewBookings;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JMenuItem getMntmAddHouse() {
		if (mntmAddHouse == null) {
			mntmAddHouse = new JMenuItem("Add House");
		}
		mntmAddHouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new Add_ModifyHouseGUI(false, null);
				a.setVisible(true);
			}
		});
		return mntmAddHouse;
	}

	private JMenuItem getMntmAddActivity() {
		if (mntmAddActivity == null) {
			mntmAddActivity = new JMenuItem("Add Activity");
		}
		mntmAddActivity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AddActivityGUI();
				a.setVisible(true);
			}
		});
		return mntmAddActivity;
	}

	private JMenuItem getMntmModifyHouse() {
		if (mntmModifyHouse == null) {
			mntmModifyHouse = new JMenuItem("Modify House");
		}

		mntmModifyHouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<RuralHouse> houseList = null;
				try {
					if (getLogin() instanceof Owner) {
						Owner o = (Owner) getLogin();
						houseList = o.getRuralHouses();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (houseList.isEmpty() != true) {
					JFrame a = new ModifyHouseGUI(houseList);
					a.setVisible(true);
				} else if (houseList.isEmpty() == true) {
					JOptionPane.showMessageDialog(null,
							"You must add a house first", "Error",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		return mntmModifyHouse;
	}

	private JMenuItem getMntmSetAvailavility() {
		if (mntmSetAvailavility == null) {
			mntmSetAvailavility = new JMenuItem("Set Availavility");
		}

		mntmSetAvailavility
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Vector<RuralHouse> houseList = null;
						try {
							if (getLogin() instanceof Owner) {
								Owner o = (Owner) getLogin();
								houseList = o.getRuralHouses();
							}

						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (houseList.isEmpty() != true) {
							JFrame a = new SetAvailability2GUI(houseList);
							a.setVisible(true);
						} else if (houseList.isEmpty() == true) {
							JOptionPane.showMessageDialog(null,
									"You must add a house first", "Error",
									JOptionPane.PLAIN_MESSAGE);
						}

					}
				});
		return mntmSetAvailavility;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
		}
		return separator_1;
	}

	private JSeparator getSeparator_2() {
		if (separator_2 == null) {
			separator_2 = new JSeparator();
		}
		return separator_2;
	}

	private JSeparator getSeparator_3() {
		if (separator_3 == null) {
			separator_3 = new JSeparator();
		}
		return separator_3;
	}

	private JMenu getMnMode() {
		if (mnMode == null) {
			mnMode = new JMenu("Mode");
			mnMode.add(getMntmOwner());
			mnMode.add(getMntmClient());
		}
		return mnMode;
	}

	private JMenuItem getMntmOwner() {
		if (mntmOwner == null) {
			mntmOwner = new JMenuItem("Owner");
		}
		mntmOwner.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnmode1.setVisible(false);
				btnmode2.setVisible(false);
				btnmode3.setVisible(false);
				btnmode4.setVisible(false);

				btnmode1o.setVisible(true);
				btnmode2o.setVisible(true);
				btnmode3o.setVisible(true);
				btnmode4o.setVisible(true);
			}
		});
		return mntmOwner;
	}

	private JMenuItem getMntmClient() {
		if (mntmClient == null) {
			mntmClient = new JMenuItem("Client");
		}
		mntmClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnmode1.setVisible(true);
				btnmode2.setVisible(true);
				btnmode3.setVisible(true);
				btnmode4.setVisible(true);

				btnmode1o.setVisible(false);
				btnmode2o.setVisible(false);
				btnmode3o.setVisible(false);
				btnmode4o.setVisible(false);
			}
		});

		return mntmClient;
	}

} // @jve:decl-index=0:visual-constraint="0,0"

