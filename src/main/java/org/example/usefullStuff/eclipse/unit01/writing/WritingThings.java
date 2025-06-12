package org.example.usefullStuff.eclipse.unit01.writing;

import org.example.usefullStuff.eclipse.unit01.model.Persona;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class WritingThings {

	public static void main(String[] args) throws IOException {

		Path path = Path.of("./data/abc.txt");

		ArrayList<String> abc = new ArrayList<>();
		abc.add("a");
		abc.add("a");
		abc.add("a");

		Persona p = new Persona(1, "Michael", "Deutsch", "whatever");
		abc.add(p.getPersonAsCSV());
		System.out.println(p.getPersonAsCSV());
		Desktop.getDesktop().open(new File("./data/abc.txt"));

		try {
			Files.write(path, abc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
