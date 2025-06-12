package org.example.usefullStuff.eclipse.unit04.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioRadioButtonExample extends JFrame implements ActionListener {

	// Step1 - declare your (new) components
	JRadioButton rb_junior;
	JRadioButton rb_senior;
	JRadioButton rb_admin;
	JRadioButton rb_test;
	ButtonGroup rb_roles;
	JTextField tf_input;
	JButton bt_read;
	JButton bt_write;
	JButton bt_update;
	JButton bt_test;

	public RadioRadioButtonExample() {
		initComponents(); // 2nd step
		addComponents();

		setLayout(new FlowLayout());

		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addComponents() {
		add(rb_junior);
		add(rb_senior);
		add(rb_admin);
		add(rb_test);
		add(tf_input);
		add(bt_read);
		add(bt_update);
		add(bt_write);
		add(bt_test);
	}

	private void initComponents() {
		rb_junior = new JRadioButton("Junior");
		rb_senior = new JRadioButton("Senior");
		rb_admin = new JRadioButton("Admin");
		rb_test = new JRadioButton("Test");
		
		rb_junior.setActionCommand("1");
		rb_senior.setActionCommand("2");
		rb_admin.setActionCommand("3");
		rb_test.setActionCommand("4");
		
		
		
		rb_roles = new ButtonGroup();
		rb_roles.add(rb_admin);
		rb_roles.add(rb_junior);
		rb_roles.add(rb_senior);
		rb_roles.add(rb_test);
		
		tf_input = new JTextField(10);
		
		bt_read = new JButton("Read");
		bt_update = new JButton("Update");
		bt_write = new JButton("Write");
		bt_test = new JButton("Test");

		bt_read.addActionListener(this);
		bt_update.addActionListener(this);
		bt_write.addActionListener(this);
		bt_test.addActionListener(this);
	}

	public static void main(String[] args) {
		new RadioRadioButtonExample();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_read) {

			String temp = rb_roles.getSelection().getActionCommand();
			tf_input.setText(temp);
			
		} else if (e.getSource() == bt_update) {
			System.out.println("update Button was clicked");
		} else if (e.getSource() == bt_write) {
			System.out.println("write Button was clicked");
		} else if (e.getSource() == bt_test) {
			System.out.println("test Button was clicked");
		}

	}

}
