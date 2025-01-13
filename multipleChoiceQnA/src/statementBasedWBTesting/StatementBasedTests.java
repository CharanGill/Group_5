package statementBasedWBTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import qnaApp.Create;
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

    @Test
    void testCreateQuizWithValidInput() {
        String input = "General Knowledge\nWhat is 2 + 2?\n4\n1\n2\n4\n5\n3\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        File quizFile = new File("questionBank/General Knowledge.txt");
        assertTrue(quizFile.exists(), "Quiz file should be created");
        try (BufferedReader reader = new BufferedReader(new FileReader(quizFile))) {
            assertEquals("What is 2 + 2?", reader.readLine());
            assertEquals("4", reader.readLine());
            assertEquals("1", reader.readLine());
            assertEquals("2", reader.readLine());
            assertEquals("4", reader.readLine());
            assertEquals("5", reader.readLine());
            assertEquals("3", reader.readLine());
        } catch (IOException e) {
            fail("File read failed: " + e.getMessage());
        }
        quizFile.delete(); // Clean up after test
    }

    @Test
    void testEmptyQuizName() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Quiz must have a name!"),
                "Error message should be displayed for empty quiz name");
    }

    @Test
    void testEmptyQuestionText() {
        String input = "Math Quiz\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Question cannot be empty!"),
                "Error message should be displayed for empty question text");
    }

    @Test
    void testInvalidNumberOfOptions() {
        String input = "History Quiz\nWho was the first president of the USA?\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("The number of answers must be between 2 and 4!"),
                "Error message should be displayed for invalid number of options");
    }

    @Test
    void testMissingOptionText() {
        String input = "Science Quiz\nWhat is H2O?\n2\nWater\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Input Error: Option cannot be empty!"),
                "Error message should be displayed for empty option text");
    }

    @Test
    void testInvalidCorrectAnswer() {
        String input = "Geography Quiz\nWhat is the largest continent?\n3\nAsia\nAfrica\nEurope\n5\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Choose a number from the provided options!"),
                "Error message should be displayed for invalid correct answer");
    }

    @Test
    void testFileWriteFailure() {
        String input = "Test Quiz\nWhat is 2 + 2?\n2\n1\n2\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setProperty("user.dir", "/invalid/path");

        create.createQuiz();

        assertTrue(outContent.toString().contains("File Error: Unable to write the quiz file."),
                "Error message should be displayed for file write failure");
    }

    @Test
    void testLoadQuestionsSuccessfully() {
        try (FileWriter writer = new FileWriter("questionBank/testQuiz.txt")) {
            writer.write("What is the capital of France?\n");
            writer.write("3\n");
            writer.write("Paris\n");
            writer.write("Berlin\n");
            writer.write("London\n");
            writer.write("1\n");
        } catch (IOException e) {
            fail("Failed to set up test file: " + e.getMessage());
        }

        quiz.loadQuestions("testQuiz");

        assertEquals(1, quiz.getQuestions().size());
        assertEquals("What is the capital of France?", quiz.getQuestions().get(0).getQuestion());
    }

    @Test
    void testLoadQuestionsFileNotFound() {
        quiz.loadQuestions("nonexistentQuiz");

        assertTrue(outContent.toString().contains("No file found for topic: nonexistentQuiz"));
    }

    @Test
    void testShuffleQuestionsAndAnswers() {
        ArrayList<String> options = new ArrayList<>();
        options.add("A");
        options.add("B");
        options.add("C");

        quiz.getQuestions().add(new Question("Question 1?", options, 1));
        quiz.getQuestions().add(new Question("Question 2?", options, 2));

        quiz.shuffleQuestionsAndAnswers();

        assertNotEquals("Question 1?", quiz.getQuestions().get(0).getQuestion());
    }

    @Test
    void testQuizResults() {
        quiz.quizResult(true);
        quiz.quizResult(false);

        assertEquals(1, quiz.getCorrectAnswers());
    }

    @Test
    void testDisplayQuizResult() {
        quiz.quizResult(true);
        quiz.quizResult(false);
        quiz.displayQuizResult();

        assertTrue(outContent.toString().contains("Final Score: 1 out of 2"));
    }

    @Test
    void testEarlyExitFromAddingQuestions() {
        String input = "Quick Quiz\nFirst Question\n2\nYes\nNo\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Quick Quiz created"));
    }

    @Test
    void testRepeatedInvalidInputs() {
        String input = "Repeated Test\n\nValid Question\n1\n2\nOption 1\n\nOption 2\n3\n1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Repeated Test created"));
    }

    @Test
    void testAllInvalidAnswersBeforeValidInput() {
        String input = "Validation Test\nQuestion\n3\nOption1\nOption2\nOption3\n4\n5\n2\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Validation Test created"));
    }

    @Test
    void testAddMaximumQuestions() {
        StringBuilder input = new StringBuilder("Max Quiz\n");
        for (int i = 1; i <= 10; i++) {
            input.append("Question ").append(i).append("?\n2\nOption 1\nOption 2\n1\nyes\n");
        }
        input.append("no\n");
        System.setIn(new ByteArrayInputStream(input.toString().getBytes()));

        create.createQuiz();

        assertTrue(outContent.toString().contains("Quiz named Max Quiz created"));
    }

    @Test
    void testQuizResultSaving() {
        quiz.quizResult(true);
        quiz.quizResult(false);

        ByteArrayOutputStream fileContent = new ByteArrayOutputStream();
        try (PrintStream fileOut = new PrintStream(fileContent)) {
            System.setOut(fileOut);
            quiz.displayQuizResult();
        }

        assertTrue(fileContent.toString().contains("Final Score: 1 out of 2"));
    }
}
