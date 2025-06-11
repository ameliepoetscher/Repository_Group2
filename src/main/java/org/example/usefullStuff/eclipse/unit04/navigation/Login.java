package org.example.usefullStuff.eclipse.unit04.navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Login extends JFrame {
	
	static ArrayList<User> user_db = new ArrayList<>();
	static int ATTEMPTS = 0;
	static final int MAX_ATTEMPTS = 3;

	JTextField tf_benutzer;
	JPasswordField tf_password;
	JButton bt_submit;

	public Login() {

		setLayout(new FlowLayout()); // override
		tf_benutzer = new JTextField(10);
		tf_password = new JPasswordField(10);
		bt_submit = new JButton("Login");

		JLabel lb_user = new JLabel("Benutzername");
		JLabel lb_password = new JLabel("Passwort");
		JLabel error_msg = new JLabel("");

		add(lb_user);
		add(tf_benutzer);
		add(lb_password);
		add(tf_password);
		add(bt_submit);
		add(error_msg);

		bt_submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String currentUserName = tf_benutzer.getText();
				String currentPassword = tf_password.getText();

				if (currentUserName.trim().length() == 0 && currentPassword.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "User input is invalid");
					return;
				}

				 // DB-Entries aval. 
				 // Container (junior, senior, ...)
				boolean hasFound = false;
				Role tempRole = null;
				
				for (User user : user_db) {
					String db_user = user.getUsername();
					String db_pwd = user.getPassword();
					
					if(db_user.equals(currentUserName) && db_pwd.equals(currentPassword)) {
						tempRole = user.getRole();
						JOptionPane.showMessageDialog(null, "you logged in ..." + db_user);
						hasFound = true;
					}
				}
				
				if(tempRole == Role.SENIOR) {
					System.out.println("Senior stage");
						dispose();
					new SeniorView(currentUserName);
				}
				else if(tempRole == Role.JUNIOR) {
					System.out.println("Junior stage");
					dispose();
				new JuniorView(currentUserName);
				}
				else if(tempRole == Role.ADMIN) {
					System.out.println("Admin stage");
				}
				
				if(!hasFound) {
					ATTEMPTS++;
					error_msg.setText("password and or user is invalid");
					
					if(ATTEMPTS >= 1 && ATTEMPTS < 3) {
						JOptionPane.showMessageDialog(null, "only " + (MAX_ATTEMPTS-ATTEMPTS) + " attempts left");

					}

					tf_benutzer.setText("");
					tf_password.setText("");
				}
				
				if(ATTEMPTS == MAX_ATTEMPTS) {
					// block the user ... 
					JOptionPane.showMessageDialog(null, "you reached your daily login attempts, app will be closed ...");
					System.exit(0);
				}
				
				System.out.println(currentPassword);
				System.out.println(currentUserName);

			}
		});

		// basic setup
		setTitle("My first application");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		User junior = new User("Junior", "Junior");
		
		junior.setIslocked(false);
		junior.setRole(Role.JUNIOR);
		user_db.add(junior);
		

		User senior = new User("Senior", "Senior");
		senior.setIslocked(false);
		senior.setRole(Role.SENIOR);
		user_db.add(senior);
		
		//user_db.forEach(System.out::println);
		

		new Login();
	}

}
