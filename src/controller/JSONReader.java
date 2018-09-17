package controller;

import java.util.Scanner;
import java.io.*;

public class JSONReader {

	public static void main(String[] s) throws FileNotFoundException {
		String path = "db.xml";
		try(Scanner scan = new Scanner(new File(path))) {
			scan.useDelimiter("[<>]");
//			scan.useDelimiter("<");

			int memberCount = 0;
			while(scan.hasNext()) {
//				scan.useDelimiter(">");
				memberCount ++;
				
				String str = scan.next().trim();
				Scanner stringScanner = new Scanner(str);
				stringScanner.useDelimiter("[ /]");
				while(stringScanner.hasNext()) {
					System.out.print(stringScanner.next().trim() + ", ");
				}
				stringScanner.close();
				System.out.println();
//				if(str.equals("member")) {
//					System.out.println("member " + memberCount);
//				}
//				System.out.println("'" + str + "'");				
//				scan.useDelimiter("<");
			}


//			System.out.print(str.substring(0, str.length() - 1));

		}
		catch(Exception e) {
		}
	}

	public static void writeFile() throws FileNotFoundException {

	}
}
