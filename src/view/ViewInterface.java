package view;

import java.util.ArrayList;

public interface ViewInterface {
	
	public void displayWelcomeMsg();
	public void displayMenu(ArrayList<String> options);
	public String getInputString();
	public int getInputInt(int minimum, int maximum);
	public String[] displayAddMember();
	public void displayMembersVerbose();
	public void DisplayMembersCompact();
	public void displayMemberReg();
	public void displayError(String e);
}
