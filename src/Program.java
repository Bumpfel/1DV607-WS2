import authentication.UserFactory;

public class Program {

	public static void main(String[] args) {
		model.MemberRegistry regM = new model.MemberRegistry();
		view.ViewInterface view = new view.EngConsole();
		new UserFactory().createGuest(view, regM).startApplication(true);
	}
}