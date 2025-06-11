package org.example.usefullStuff.eclipse.unit02.lombok;

public class PersonLegacyApproach {
	
	private int id_nr;
	private String first_name;
	private String second_name;
	private String email;
	private int age;
	
	// 5 -> 20
	
	public int getId() {
		return id_nr;
	}

	public void setId(int id) {
		this.id_nr = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSecond_name() {
		return second_name;
	}

	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PersonLegacyApproach(int id, String first_name, String second_name, String email) {
		super();
		this.id_nr = id;
		this.first_name = first_name;
		this.second_name = second_name;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [id=" + id_nr + ", first_name=" + first_name + ", second_name=" + second_name + ", email=" + email
				+ "]";
	}
	
	public String getPersonAsCSV() {
		return ""+id_nr+";"+first_name+";"+second_name+";"+email;
	}
	
	
	
	
	
	
	

}
