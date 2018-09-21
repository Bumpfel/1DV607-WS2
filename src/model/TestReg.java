package model;

import java.util.Scanner;

public class TestReg {
    private static Scanner input = new Scanner(System.in);
    private static MemberRegistry mr = new MemberRegistry();

    public static void main(String[] args) {
        displayMenu();
    }

    static void displayMenu() {        
        System.out.println("1. Add member");
        System.out.println("2. Delete member");
        System.out.println("3. Get member");
        System.out.println("4. Edit member name");
        System.out.println("5. Edit member PNR");
        System.out.println("6. Print all members");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(input.nextLine());

        switch (choice) {
        case 1:
            addMember();
            break;
        case 2:
            deleteMember();
            break;
        case 3:
            getMember();
            break;
        case 4:
            editName();
            break;
        case 5:
            editPnr();
            break;
        case 6:
            getAll();
            break;
        default:
            System.out.println("Invalid");
            break;
        }
    }

    private static void getAll() {
    	for (Member m : mr.getAllMembers())
            System.out.println("id " + m.getId() + ":\tname: " + m.getName() + "\tpnr: " + m.getPNr());
    	displayMenu();
    }

    private static void editPnr() {
        for (Member m : mr.getAllMembers())
            System.out.println("id: " + m.getId() + "\t" + m.getName());
        System.out.println("Enter id: ");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Enter new personal number: ");
        String pnr = input.nextLine();
        mr.editMemberPnr(id, pnr);
        displayMenu();
    }

    private static void editName() {
        for (Member m : mr.getAllMembers())
            System.out.println("id: " + m.getId() + "\t" + m.getName());
        System.out.println("Enter id: ");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Enter new name: ");
        String name = input.nextLine();
        mr.editMemberName(id, name);
        displayMenu();
    }

    private static void getMember() {
    	System.out.print("Enter id: ");
    	int id = Integer.parseInt(input.nextLine());
    	Member m = mr.getMember(id);
    	System.out.println("ID:\t" + m.getId());
    	System.out.println("Name:\t" + m.getName());
    	System.out.println("Pnr:\t" + m.getPNr());
    	displayMenu();
    }

    private static void deleteMember() {
    	System.out.print("Enter id: ");
    	int id = Integer.parseInt(input.nextLine());
    	mr.deleteMember(id);
    	displayMenu();
    }

    private static void addMember() {
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Personal number: ");
        String pnr = input.nextLine();
        mr.addMember(name, pnr);
        displayMenu();
    }

}