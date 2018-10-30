package authentication;

import controller.Admin;
import controller.User;
import model.MemberRegistry;
import view.ViewInterface;

public class UserFactory {
	
	public User createGuest(ViewInterface view, MemberRegistry memberRegistry) {
		return new User(memberRegistry,view);
	}
	
	protected User createAdmin(ViewInterface view, MemberRegistry memberRegistry) {
		return new Admin(memberRegistry,view);
	}
}