import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame {
    public Main() {
        ImageIcon img = new ImageIcon("welcome.jpg");
        int width = img.getIconWidth() + 15;
        int height = img.getIconHeight() + 70;

        setTitle("Welcome to Swing Quiz");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        JLabel imgLabel = new JLabel(img);
        add(imgLabel, BorderLayout.CENTER);

        JButton startB = new JButton("Start Quiz");
        startB.setFont(new Font("Arial", Font.BOLD, 20));
        startB.setBackground(Color.GREEN);
        startB.setForeground(Color.BLACK);
        startB.setFocusPainted(false);

        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Question> questions = QuizApp.questionToList();
                new QuizApp(questions);
                dispose();
            }
        });

        add(startB, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
