package qnaApp;

import java.util.ArrayList;


public class Question {
	private String question;
	private ArrayList<String> options;
	private int answer;

	public Question(String question, ArrayList<String> options, int answer) {
		if (options == null || options.size() > 4) {
			throw new IllegalArgumentException("Must be between 1 and 4 choices!");
		}

		this.question = question;
		this.options = options;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		StringBuilder print = new StringBuilder(question + "\n");
		for (int i = 0; i < options.size(); i++) {
			print.append((i + 1)).append(". ").append(options.get(i)).append("\n");
		}
		return print.toString();
	}
}

