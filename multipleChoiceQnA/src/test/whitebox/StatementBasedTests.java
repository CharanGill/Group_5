package test.whitebox;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import qnaApp.Create;
import qnaApp.Main;
import qnaApp.Quiz;
import qnaApp.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class StatementBasedTests {

    private Create create;
    private Quiz quiz;
    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        create = new Create();
        quiz = new Quiz();
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    
    //Statement based White-box test
    @Test
    void testEmptyQuizName() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Quiz must have a name!"),
                "Error message should be displayed for empty quiz name");
    }
    
    //Statement based White-box test
    @Test
    void testEmptyQuestionText() {
        String input = "Math Quiz\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Question cannot be empty!"),
                "Error message should be displayed for empty question text");
    }

    //Statement based White-box test
    @Test
    void testInvalidNumberOfOptions() {
        String input = "History Quiz\nWho was the first president of the USA?\n5\n2\nDonald Trump\nGeorge Washington\n2\nNo\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("The number of answers must be between 2 and 4!"),
                "Error message should be displayed for invalid number of options");
    }

    //Statement based White-box test
    @Test
    void testMissingOptionText() {
        String input = "Science Quiz\nWhat is H2O?\n2\nWater\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Option cannot be empty!"),
                "Error message should be displayed for empty option text");
    }

    //Statement based White-box test
    @Test
    void testInvalidCorrectAnswer() {
        String input = "Geography Quiz\nWhat is the largest continent?\n3\nAsia\nAfrica\nEurope\n5\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Choose a number from the provided options!"),
                "Error message should be displayed for invalid correct answer");
    }

    //Statement based White-box test
    @Test
    void testFileWriteFailure() {
        String input = "imaginaryFolder/Test Quiz\nWhat is 2 + 2?\n2\n1\n2\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setProperty("user.dir", "/invalid/path");

        create.createQuiz();

        assertTrue(outContent.toString().contains("File Error: Unable to write the quiz file."),
                "Error message should be displayed for file write failure");
    }

    
    //Statement based White-box test
    @Test
    void testLoadQuestionsFileNotFound() {
        quiz.loadQuestions("nonexistentQuiz");

        assertTrue(outContent.toString().contains("No file found for topic: nonexistentQuiz"));
    }


    //Statement based White-box test
    @Test
    void testEarlyExitFromAddingQuestions() {
        String input = "Quick Quiz\nFirst Question\n2\nYes\nNo\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Quick Quiz created"));
    }


    //Statement based White-box test
    @Test
    void testAllInvalidAnswersBeforeValidInput() {
        String input = "Validation Test\nQuestion\n3\nOption1\nOption2\nOption3\n4\n5\n2\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Validation Test created"));
    }

    //Statement based White-box test
    @Test
    void testQuizResultSaving() {
    	quiz.loadQuestions("waffle");
        quiz.quizResult(true);
        quiz.quizResult(false);

        ByteArrayOutputStream fileContent = new ByteArrayOutputStream();
        try (PrintStream fileOut = new PrintStream(fileContent)) {
            System.setOut(fileOut);
            quiz.displayQuizResult();
        }

        assertTrue(fileContent.toString().contains("Final Score: 1 out of 2"));
    }
    
    //Statement based White-box test
    @Test
    void testClosingApplication() {
    	String input = "testingQuiz\n1\nN\n";
    	System.setIn(new ByteArrayInputStream(input.getBytes()));
    	Main app = new Main();
    	app.attemptQuiz();
    	
    	assertTrue(outContent.toString().contains("Closing application!"));
    }
    
    //Statement based White-box test
    @Test
    void testMainRun() {
    	String input = "exit\n";
    	System.setIn(new ByteArrayInputStream(input.getBytes()));
    	Main app = new Main();
    	app.run();
    	assertTrue(outContent.toString().contains("Exiting the program."));
    }
    
    
}
