package blackboxTests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Test;

import qnaApp.Create;
import qnaApp.Main;
import qnaApp.Question;
import qnaApp.Quiz;

public class Specification {

	@Test
	public void testLoadQuestionWithExistingFile() {
		Quiz quiz = new Quiz();
		quiz.loadQuestions("test");
		assertFalse(quiz.getQuestions().isEmpty());
	}
	
	@Test
	public void testLoadQuestionWithNonExistingFile() {
		Quiz quiz = new Quiz();
		quiz.loadQuestions("imaginaryFile");
		assertTrue(quiz.getQuestions().isEmpty());
	}
	
	@Test
	public void testQuestionOptionsExist() {
		Quiz quiz = new Quiz();
		quiz.loadQuestions("test");
		for (Question question : quiz.getQuestions()) {  // Iterate over all questions in the quiz.
	        assertFalse(question.getOptions().isEmpty());
	    }
	}
	
	@Test
	public void testCreateQuiz() {
		String userInput = "testingQuiz\n" +
							"What is 5 + 5? \n" +
							"2\n" +
							"25\n" +
							"10\n" +
							"2\n" +
							"no\n";
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		
		Create create = new Create();
	    create.createQuiz();
	    
	    File file = new File("questionBank/testingQuiz.txt");
	    assertTrue(file.exists());
	}
	
	@Test
	public void testAttemptQuiz() {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		String userInput = 	"simpleQuiz\n" +
							"\n" +
							"no";
		
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
		Main app = new Main();
		app.attemptQuiz();
		assertTrue(outputStream.toString().contains("Closing application!"));
	}
	
}
