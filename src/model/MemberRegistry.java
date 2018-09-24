package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MemberRegistry {
	private ArrayList<Member> members = new ArrayList<>();
	private DBHandler db = new DBHandler();

	public MemberRegistry() {
		try {
			members = db.readMemberDB();
		} catch (Exception e) {
			System.out.println("Error reading member database from file.");
		}
	}

	public void addMember(String name, String pNr) {
		Member m = new Member(name, pNr);
		members.add(m);
		db.saveDB();
	}

	public void deleteMember(int id) {
		Member m = getMember(id);
		members.remove(m);
		db.saveDB();
	}

	public void editMemberName(int id, String newName) {
		Member m = getMember(id);
		m.editName(newName);
		db.saveDB();
	}

	public void editMemberPnr(int id, String newPnr) {
		Member m = getMember(id);
		m.editPNr(newPnr);
		db.saveDB();
	}

	public Member getMember(int id) {
		for (Member m : members) {
			if (m.getId() == id)
				return m;
		}
		throw new NoSuchElementException();
	}

	public ArrayList<Member> getAllMembers() {
		return new ArrayList<Member>(members);
	}
}
