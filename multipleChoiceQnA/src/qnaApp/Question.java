package qnaApp;

import java.util.Arrays;

public class Question {
	
	private String question;
	private String[] options;
	private int answer;
	
	public Question(String question, String[] options, int answer) {
		
		if (options == null || options.length > 4) {
			throw new IllegalArgumentException("Must be between 1 and 4 choices!");
		}
		
		if (answer > 1) {
			throw new IllegalArgumentException("Must only be 1 answer!");
		}
		
		this.question = question;
		this.options = options;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}


	public String[] getOptions() {
		return options;
	}


	public int getAnswer() {
		return answer;
	}

	@Override
	public String toString() {
		String print = question + "\n";
		for (String option: options) {
			print += option + "\n"; 
		}
		print += "Answer: " + answer + "\n";
		return print;
	}
}

