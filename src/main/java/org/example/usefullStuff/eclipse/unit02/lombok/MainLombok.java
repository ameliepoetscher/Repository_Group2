package org.example.usefullStuff.eclipse.unit02.lombok;

public class MainLombok {

	public static void main(String[] args) {


		PersonWithLombokAnnotation sample = new PersonWithLombokAnnotation("John", "Doe");
		sample.setId(1);
		System.out.println("Name: " + sample.getFirst_name());
		System.out.println(sample);

	}

}
