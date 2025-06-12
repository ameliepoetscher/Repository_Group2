package org.example.usefullStuff.eclipse.unit04.navigation;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

	@NonNull
	String username;
	@NonNull
	String password;

	boolean islocked = false;
	Role role = Role.JUNIOR;

}
