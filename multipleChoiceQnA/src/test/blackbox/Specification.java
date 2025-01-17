package test.blackbox;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import qnaApp.Create;
import qnaApp.Main;
import qnaApp.Question;
import qnaApp.Quiz;
import qnaApp.Timer;

public class Specification {

	
	//Specification black-box testing
	@Test
	public void testLoadQuestionWithNonExistingFile() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
        Quiz quiz = new Quiz();
		quiz.loadQuestions("imaginaryFile");
        assertTrue(outputStream.toString().contains("No file found for topic: imaginaryFile"));
	}
	
	//Specification black-box testing
	@Test
	public void testQuestionOptionsExist() {
		Quiz quiz = new Quiz();
		quiz.loadQuestions("test");
		for (Question question : quiz.getQuestions()) {  
	        assertFalse(question.getOptions().isEmpty());
	    }
	}
	
	//Specification black-box testing
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
	    file.delete();
	}
	
	//Specification black-box testing
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
	
	//Specification black-box testing
	@Test
	public void testAttemptExitingProgram() {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		String userInput = 	"exit\n";
						
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
		Main app = new Main();
		app.run();
		assertTrue(outputStream.toString().contains("Exiting the program."));
	}
	
	
	//Specification black-box testing
	@Test
	public void testViewAllQuestions() {
		Quiz quiz = new Quiz();
		quiz.loadQuestions("waffle");
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(outputStream);
		
		System.setOut(ps);
		quiz.viewAllQuestions();
		
		
		String expectedOutput = "List of Questions:\n"
								+ "Question 1:\n"
								+ "What is the capital city of the UK?\n"
								+ "1. Leicester\n"
								+ "2. Birmingham\n"
								+ "3. London\n"
								+ "4. Manchester\n"
								+ "\n"
								+ "Question 2:\n"
								+ "What is the colour of the sky?\n"
								+ "1. blue\n"
								+ "2. purple\n"
								+ "3. orange\n"
								+ "\n";
				
		assertEquals(outputStream.toString(), expectedOutput);	
	}
	
	//Specification black-box testing
	@Test
	public void testEmptyQuizName() {
	    Create create = new Create();
	    
	    InputStream sysInBackup = System.in;
	    
	    try {
	        ByteArrayInputStream in = new ByteArrayInputStream("\n".getBytes());
	        System.setIn(in);
	        
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outputStream));
	        
	        create.createQuiz();
	        
	        String output = outputStream.toString();
	        assertTrue(output.contains("Quiz must have a name!"));
	        
	    } finally {
	        System.setIn(sysInBackup);
	    }
	}
	
	//Specification black-box testing
	@Test
	public void testCreatingEmptyQuestion() {
		Create create = new Create();
	    
	    InputStream sysInBackup = System.in;
	    
	    try {
	        ByteArrayInputStream in = new ByteArrayInputStream("testingcreation\n\n".getBytes());
	        System.setIn(in);
	        
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outputStream));
	        
	        create.createQuiz();
	        
	        String output = outputStream.toString();
	        assertTrue(output.contains("Question cannot be empty!"));
	        
	    } finally {
	        System.setIn(sysInBackup);
	    }
	}
	
	//Specification black-box testing
	@Test
    public void testTimerExpiresAfterTimeLimit() throws InterruptedException {
        int timeLimit = 2; 
        Timer timer = new Timer(timeLimit);

        Thread timerThread = new Thread(timer);
        timerThread.start();
       
        Thread.sleep((timeLimit + 1) * 1000);

        assertTrue(timer.isTimeUp());
    }
	
	//Specification black-box testing
	@Test
	public void testSavedQuizResults() throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		String userInput = 	"simpleQuiz\n" +
							"1\n" +
							"no";
		
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
		Main app = new Main();
		app.attemptQuiz();
		
		String filePath = "result.txt";
		String fileContent = new String(Files.readAllBytes(Paths.get(filePath))).trim();
		String expectedString = "Quiz Completed!\n"
				+ "Final Score: 0 out of 1";
		
		assertEquals(expectedString, fileContent);
		// This can fail due to the chance of the question input being correct due to the shuffle method.
		
	}
	
	//Specification black-box testing
	@Test
	public void testDisplayQuizResults() {
		// This will be testing the output to terminal rather than to the file
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		String userInput = 	"simpleQuiz\n" +
							"1\n" +
							"no";
		
		System.setIn(new ByteArrayInputStream(userInput.getBytes()));
		
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
		Main app = new Main();
		app.attemptQuiz();
				
		assertTrue(outputStream.toString().contains("Quiz Completed!\n"
				+ "Final Score: 0 out of 1"));
	}
	
	//Specification black-box testing
	@Test
	public void testShuffleQuestionsAndAnswers() {
		
		Quiz nonShuffledQuiz = new Quiz();
		nonShuffledQuiz.loadQuestions("basicQuiz");
		
		Quiz shuffledQuiz = new Quiz();
		shuffledQuiz.loadQuestions("basicQuiz");
		shuffledQuiz.shuffleQuestionsAndAnswers();
		assertNotEquals(nonShuffledQuiz, shuffledQuiz);
		
	}
	
	
}
