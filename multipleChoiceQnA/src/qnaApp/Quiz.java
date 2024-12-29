package qnaApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz {
	private ArrayList<Question> questions = new ArrayList<>();
	private int currentQuestionIndex = 0;

	public void loadQuestions() {
		try {
			File myObj = new File("questionBank/test.txt");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				String question;
				ArrayList<String> options = new ArrayList<>();
				int answer = 0;

				question = myReader.nextLine();
				int numberOfChoices = Integer.valueOf(myReader.nextLine());

				if (numberOfChoices > 0) {
					for (int i = 0; i < numberOfChoices; i++) {
						options.add(myReader.nextLine());
					}
				}

				answer = Integer.valueOf(myReader.nextLine());
				questions.add(new Question(question, options, answer));
			}

			myReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("No file found!");
		}
	}

	public void shuffleQuestionsAndAnswers() {
		Collections.shuffle(questions);

		for (Question question : questions) {
			ArrayList<String> options = question.getOptions();
			int correctAnswerIndex = question.getAnswer() - 1;

			String correctAnswer = options.get(correctAnswerIndex);
			Collections.shuffle(options);

			int newCorrectAnswerIndex = options.indexOf(correctAnswer);
			question.setAnswer(newCorrectAnswerIndex + 1);
		}
	}

	public void viewAllQuestions() {
		System.out.println("List of Questions:");
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			System.out.println("Question " + (i + 1) + ":");
			System.out.println(question);
		}
	}

	public Question getQuestion() {
		if (currentQuestionIndex < questions.size()) {
			return questions.get(currentQuestionIndex++);
		} else {
			return null;
		}
	}
}
