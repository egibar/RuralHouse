package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

@SuppressWarnings("serial")
public class Client extends User implements Serializable {

	

	public Client(String name, String login, String password) {
		super(name, login, password);

	}

	public Client(String name, String login, String password, String bankAccount) {
		super(name, login, password, bankAccount);

	}

	

}
