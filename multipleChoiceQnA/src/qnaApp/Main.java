package qnaApp;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
  
    System.out.print("Enter a quiz topic (e.g., 'test', 'math'): ");
    String topic = scanner.nextLine();

		Quiz quiz = new Quiz();
		quiz.loadQuestions(topic);

		quiz.shuffleQuestionsAndAnswers();

		quiz.viewAllQuestions();

    while(True) {
		  System.out.println("\nStarting the Quiz...");
		  Question question;
		  while ((question = quiz.getQuestion()) != null) {
			  System.out.println(question);
			  System.out.print("Enter Your Answer: ");
			  int choice = Integer.parseInt(scanner.nextLine());

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
      } 
      else {
        break;
      }
    }
    scanner.close();
}

