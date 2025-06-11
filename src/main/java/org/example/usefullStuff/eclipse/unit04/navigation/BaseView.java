package org.example.usefullStuff.eclipse.unit04.navigation;

import javax.swing.*;
import java.time.LocalTime;

public class BaseView extends JFrame {
	
	
	LocalTime currentTime;
	
	public BaseView(String currentUserName) {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		LocalTime currentTime= LocalTime.now();
	
		int currentHour = currentTime.getHour();
		System.out.println(currentHour);
		
		String greeting ="";
		if(currentHour >= 6 && currentHour < 17) {
			greeting = "Good day ";
		}else {
			greeting = "Good night ";
		}
		
		setTitle(greeting + currentUserName);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}



}
