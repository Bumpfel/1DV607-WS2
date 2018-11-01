package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;

public class EngMemberConsole {

	//-----------
	// MainMenu Actions
	//--------

    public Member displayAddMember(EngConsole console) {
		console.printSeparation();
		console.displayTitle("Add Member");
		
		String newName = promptForValidMemberName(console);
		if(newName == null) {
			return null;
		}
		String newPNr = promptForValidMemberPNr(console);
		if(newPNr == null) {
			return null;
		}

		return new Member(newName, newPNr);
	}

	//TODO old menu standard
	public Member displayEditMember(EngConsole console, Member m) {
		console.printSeparation();

		console.displayTitle("Edit \'" + m + "\'");

		Member tempMember = new Member(m.getName(), m.getPNr());
		String[] options = { 
			"Edit name",
			"Edit personal code",
			"Back"
		};

		console.displayMenuOptions(options);
		
		int chosenOption = console.promptForValidMenuInput("", options.length - 1);

		if(chosenOption == 1) {
			String name = promptForValidMemberName(console);
			if(name != null)
				tempMember.editName(name.trim());
		}
		else if(chosenOption == 2) {
			String pNr = promptForValidMemberPNr(console);
			if(pNr != null)
				tempMember.editPNr(pNr);
		}
		else if(chosenOption == 0)
			return null;
		
		return tempMember;
	}

	public void displayDeleteMember(EngConsole console, ArrayList<Member> membersList) {
		console.printSeparation();
		console.displayTitle("Delete Member");
	}

	public boolean displayMembersList(EngConsole console, ArrayList<Member> membersList) {
		String[] options = { 
			"View compact list",
			"View verbose list",
			"Back"
		};
		
		int choice = console.createMenu("List Members", "", options);

		if(choice == 1) {
			console.printSeparation();
			console.displayTitle("List Members - Verbose");
			displayMembersCompact(membersList);
			console.displayWait();
		}
		else if(choice == 2) { 
			console.printSeparation();
			console.displayTitle("List Members - Verbose");
			displayMembersVerbose(membersList);
			console.displayWait();
		}
		else if(choice == 0)
			return false;
		else
			console.displayInvalidMenuChoiceError();

		return true;
	}
	

	//----------------
	// Used locally or by EngConsole
	//------------

    private void displayMemberFullInformation(Member member) {
		System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getPNr());
		ArrayList<Boat> memberBoats = member.getBoats();
		if(memberBoats.size() > 0) {
			System.out.format("\n%-11s %-26s %-16s \n"," > Boats", "Type", "Size (m)");
			for(Boat boat : memberBoats) {
				System.out.format("%-12s %-26s %.16s \n", "", boat.getType(), boat.getSize());
			}
			System.out.println(" Member has " + memberBoats.size() + " boat(s) registered in total");
		}
		else {
			System.out.println(" Member has no registered boats");
		}
		System.out.println("  -   -   -");
		// System.out.println("  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .");
	}

	void displayMemberInfo(EngConsole console, Member member) {
		console.printSeparation();

		console.displayTitle("View Member");
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");
		displayMemberFullInformation(member);

		console.displayWait();
	}	
	
	private void displayMembersVerbose(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MEMBER ID", "NAME", "PERSONAL NUMBER");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			displayMemberFullInformation(member);
		}
	}
	
	void displayMembersCompact(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
	}

    
	private String promptForValidMemberName(EngConsole console) { // Used by add and edit member
		String newName = displayNamePrompt(console);
		while(!Member.isValidName(newName)) {
			if(newName.equals(console.GO_BACK_INPUT))
				return null;
            console.displayInvalidNameError();
			newName = displayNamePrompt(console);
			}
			return newName;
		}
		
	private String promptForValidMemberPNr(EngConsole console) { // Used by add and edit member
		String newPNr = displayPNrPrompt(console);
		while(!Member.isValidPNr(newPNr)) {
			if(newPNr.equals(console.GO_BACK_INPUT))
				return null;
            console.displayInvalidPNrError();
			newPNr = displayPNrPrompt(console);
		}
		return newPNr;
    }
    

    Member displayMemberSelection(EngConsole console, MemberRegistry memReg) {
		int input = displayIdPrompt(console);
		while(!memReg.memberExists(input)) {
			if(input == console.GO_BACK_INPUT_INT)
				return null;
            console.displayMemberDoesNotExistError();
			input = displayIdPrompt(console);
		}
        return memReg.getMember(input);
	}
	
	private int displayIdPrompt(EngConsole console) {
		System.out.print("Enter member ID: ");
		return console.getInputInt();
	}
        
    private String displayNamePrompt(EngConsole console) {
        System.out.print("Enter new name: ");
        return console.getInput();
    }
    
    private String displayPNrPrompt(EngConsole console) {
        System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
        return console.getInput();
    }
}