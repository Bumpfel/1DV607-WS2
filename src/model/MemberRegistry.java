package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MemberRegistry {
	ArrayList<Member> members = new ArrayList<>();
	
	MemberRegistry() {
		
	}
	
	void addMember(String name, String pNr) {
		Member m = new Member(name, pNr);
		members.add(m);
	}
	
	void deleteMember(int position) {
		members.remove(position);
	}
	
	Member getMember(int id) {		
		for (Member m : members) {
			if (m.getId() == id) {
				 return m;
			}
		}
		throw new NoSuchElementException();		
	}
	
	void editMemberName(int id, String newName) {		
		for (Member m : members) {
			if (m.getId() == id) {
				m.editName(newName);
			}
			else if ((members.indexOf(m) == members.size() - 1) && (m.getId() != id)) {
				throw new NoSuchElementException();
			}
		}		
	}
	
	void editMemberPnr(int id, String newPnr) {		
		for (Member m : members) {
			if (m.getId() == id) {
				m.editName(newPnr);
			}
			else if ((members.indexOf(m) == members.size() - 1) && (m.getId() != id)) {
				throw new NoSuchElementException();
			}
		}		
	}
	
	ArrayList<Member> getAllMembers() {		
		return new ArrayList<Member>(members);
	}
}
