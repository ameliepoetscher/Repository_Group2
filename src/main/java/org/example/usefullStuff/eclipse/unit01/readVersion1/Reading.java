package org.example.usefullStuff.eclipse.unit01.readVersion1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Reading {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		readFile("./data/backup.txt");

	//	readFile("./data/names.txt");

	}

	public static void readFile(String datei) {
		Path path = Path.of(datei);
		
		List<String> content = null;
		try {
			content = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		content.forEach(System.out::println);
	}

}
