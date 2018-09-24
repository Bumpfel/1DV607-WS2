package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBHandler {    
    
    ArrayList<Member> readMemberDB() throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper oMapper = new ObjectMapper();
		File inputFile = new File("res/db.txt");
		
		return oMapper.readValue(inputFile, new TypeReference<ArrayList<Member>>(){});
	}
	
	void saveDB() {
		ObjectMapper oMapper = new ObjectMapper();
		File outputFile = new File("res/db.txt");
        MemberRegistry mr = new MemberRegistry();        
		ArrayList<Member> members = mr.getAllMembers();
        try {
		oMapper.writeValue(outputFile, members);
		} catch (Exception e) {
			System.out.println("Error saving to database.");
		}
	}
}