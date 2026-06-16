package ui;

import javax.swing.*;
import java.awt.*;

public class ResultScreen {

    public static JPanel create(int score, int total, int highScore, Runnable restart) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Quiz Completed!");
        title.setFont(UIStyle.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel result = new JLabel(score + " / " + total);
        result.setFont(new Font("Segoe UI", Font.BOLD, 22));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bestScore = new JLabel("🏆 Best Score: " + highScore);
        bestScore.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bestScore.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel(
                score == total ? "Excellent 🏆" :
                score >= total / 2 ? "Good 👍" : "Keep Practicing 📚"
        );
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setFont(UIStyle.BUTTON);
        restartBtn.setBackground(UIStyle.SUCCESS);
        restartBtn.setForeground(Color.WHITE);

        restartBtn.addActionListener(e -> restart.run());
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(40));
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(result);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bestScore);
        panel.add(Box.createVerticalStrut(10));
        panel.add(msg);
        panel.add(Box.createVerticalStrut(30));
        panel.add(restartBtn);

        return panel;
    }
}