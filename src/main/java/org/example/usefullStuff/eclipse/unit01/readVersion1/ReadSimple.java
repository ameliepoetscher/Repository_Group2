package org.example.usefullStuff.eclipse.unit01.readVersion1;


import org.example.usefullStuff.eclipse.unit02.lombok.PersonWithLombokAnnotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadSimple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<PersonWithLombokAnnotation> allPersons = readPersonAsList();
		allPersons.forEach(System.out::println);

	}

	public static List<PersonWithLombokAnnotation> readPersonAsList() {

		ArrayList<PersonWithLombokAnnotation> persons = new ArrayList<>();

		String path = "./data/names.txt";
		try {
			Scanner scan = new Scanner(new File(path));

			while (scan.hasNext()) {
				String temp = scan.nextLine();
				// System.out.println(temp);
				String[] parts = temp.split(","); // container in String-Format
				// System.out.println(Arrays.toString(parts));

				int id = Integer.valueOf(parts[0]);
				String first_name = parts[1];
				String second_name = parts[2];
				String email = parts[3];
				int age = id+3;

				PersonWithLombokAnnotation currentPerson = new PersonWithLombokAnnotation(id, first_name, second_name, email, age, email);
				persons.add(currentPerson);
				// System.out.println(currentPerson);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return persons;
	}

}
