import controller.Admin;

public class Program {

	public static void main(String[] args) {
		model.MemberRegistry regM = new model.MemberRegistry();
		view.ViewInterface view = new view.EngConsole();
		controller.User controller = new Admin(regM, view);
		
		controller.startApplication();

	}
}