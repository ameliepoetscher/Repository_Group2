package org.example.usefullStuff.eclipse.unit04.navigation;

public class SeniorView extends BaseView {

	public SeniorView(String currentUserName) {
		super(currentUserName + "(Senior)");
	}

	public static void main(String[] args) {
		new SeniorView("Michael");
	}

}
