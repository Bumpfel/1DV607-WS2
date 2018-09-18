package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Member;

public class DBobjectMapper {

	/*public static void main(String[] args) throws Exception {

		ArrayList<Member> tempMembers = new ArrayList<>();
		tempMembers.add(new Member("Per", "18585555585"));
		tempMembers.add(new Member("Kalle", "28585155585"));
		tempMembers.add(new Member("Mats", "3853555585"));
		tempMembers.add(new Member("Peter", "48545555585"));
				
		ArrayList<Member> members = readMemberDB();
		
		for(Member mem : members) {
			System.out.println(mem.getName() + " " + mem.getPNr() + " has " + mem.getBoats().size() + " boats.");
		}

		
		writeToFile("res/db-temp.txt", tempMembers);
	}*/

	public static ArrayList<Member> readMemberDB() throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper oMapper = new ObjectMapper();
		File inputFile = new File("res/db.txt");
		
		return oMapper.readValue(inputFile, new TypeReference<ArrayList<Member>>(){});
	}
	

	static void writeToFile(String filePath, Collection<?> col) throws IOException, JsonMappingException, JsonGenerationException {
		ObjectMapper oMapper = new ObjectMapper();
		File outputFile = new File(filePath);
		
		oMapper.writeValue(outputFile, col);
	}



	// Casting
//	ArrayList<Object> objMembers = readFile("db.txt");
//	ArrayList<Member> members = new ArrayList<>();
//	for(Object objMember : objMembers) {
//		if (objMember instanceof Member)
//			members.add((Member) objMember);
//	}
}
