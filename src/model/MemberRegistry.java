package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MemberRegistry {
	private ArrayList<Member> members = new ArrayList<>();
	
	public MemberRegistry() {
		try {
			members = readMemberDB();
			Member lastMember = members.get(members.size() - 1);
			lastMember.setNextId(lastMember.getId() + 1);
		} catch (Exception e) {
			System.out.println("Error reading member database from file.");
		}
	}

	public void addMember(String name, String pNr) {
		Member m = new Member(name, pNr);
		members.add(m);
		saveDB(); //TODO ta bort
	}

	public void deleteMember(int id) {
		Member m = getMember(id);
		members.remove(m);
		saveDB(); //TODO ta bort
	}

	public void editMemberName(int id, String newName) {
		Member m = getMember(id);
		m.editName(newName);
		saveDB(); //TODO ta bort
	}

	public void editMemberPnr(int id, String newPnr) {
		Member m = getMember(id);
		m.editPNr(newPnr);
		saveDB(); //TODO ta bort
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

	private ArrayList<Member> readMemberDB() throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper oMapper = new ObjectMapper();
		File inputFile = new File("res/db.txt");

		return oMapper.readValue(inputFile, new TypeReference<ArrayList<Member>>() {
		});
	}

	public void saveDB() {
		ObjectMapper oMapper = new ObjectMapper();
		File outputFile = new File("res/db.txt");		
		try {
			oMapper.writeValue(outputFile, members);
		} catch (Exception e) {
			System.out.println("Error saving to database.");
		}
	}
}
