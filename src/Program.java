import controller.*;

public class Program {

	public static void main(String[] args) {
		model.MemberRegistry regM = new model.MemberRegistry();
		view.ViewInterface view = new view.EngConsole();
		User controller = new User(regM, view);
		
		controller.startApplication();

	}
}