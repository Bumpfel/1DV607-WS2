package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;

public interface ViewInterface {
	
//	public enum Msg {};
//	public enum MenuOptions {
//		EditName(""),
//		EditPNr(""),
//		CompactList(""),
//		VerboseList(""),
//		;
//		
//		private String option;
//		
//		private MenuOptions(String str) {
//		}
//		
//		public String getOption() {
//			return option;
//		}
//		
//	};
	
	public void displayMainMenu();
	public int  displaySubMenu(String...options);
	public int displayEditMemberMenu();
	
	public String[] getEditMemberOptions();
	
	public String getInputString();
	public int getInputInt();
	public int getInputInt(int minimum);
	public int getInputInt(int minimum, int maximum);

	public String[] displayAddMember();
	public int displayEnterMemberId();
	public String displayEditName();
	public String displayEditPnr();
	public Boat displayEnterBoat(ArrayList<Boat> boats);
	
	public void displayDeleteMember();
	public int displayViewMemberListMenu();
	public void displayMembersVerbose(ArrayList<Member> list);
	public void displayMembersCompact(ArrayList<Member> list);
	
	public Boat.BoatType displayEnterBoatType();
	public double displayBoatEnterSize();
	
	public void displayWelcomeMsg();
	public void displayBoatConfirm();
	public void displayEditBoatTypeConfirm();
	public void displayEditBoatSizeConfirm();
	public void displayDeleteBoatConfirm();
	public void displayNameChangedConfirm();
	public void displayMemberDoesNotExistMsg();
	
	public void displayMessage(String m);
	public void displayError(String e);
	
	public void displayViewMember(Member member);
	public void displayBoatListCompact(Member currentMember);
	public int displayEditBoatMenu();
	
}
