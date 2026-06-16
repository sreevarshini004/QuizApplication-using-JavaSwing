import javax.swing.*;
import model.Question;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import ui.StartScreen;
import ui.ResultScreen;
import java.io.*;

public class App {

    static int score = 0;
    static int currentQuestion = 0;
    static int[] userAnswers = new int[10];

    static int timeLeft = 10;
    static Timer timer;

    static Question[] questions = {
    new Question("What is the capital of France?", "London", "Paris", "Rome", "Berlin", 2),
    new Question("Which planet is known as the Red Planet?", "Venus", "Earth", "Mars", "Jupiter", 3),
    new Question("What is 5 x 6?", "20", "30", "35", "40", 2),
    new Question("What is the capital of India?", "Mumbai", "New Delhi", "Chennai", "Kolkata", 2),
    new Question("Which is the largest ocean?", "Atlantic", "Indian", "Arctic", "Pacific", 4),
    new Question("Who wrote Harry Potter?", "J.K. Rowling", "Stephen King", "Mark Twain", "Chetan Bhagat", 1),
    new Question("What is H2O commonly known as?", "Salt", "Water", "Oxygen", "Hydrogen", 2),
    new Question("What is 12 x 8?", "96", "88", "108", "120", 1),
    new Question("Which gas do humans breathe in?", "Carbon Dioxide", "Oxygen", "Nitrogen", "Helium", 2),
    new Question("Which language is used for Android development?", "Python", "Java", "C++", "Ruby", 2)
};

    public static void main(String[] args) {

        // shuffle questions
        Collections.shuffle(Arrays.asList(questions));

        JFrame frame = new JFrame("Quiz Application");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showStartScreen(frame);

        frame.setVisible(true);
    }

    static void showStartScreen(JFrame frame) {

        frame.setContentPane(
            StartScreen.create(() -> showQuizScreen(frame))
        );

        frame.revalidate();
        frame.repaint();
    }

    static void showQuizScreen(JFrame frame) {

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        // FIXED BACKGROUND (NO "panel" ERROR ANYMORE)
        quizPanel.setBackground(new Color(240, 242, 245));

        quizPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // CARD STYLE
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel progressLabel = new JLabel();
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel timerLabel = new JLabel("Time: 10");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton option1 = new JRadioButton();
        JRadioButton option2 = new JRadioButton();
        JRadioButton option3 = new JRadioButton();
        JRadioButton option4 = new JRadioButton();

        JRadioButton[] options = {option1, option2, option3, option4};

        for (JRadioButton opt : options) {
            opt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            opt.setBackground(Color.WHITE);
            opt.setFocusPainted(false);
            opt.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextButton.setBackground(new Color(59, 130, 246));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Runnable loadQuestion = () -> {

            Question q = questions[currentQuestion];

            progressLabel.setText("Question " + (currentQuestion + 1) + " / " + questions.length);

            questionLabel.setText(q.question);
            option1.setText(q.option1);
            option2.setText(q.option2);
            option3.setText(q.option3);
            option4.setText(q.option4);

            group.clearSelection();

            startTimer(frame, timerLabel);
        };

        loadQuestion.run();

       nextButton.addActionListener(e -> {

            if (timer != null) timer.stop();

            Question q = questions[currentQuestion];

            int selected = 0;

            if (option1.isSelected()) selected = 1;
            else if (option2.isSelected()) selected = 2;
            else if (option3.isSelected()) selected = 3;
            else if (option4.isSelected()) selected = 4;

            userAnswers[currentQuestion] = selected;

            if (selected == q.correctOption) {
                score++;
            }

            currentQuestion++;

            if (currentQuestion < questions.length) {
                loadQuestion.run();
            } else {
                showResultScreen(frame);
            }
});

        card.add(progressLabel);
        card.add(Box.createVerticalStrut(10));

        card.add(timerLabel);
        card.add(Box.createVerticalStrut(10));

        card.add(questionLabel);
        card.add(Box.createVerticalStrut(15));

        card.add(option1);
        card.add(option2);
        card.add(option3);
        card.add(option4);

        card.add(Box.createVerticalStrut(20));
        card.add(nextButton);

        quizPanel.add(card);

        frame.setContentPane(quizPanel);
        frame.revalidate();
        frame.repaint();
    }

    static void startTimer(JFrame frame, JLabel timerLabel) {

        if (timer != null) timer.stop();

        timeLeft = 10;
        timerLabel.setText("Time: 10");
        timerLabel.setForeground(Color.BLACK);

        timer = new Timer(1000, e -> {

            timeLeft--;

            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 3) {
                timerLabel.setForeground(Color.RED);
            }

            if (timeLeft <= 0) {
                timer.stop();

                currentQuestion++;

                if (currentQuestion < questions.length) {
                    showQuizScreen(frame);
                } else {
                    showResultScreen(frame);
                }
            }
        });

        timer.start();
    }

