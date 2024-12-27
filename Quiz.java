package qnaApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
	
	private ArrayList<Question> questions = new ArrayList<>();
	private int currentQuestionIndex = 0;
	private int correctAnswers = 0;
	private int totalQuestions = 0;
	
	public void loadQuestions() {
	
		try {
			File myObj = new File("questionBank/test.txt");
			Scanner myReader = new Scanner(myObj);
		
			while(myReader.hasNextLine()) {
				
				int numberOfChoices = 0;
				String question;
				ArrayList<String> options = new ArrayList<String>();
				int answer = 0;
				
				question = myReader.nextLine();
				numberOfChoices = Integer.valueOf(myReader.nextLine());
			
				if (numberOfChoices > 0) {
					for(int i = 0; i < numberOfChoices; i++) {
						options.add(myReader.nextLine());
					}
				}
				
				answer = Integer.valueOf(myReader.nextLine());
				
				questions.add(new Question(question, options, answer));
			}

			totalQuestions = questions.size();
			
			myReader.close();
			
		}
		catch (FileNotFoundException e) {
			System.out.println("No file found!");
		}
	
	}
	
	public Question getQuestion() {
		if (currentQuestionIndex < questions.size()) {
			return questions.get(currentQuestionIndex++);
		}
		else {
			return null;
		}
		
	}

	public void quizResult(boolean isCorrect){

		//compares input with test.txt, if correct, grants 1 point
		if (isCorrect){
			correctAnswers++;
		}


	}

	public void displayQuizResult(){

		System.out.println("Quiz Completed!");
        System.out.println("Final Score: " + correctAnswers + " out of " + totalQuestions);

        // Save the result into a file
        saveQuizResult();

	}


	public void saveQuizResult(){

		//saves the result and output it in a result.txt
		try (FileWriter writer = new FileWriter("result.txt")) {

            writer.write("Quiz Completed!\n");
            writer.write("Final Score: " + correctAnswers + " out of " + totalQuestions + "\n");
            System.out.println("Result saved to result.txt");

        } catch (IOException e) {
            System.out.println("An error occurred while saving the result: " + e.getMessage());
        }
    }

	
}

