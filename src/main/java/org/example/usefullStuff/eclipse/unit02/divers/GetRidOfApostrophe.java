package org.example.usefullStuff.eclipse.unit02.divers;

public class GetRidOfApostrophe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String temp = "\"Gasthof Singula\"";
		System.out.println(temp);
		temp = temp.replace("\"", "");
		System.out.println(temp);

	}

}
