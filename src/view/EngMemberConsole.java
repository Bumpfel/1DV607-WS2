package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;

public class EngMemberConsole {

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

	public Member displayEditMember(EngConsole console, Member m) {
		console.printSeparation();

		console.displayTitle("Edit \'" + m + "\'");

		Member tempMember = new Member(m.getName(), m.getPNr());
		String[] options = { "Edit name", "Edit personal code", "Back" };

		console.displayMenuOptions(options);
		
		int chosenOption = console.promptForValidMenuInput("", options.length - 1);

		switch(chosenOption) {
			case 1: 
				String name = promptForValidMemberName(console);
				if(name != null)
					tempMember.editName(name.trim());
				break;
			case 2:
				String pNr = promptForValidMemberPNr(console);
				if(pNr != null)
					tempMember.editPNr(pNr);
				break;
			case 0:
				return null;
		}
		return tempMember;
	}

	public void displayDeleteMember(EngConsole console, ArrayList<Member> membersList) {
		console.printSeparation();
		console.displayTitle("Delete Member");
	}

	public boolean displayMembersList(EngConsole console, ArrayList<Member> membersList) {
		console.printSeparation();
		console.displayTitle("List Members");
		
		String[] options = { "View compact list", "View verbose list", "Back" };
		
		console.displayMenuOptions(options);
		int chosenOption = console.getMenuInput();

		switch(chosenOption) {
			case 1:
                console.printSeparation();
                console.displayTitle("List Members - Verbose");
				displayMembersCompact(membersList);
				console.displayWait();
				break;
			case 2: 
                console.printSeparation();
				console.displayTitle("List Members - Verbose");
				displayMembersVerbose(membersList);
				console.displayWait();
				break;
			case 0:
				return false;
			default:
                console.displayInvalidMenuChoiceError();
		}
		return true;
    }
    

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
		System.out.println("  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .");
	}

	public void displayMemberInfo(EngConsole console, Member member) {
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
	
	public void displayMembersCompact(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
	}

    
	private String promptForValidMemberName(EngConsole console) { // Used by add and edit member
		String newName = displayNamePrompt(console);
		while(!Member.isValidName(newName)) {
			if(newName.equals("0"))
				return null;
            console.displayInvalidNameError();
			newName = displayNamePrompt(console);
			}
			return newName;
		}
		
	private String promptForValidMemberPNr(EngConsole console) { // Used by add and edit member
		String newPNr = displayPNrPrompt(console);
		while(!Member.isValidPNr(newPNr)) {
			if(newPNr.equals("0"))
				return null;
            console.displayInvalidPNrError();
			newPNr = displayPNrPrompt(console);
		}
		return newPNr;
    }
    

    public Member displayMemberSelection(EngConsole console, MemberRegistry memReg) {
		System.out.print("Enter member ID: ");
		int input = console.getInputInt();
		while(!memReg.memberExists(input)) {
			if(input == 0)
				return null;
            console.displayMemberDoesNotExistError();
			System.out.print("Enter member ID: ");
			input = console.getInputInt();
		}
        return memReg.getMember(input);
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