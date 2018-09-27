package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;

public interface ViewInterface {
	
	public String getInputString();
	public int getInputInt();
	public int getInputInt(int minimum);
	public int getInputInt(int minimum, int maximum);
	public int displayEnterMemberId();
	public void displayMessage(String m);
	public void displayError(String e);
	
	public void displayWelcomeMsg();
	public void displayMainMenu(ArrayList<String> options);
	
	public String[] displayAddMember();
	
	public int displayEditMemberMenu();
	public String displayEditName();
	public String displayEditPnr();
	
	public void displayViewMember(Member member);
	
	public void displayDeleteMember();
	
	public int displayViewMemberListMenu();
	public void displayMembersVerbose(ArrayList<Member> list);
	public void displayMembersCompact(ArrayList<Member> list);
	
	public Boat.BoatType displayEnterBoatType();
	public double displayBoatEnterSize();
	public void displayBoatConfirm();
	
	public void displayEditBoatTypeConfirm();
	public void displayEditBoatSizeConfirm();
	
	public void displayBoatListCompact(Member currentMember);
	public Boat displayEnterBoat(ArrayList<Boat> boats);
	public int displayEditBoatMenu();
	
	public void displayDeleteBoatConfirm();
}
