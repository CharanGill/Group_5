package qnaApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Create {
	
	public void createQuiz() {
		
		Scanner scanner = new Scanner(System.in);
		ArrayList<Question> questions = new ArrayList<>();
		
		try {
			System.out.print("Enter the name of your quiz: ");
			String quizName = scanner.nextLine().trim();
			
			if(quizName.isEmpty()) {
				throw new IllegalArgumentException("Quiz must have a name!");
			}
		
			boolean addQuestion = true;
			
			while(addQuestion) {
				System.out.print("Enter the question: ");
				String questionText = scanner.nextLine();
				
				if(questionText.isEmpty() ) {
					throw new IllegalArgumentException("Question cannot be empty!");
				}
				
				
				int numOfAnswers;
				
				while(true) {
					System.out.print("Enter the number of answers (between 2 - 4): ");
					numOfAnswers = Integer.valueOf(scanner.nextLine());
					
					if(numOfAnswers >= 2 && numOfAnswers <= 4) {
						break;
					}
					
					System.out.println("The number of answers must be between 2 and 4!");
				}
				
				ArrayList<String> questionOptions = new ArrayList<>();
				
				for(int i = 1; i <= numOfAnswers; i++) {
					System.out.print("Enter option " + i + ": ");
					String option = scanner.nextLine();
					
					if(option.isEmpty()) {
						throw new IllegalArgumentException("Option cannot be empty!");
					}
					
					questionOptions.add(option);
				}
				
				int correctAnswer;
				
				while (true) {
					System.out.print("Enter the correct answer (1 - " + numOfAnswers + "): ");
					
					try {
						correctAnswer = Integer.valueOf(scanner.nextLine());
						
						if(correctAnswer >= 1 && correctAnswer <= numOfAnswers) {
							break;
						}
					
					}catch(NumberFormatException e) {
						System.out.println("Input must be of integer type!");
					}
					System.out.println("Choose a number from the provided options!");
					
				}
				
				questions.add(new Question(questionText, questionOptions, correctAnswer));
				System.out.print("Do you want to add another question? (yes/no): ");
                String response = scanner.nextLine();
                
                if (!response.equals("yes")) {
                    addQuestion = false;
			}
		}
		
		String fileName = "questionBank" + File.separator + quizName + ".txt";
		
		try (FileWriter myWriter = new FileWriter(fileName)) {
			
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				myWriter.write(q.getQuestion() + "\n");
				myWriter.write(String.valueOf(q.getOptions().size()) + "\n");
				
				for (int j = 0; j < q.getOptions().size(); j++) {
					myWriter.write(q.getOptions().get(j) + "\n");
				}
				
				myWriter.write(String.valueOf(q.getAnswer()) + "\n");
			}
		}
		
		System.out.println("Quiz named " + quizName + " created");
		
		} catch (IllegalArgumentException e) {
			System.out.println("Input Error: " + e.getMessage());
	    } catch (IOException e) {
	        System.out.println("File Error: Unable to write the quiz file.");
	    } 
	}
	
}
