package org.example.usefullStuff.eclipse.unit01.writing;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class WritePlusAppend {

	public static void main(String[] args) {

		//#1 Path is required
		String userinput = JOptionPane.showInputDialog("What should be the name of the file?");
		
		String fileTyp = ".txt";
		String location ="./data/"+userinput+fileTyp;
		System.out.println(location);
		
		Path path = Path.of(location);
		
		// #2 some kind of data
		ArrayList<String> content = new ArrayList<>();
		content.add("A");
		content.add("b");
		content.add("b");
		content.add("b");
		content.add("b");
		
		// #3 write file
		
		try {
			if(Files.exists(path)) {
				Files.write(path, content, StandardOpenOption.APPEND);
				System.out.println("note: something has been added to an existing document");
			}else {
				Files.write(path, content);
				System.out.println("note: a new document has been created");


			}
			
			
			int choice = JOptionPane.showConfirmDialog(null, "You wanna see the written file?");
			//System.out.println(choice);
			
			if(choice == 0) {
				Desktop.getDesktop().open(new File(path.toString()));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// #3a try/catch must be added
		
		// #3b open the file right now
		

	}

}
