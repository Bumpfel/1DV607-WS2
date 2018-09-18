
public class Program {

	public static void main(String[] args) {
		model.MemberRegistry regM = new model.MemberRegistry();
		view.ViewInterface view = new view.Console();
		controller.User controller = new controller.User(view);
		
		controller.startApplication();

	}

}
