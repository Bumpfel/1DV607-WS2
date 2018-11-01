package authentication;

import java.io.IOException;

import controller.User;
import model.MemberRegistry;
import view.ViewInterface;

public final class Authentication {
	static boolean adminAuthenticated = false;

	//"Static" class
	private Authentication() {
		
	}
	
	public static boolean adminAuthenticate(String inputPw) throws IOException {
    	//Reads password from pw.txt file
		Password adminPw = new Password();
		
		//Reads user input password
		Password userPw = new Password(inputPw);
		
		if (adminPw.isEqual(userPw)) {
			adminAuthenticated = true;
			return true;
		}
		
		return false;
	}

	public static void adminLogOut(MemberRegistry memberRegistry, ViewInterface view) {
		adminAuthenticated = false;

		User guest = new UserFactory().createGuest(view, memberRegistry);
		guest.startApplication(false);
	}
	
	public static void runAdmin(MemberRegistry memberRegistry, ViewInterface view) {
		if (adminAuthenticated) {
			User admin = new UserFactory().createAdmin(view, memberRegistry);
			admin.startApplication(false);
		}
	}
}