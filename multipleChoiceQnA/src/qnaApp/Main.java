package qnaApp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a quiz topic (e.g., 'test', 'math'): ");
        String topic = scanner.nextLine();
        
        Quiz quiz = new Quiz();
        quiz.loadQuestions(topic);
        
        while (true) {
            Question question;
            while ((question = quiz.getQuestion()) != null) {
                System.out.println(question);
                System.out.print("Enter your answer: ");
                int choice = Integer.valueOf(scanner.nextLine());
                
                if (choice == question.getAnswer()) {
                    System.out.println("Correct \n");
                } else {
                    System.out.println("Incorrect \n");
                }
            }
            
            System.out.print("Would you like to retry the quiz? (Y/N): ");
            String retryChoice = scanner.nextLine();
            
            if (retryChoice.equalsIgnoreCase("y")) {
                quiz.resetQuiz();
            } else {
                break;
            }
        }
        
        scanner.close();
    }
}

