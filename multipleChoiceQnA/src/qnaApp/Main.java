package qnaApp;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Quiz quiz = new Quiz();
		quiz.loadQuestions();

		quiz.shuffleQuestionsAndAnswers();

		quiz.viewAllQuestions();

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
	}
}