    static void showResultScreen(JFrame frame) {

    int highScore = loadHighScore();

    if (score > highScore) {
        saveHighScore(score);
        highScore = score;
    }

    frame.setContentPane(
        ResultScreen.create(
            score,
            questions.length,
            highScore,
            () -> showReviewScreen(frame)
        )
    );

    frame.revalidate();
    frame.repaint();
}

    static void showReviewScreen(JFrame frame) {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(panel);

    JLabel title = new JLabel("Answer Review");
    title.setFont(new Font("Segoe UI", Font.BOLD, 24));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(Box.createVerticalStrut(20));
    panel.add(title);
    panel.add(Box.createVerticalStrut(20));

    for (int i = 0; i < questions.length; i++) {

        Question q = questions[i];

        String correctAnswer;

        switch (q.correctOption) {
            case 1 -> correctAnswer = q.option1;
            case 2 -> correctAnswer = q.option2;
            case 3 -> correctAnswer = q.option3;
            default -> correctAnswer = q.option4;
        }

        String userAnswer;

        switch (userAnswers[i]) {
            case 1 -> userAnswer = q.option1;
            case 2 -> userAnswer = q.option2;
            case 3 -> userAnswer = q.option3;
            case 4 -> userAnswer = q.option4;
            default -> userAnswer = "Not Answered";
        }

        boolean correct = userAnswers[i] == q.correctOption;

        JLabel review = new JLabel(
            "<html><b>Question " + (i + 1) + ":</b> " +
            q.question +
            "<br>Your Answer: " + userAnswer +
            "<br>Correct Answer: " + correctAnswer +
            "<br><b>" +
            (correct ? "✅ Correct" : "❌ Wrong") +
            "</b></html>"
        );

        review.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    correct ? new Color(34, 197, 94)
                            : new Color(239, 68, 68)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        );

        panel.add(review);
        panel.add(Box.createVerticalStrut(10));
    }

    JButton restart = new JButton("Restart Quiz");

    restart.addActionListener(e -> {

        score = 0;
        currentQuestion = 0;
        userAnswers = new int[10];

        Collections.shuffle(Arrays.asList(questions));

        showStartScreen(frame);
    });

    restart.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(Box.createVerticalStrut(20));
    panel.add(restart);
    panel.add(Box.createVerticalStrut(20));

    frame.setContentPane(scrollPane);
    frame.revalidate();
    frame.repaint();
}
    static int loadHighScore() {

    try {
        File file = new File("highscore.txt");

        if (!file.exists()) {
            return 0;
        }

        BufferedReader reader =
                new BufferedReader(new FileReader(file));

        int score = Integer.parseInt(reader.readLine());

        reader.close();

        return score;

    } catch (Exception e) {
        return 0;
    }
}

static void saveHighScore(int score) {

    try {

        BufferedWriter writer =
                new BufferedWriter(new FileWriter("highscore.txt"));

        writer.write(String.valueOf(score));

        writer.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
       

}