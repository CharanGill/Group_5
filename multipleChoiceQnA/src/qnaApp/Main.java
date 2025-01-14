package qnaApp;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Main app = new Main();
        app.run();
	}
        
		public void run() {
			Scanner scanner = new Scanner(System.in);
		
			while(true) {
				System.out.print("Would you like to create a quiz or attempt a quiz? (type create or attempt): ");
				String choice = scanner.nextLine();
			
				if(choice.equals("create")) {
					Create newQuiz = new Create();
					newQuiz.createQuiz();
				}
				else if(choice.equals("attempt")) {
					attemptQuiz();
					break;
				}
				else {
					System.out.println("Exiting the program.");
					break;
				}
			}
		
		}
		
		
		public void attemptQuiz() {
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter a quiz topic (e.g., 'test', 'math'): ");
			String topic = scanner.nextLine();
		
			Quiz quiz = new Quiz();
		
			quiz.loadQuestions(topic);

			quiz.shuffleQuestionsAndAnswers();
		
			// If you want to view all questions at once rather than one at a time
			quiz.viewAllQuestions();

			while(true) {
				System.out.println("\nStarting the Quiz...");
				Question question;
			
				while ((question = quiz.getQuestion()) != null) {
	            
					Timer timer = new Timer(1); // 10-second time limit for each question
					Thread timerThread = new Thread(timer);
					timerThread.start(); // Start the timer
	            
				
					System.out.println(question);
					System.out.println("You have 10 seconds to answer."); // Simple message
					System.out.print("Enter Your Answer: ");
					int choice = -1;
	          
	                	// Wait for user input while the timer runs
					while (!timer.isTimeUp()) {
						try {
							if (System.in.available() > 0) { // Check if input is available
								choice = Integer.parseInt(scanner.nextLine());
								boolean answeredInTime = true;
								timerThread.interrupt(); // Stop the timer when the user answers
								break;
							}
						} catch (IOException e) {
							System.out.println("An error occurred while checking for input: " + e.getMessage());
						
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a number.");
						}
					}	 
	            
					// int choice = Integer.parseInt(scanner.nextLine());

					if (choice == question.getAnswer()) {
						System.out.println("Correct \n");
						quiz.quizResult(true);
					} 
				
					else {
						System.out.println("Incorrect \n");
						quiz.quizResult(false);
					}
				}
			
				quiz.displayQuizResult();
				System.out.print("Would you like to retry the quiz? (Y/N): ");
				String retryChoice = scanner.nextLine();
				
				if (retryChoice.equalsIgnoreCase("y")) {
					System.out.println("Restarting Quiz...");
					quiz.setCorrectAnswers(0);
					quiz.resetQuiz();
				}	 
				else {
					System.out.println("Closing application!");
					break;
				}
			}
			scanner.close();
		}
	}


