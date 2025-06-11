package org.example.usefullStuff.eclipse.unit02.lombok;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonWithLombokAnnotation {
	
	private int id;
	@NonNull
	private String first_name;
	@NonNull
	private String second_name;
	private String email;
	private int age;
	private String nickname;
	
	

}
