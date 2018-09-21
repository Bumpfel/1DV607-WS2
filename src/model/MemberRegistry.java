package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MemberRegistry {
	ArrayList<Member> members = new ArrayList<>();
	
	public MemberRegistry() {
		try {
			members = readMemberDB();						
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}	
		
	public void addMember(String name, String pNr) {
		Member m = new Member(name, pNr);
		members.add(m);
		try {
			writeToFile("res/db.txt", members);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteMember(int id) {
		for (Member m : members) {
			if (m.getId() == id) {
				 members.remove(m);
				 try {
						writeToFile("res/db.txt", members);
					 } catch (Exception e) {
						System.out.println("Something went horribly wrong while saving the changes.");					
					 }
				 return;
			}		
		}		
		throw new NoSuchElementException();
	}
	
	public Member getMember(int id) {		
		for (Member m : members) {
			if (m.getId() == id) {
				 return m;
			}
		}
		throw new NoSuchElementException();		
	}
	
	public void editMemberName(int id, String newName) {	
		for (Member m : members) {
			if (m.getId() == id) {
				m.editName(newName);				
				try {
					writeToFile("res/db.txt", members);
				} catch (Exception e) {
					System.out.println("Something went horribly wrong while saving the changes.");
				}
				return;
			}									
		}
		throw new NoSuchElementException();
	}
	
	public void editMemberPnr(int id, String newPnr) {		
		for (Member m : members) {
			if (m.getId() == id) {
				m.editPNr(newPnr);				
				try {
					writeToFile("res/db.txt", members);
				} catch (Exception e) {
					System.out.println("Something went horribly wrong while saving the changes.");
				}
				return;
			}									
		}
		throw new NoSuchElementException();
	}
	
	public ArrayList<Member> getAllMembers() {		
		return new ArrayList<Member>(members);
	}
	
	static ArrayList<Member> readMemberDB() throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper oMapper = new ObjectMapper();
		File inputFile = new File("res/db.txt");
		
		return oMapper.readValue(inputFile, new TypeReference<ArrayList<Member>>(){});
	}
	
	static void writeToFile(String filePath, Collection<?> col) throws IOException, JsonMappingException, JsonGenerationException {
		ObjectMapper oMapper = new ObjectMapper();
		File outputFile = new File(filePath);
		
		oMapper.writeValue(outputFile, col);
	}
}
