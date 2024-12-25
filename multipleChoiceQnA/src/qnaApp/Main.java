package qnaApp;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		Quiz quiz = new Quiz();
		quiz.loadQuestions();
		
		Question question;
		while ((question = quiz.getQuestion()) != null) {
            System.out.println(question);
            System.out.print("Enter Your Answer: ");
            int choice = Integer.valueOf(scanner.nextLine());
            
            if(choice == question.getAnswer()) {
            	System.out.println("Correct \n");
            }
            else if(choice != question.getAnswer()) {
            	System.out.println("Incorrect \n");
            	
            }
		}
	}
}
