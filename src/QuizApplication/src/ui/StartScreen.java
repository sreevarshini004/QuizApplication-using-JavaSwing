package ui;

import javax.swing.*;
import java.awt.*;

public class StartScreen {

    public static JPanel create(Runnable onStart) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("QUIZ APPLICATION", SwingConstants.CENTER);
        title.setFont(UIStyle.TITLE);

        JLabel subtitle = new JLabel("Test your knowledge!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);

        JButton start = new JButton("Start Quiz");
        start.setFont(UIStyle.BUTTON);
        start.setBackground(UIStyle.PRIMARY);
        start.setForeground(Color.WHITE);
        start.setFocusPainted(false);

        start.addActionListener(e -> onStart.run());

        panel.add(title, BorderLayout.CENTER);
        panel.add(subtitle, BorderLayout.NORTH);
        panel.add(start, BorderLayout.SOUTH);

        return panel;
    }
}