package view;

public interface ViewInterface {
	
	public void displayWelcomeMsg();
	public void displayMenu();
	public String getInput();
	public void displayMembersVerbose();
	public void DisplayMembersCompact();
	public void displayMemberReg();
	public void displayError(String e);
}
