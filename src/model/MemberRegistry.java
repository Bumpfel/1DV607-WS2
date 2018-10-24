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
		
	private int nextId;
	
	public MemberRegistry() {
		try {
			members = readMemberDB();
			if (members.size() > 0) {
				Member lastMember = members.get(members.size() - 1);
				nextId = lastMember.getId() + 1;
			}
		} catch (Exception e) {
		}
	}

	public void addMember(Member m) {
		members.add(new Member(m, nextId));
		nextId ++;
		saveDB();
	}

	public void deleteMember(Member m) {
		members.remove(m);
		saveDB();
	}

	public Member getMember(int id) {
		for (Member m : members) {
			if (m.getId() == id)
				return m;
		}
		throw new NoSuchElementException();
	}

	public boolean memberExists(int id) {
		try {
			getMember(id);
			return true;
		}
		catch(NoSuchElementException e) {
			return false;
		}
	}

	public ArrayList<Member> getAllMembers() {
		return new ArrayList<Member>(members);
	}

	private ArrayList<Member> readMemberDB() throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper oMapper = new ObjectMapper();
		File inputFile = new File("db.txt");

		return oMapper.readValue(inputFile, new TypeReference<ArrayList<Member>>() {
		});
	}

	public void saveDB() {
		ObjectMapper oMapper = new ObjectMapper();
		File outputFile = new File("db.txt");
		try {
			oMapper.writeValue(outputFile, members);
		}
		catch (Exception e) {
		}
	}
}
