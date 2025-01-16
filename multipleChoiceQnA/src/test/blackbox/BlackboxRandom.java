package test.blackbox;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import qnaApp.Create;
import qnaApp.Question;
import qnaApp.Quiz;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlackboxRandom {

    private Quiz quiz;
    private ArrayList<Question> sampleQuestions;

    @BeforeEach
    public void setup() {
        quiz = new Quiz();
        sampleQuestions = new ArrayList<>();
        sampleQuestions.add(new Question("What is 2+2?", new ArrayList<>(List.of("3", "4", "5", "6")), 2));
        sampleQuestions.add(new Question("What is the capital of France?", new ArrayList<>(List.of("Berlin", "Paris", "Rome", "Madrid")), 2));
    }

    //random based black-box tests
    @Test
    public void testQuestionCreation() {
        Question question = new Question("What is Java?", new ArrayList<>(List.of("Programming Language", "Coffee", "Car Brand")), 1);
        assertEquals("What is Java?", question.getQuestion());
        assertEquals(3, question.getOptions().size());
        assertEquals(1, question.getAnswer());
    }

    //random based black-box tests
    @Test
    public void testQuizLoadQuestions_validFile() throws IOException {
        File testFile = new File("questionBank/test.txt");
        testFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("What is 2+2?\n");
            writer.write("4\n");
            writer.write("1\n2\n3\n4\n");
            writer.write("2\n");
        }

        quiz.loadQuestions("test");
        assertEquals(1, quiz.getQuestions().size());

        testFile.delete();
    }

    //random based black-box tests
    @Test
    public void testQuizLoadQuestions_invalidFile() {
        quiz.loadQuestions("non_existent_file");
        assertEquals(0, quiz.getQuestions().size());
    }

    //random based black-box tests  
    @Test
    public void testQuizResultCorrectAnswers() {
        quiz.setQuestions(new ArrayList<>(sampleQuestions));
        quiz.quizResult(true);
        assertEquals(1, quiz.getCorrectAnswers());
    }

    //random based black-box tests
    @Test
    public void testQuizResultIncorrectAnswers() {
        quiz.setQuestions(new ArrayList<>(sampleQuestions));
        quiz.quizResult(false);
        assertEquals(0, quiz.getCorrectAnswers());
    }

    //random based black-box tests
    @Test
    public void testQuizReset() {
        quiz.setQuestions(new ArrayList<>(sampleQuestions));
        quiz.getQuestion();
        quiz.resetQuiz();
        assertEquals(0, quiz.getCurrentQuestionIndex());
    }


    //random based black-box tests
    @Test
    public void testQuizSaveResult() {
        quiz.setQuestions(new ArrayList<>(sampleQuestions));
        quiz.quizResult(true);
        quiz.displayQuizResult();

        File resultFile = new File("result.txt");
        assertTrue(resultFile.exists());

        resultFile.delete();
    }
}

