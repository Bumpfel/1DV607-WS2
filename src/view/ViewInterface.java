package view;

import java.util.ArrayList;

public interface ViewInterface {
	
	public void displayWelcomeMsg();
	public void displayMenu(ArrayList<String> options);
	public String getInputString();
	public int getInputInt();
	public int getInputInt(int minimum, int maximum);
	public String[] displayAddMember();
	public int displayEditMemberMenu();
	public String displayEditName();
	public String displayEditPnr();
	public int displayEnterMemberId();
	public void displayMembersVerbose();
	public void displayMembersCompact();
	public void displayMemberReg();
	public void displayMessage(String m);
	public void displayError(String e);
}
