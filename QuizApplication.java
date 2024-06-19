import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {
    private static final String[][] questions = {
            {"Java is platform ___?", "Dependent", "Independent", "Both 1 and 2", "Non of these", "2"},
            {"Correct o/p of 'Hello World in java?'", "println(\"Hello World\")", "cout<<\"Hello World\"", "System.out.println(\"Hello World\")", "Non of these", "3"},
            {"How do you insert COMMENTS in Java code??", "//comment line", "#comment line", "/* comment line", "/comment line", "1"}
    };

    private static int currQues = 0;
    private static int score = 0;
    private static final int timeLimit = 15; // seconds
    private static Timer timer;
    private static boolean answered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (currQues < questions.length) {
            answered = false;
            loadQuestion(currQues);
            startTimer();

            String userAnswer = scanner.nextLine();
            if (!answered) {
                timer.cancel();
                checkAnswer(userAnswer);
            }
        }
        showResults();
        scanner.close();
    }

    private static void loadQuestion(int questionIndex) {
        System.out.println("Question " + (questionIndex + 1) + ": " + questions[questionIndex][0]);
        for (int i = 1; i <= 4; i++) {
            System.out.println(i + ". " + questions[questionIndex][i]);
        }
        System.out.println("You have " + timeLimit + " seconds to answer.");
    }

    private static void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int timeRemaining = timeLimit;

            @Override
            public void run() {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    System.out.println("\nTime's up!");
                    timer.cancel();
                    answered = true;
                    currQues++;
                    if (currQues < questions.length) {
                        loadQuestion(currQues);
                        startTimer();
                    } else {
                        showResults();
                        System.exit(0);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1500);
    }

    private static void checkAnswer(String userAnswer) {
        timer.cancel();
        if (userAnswer.matches("[1-4]")) {
            int answerIndex = Integer.parseInt(userAnswer);
            if (answerIndex == Integer.parseInt(questions[currQues][5])) {
                score++;
            }
        }
        currQues++;
        if (currQues < questions.length) {
            loadQuestion(currQues);
            startTimer();
        } else {
            showResults();
            System.exit(0);
        }
    }

    private static void showResults() {
        System.out.println("\nQuiz finished!");
        System.out.println("Your score: " + score + "/" + questions.length);
        System.out.println("Summary:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i][0]);
            System.out.println("Correct answer: " + questions[i][Integer.parseInt(questions[i][5])]);
        }
    }
}
