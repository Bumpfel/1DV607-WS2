package authentication;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream; //For potential change password function
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
	private byte[] hash;
	
	//Reads password from file
	public Password() {
		File pwFile = new File("pw.txt");
		hash = readFile(pwFile);
	}
	
	//Creates hash from string
	public Password(String password) {
			hash = digest(password);
	}
	
	public boolean isEqual(String input) {
		byte[] passwordDigest = digest(input);

		return MessageDigest.isEqual(hash,passwordDigest);
	}
	
	public boolean isEqual(Password input) {
		return MessageDigest.isEqual(hash,input.hash);
	}
	
	private byte[] digest(String input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] stringBytes;
			stringBytes = input.getBytes("UTF-8");
			
			return messageDigest.digest(stringBytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//If hashing is not possible
		return null;
	}

	//Reads bytes from the input file
	private byte[] readFile(File f) throws IllegalStateException {
		try {
			FileInputStream stream;
			stream = new FileInputStream(f);

			byte[] byteArray = new byte[32];
			stream.read(byteArray);
			stream.close();

			return byteArray;
		}
		catch (IOException e) {
			throw new IllegalStateException("Error: File not found!");
		}
	}
	
	//Writes bytes to output file, can be used to implement a change password feature in the future
	/*private void writeFile(File f,byte[] output) throws IllegalStateException  {
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(f);
		    stream.write(output);
		    stream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	*/
}
