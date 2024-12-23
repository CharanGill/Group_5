package qnaApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
	
	private ArrayList<Question> questions = new ArrayList<>();
	
	public void loadQuestions() {
	
		try {
			File myObj = new File("filename.txt");
			Scanner myReader = new Scanner(myObj);
		
			String line;
			String question;
			String[] options = null;
		
			while(myReader.hasNextLine()) {
				line = myReader.nextLine();
			}
			
			myReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("No file found!");
		}
	
	}
}
