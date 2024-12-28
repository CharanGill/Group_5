package qnaApp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        Quiz quiz = new Quiz();
        quiz.loadQuestions();

        Question question;
        while ((question = quiz.getQuestion()) != null) {
            Timer timer = new Timer(10); // 10-second time limit for each question
            Thread timerThread = new Thread(timer);
            timerThread.start(); // Start the timer

            System.out.println(question);
            System.out.println("You have 10 seconds to answer."); // Simple message
            System.out.print("Enter Your Answer: ");
            int choice = -1;
            boolean answeredInTime = true;

            try {
                // Wait for user input while the timer runs
                while (!timer.isTimeUp()) {
                    if (System.in.available() > 0) { // Check if input is available
                        choice = Integer.parseInt(scanner.nextLine());
                        timerThread.interrupt(); // Stop the timer when the user answers
                        break;
                    }
                }

