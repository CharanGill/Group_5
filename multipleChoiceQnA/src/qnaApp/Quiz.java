package qnaApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {

    private ArrayList<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;

    public void loadQuestions() {
        try {
            File myObj = new File("questionBank/test.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                int numberOfChoices = 0;
                String question;
                ArrayList<String> options = new ArrayList<String>();
                int answer = 0;

                question = myReader.nextLine();
                numberOfChoices = Integer.valueOf(myReader.nextLine());

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

    public Question getQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex++);
        } else {
            return null;
        }
    }
}

