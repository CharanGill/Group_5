package qnaApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz {

	private ArrayList<Question> questions = new ArrayList<>();
	private int currentQuestionIndex = 0;
	private int correctAnswers = 0;
	private int totalQuestions = 0;

	
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	
	public Integer getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}
	
	

	public void loadQuestions(String topic) {
		try {
			File myObj = new File("questionBank/" + topic + ".txt");
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
			totalQuestions = questions.size();
			myReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("No file found for topic: " + topic);
		}
	}

	public void resetQuiz() {
		currentQuestionIndex = 0;
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
	
	public void quizResult(boolean isCorrect){

		//compares input with test.txt, if correct, grants 1 point
		if (isCorrect){
			correctAnswers++;
		}
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
	
	public void displayQuizResult(){

		System.out.println("Quiz Completed!");
        System.out.println("Final Score: " + correctAnswers + " out of " + totalQuestions);

        // Save the result into a file
        saveQuizResult();

	}
	
}
