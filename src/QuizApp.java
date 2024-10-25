import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizApp extends JFrame {
    JLabel questionLabel;
    JRadioButton[] optionB;
    ButtonGroup bGroup;
    JButton nextB, previousB, finishB;
    ArrayList<Question> questions;
    ArrayList<String> answers;
    int questionIndex = 0;

    public QuizApp(ArrayList<Question> questions) {
        setTitle("Swing Quiz");
        setSize(500, 300);
        setLayout(new GridLayout(6, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.questions = questions;
        answers = new ArrayList<>();

        // Initializing empty string because using list.set() method
        for (int i = 0; i < questions.size(); i++) {
            answers.add("");
        }

        questionLabel = new JLabel();
        add(questionLabel);

        optionB = new JRadioButton[4];
        bGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionB[i] = new JRadioButton();
            bGroup.add(optionB[i]);
            add(optionB[i]);
        }

        loadQOnFrame(questionIndex);

        previousB = new JButton("Previous");
        previousB.setEnabled(false);
        previousB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionIndex > 0) {
                    saveAns();
                    questionIndex--;

                    loadQOnFrame(questionIndex);
                    finishB.setEnabled(false);
                    nextB.setEnabled(true);

                    if (questionIndex == 0) {
                        previousB.setEnabled(false);
                    }
                }
            }
        });

        nextB = new JButton("Next");
        nextB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAns();
                questionIndex++;
                previousB.setEnabled(true);

                if (questionIndex < questions.size()) {
                    loadQOnFrame(questionIndex);
                }

                if (questionIndex == (questions.size() - 1)) {
                    nextB.setEnabled(false);
                    finishB.setEnabled(true);
                }
            }
        });

        finishB = new JButton("Submit");
        finishB.setEnabled(false);
        finishB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAns();
                int score = score();
                JOptionPane.showMessageDialog(null, "Your score is: "
                    + score + "/" + questions.size(), "Score", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(previousB);
        buttonPanel.add(nextB);
        buttonPanel.add(finishB);

        add(buttonPanel);

        setVisible(true);
    }

    public static ArrayList<Question> questionToList() {
        ArrayList<Question> questions = new ArrayList<>();

        try(Scanner fs = new Scanner(Paths.get("questions.txt"))) {
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                String[] parts = line.split("#");

                String question = parts[0];
                String[] options = {parts[1], parts[2], parts[3], parts[4]};
                String correctOption = parts[5];

                questions.add(new Question(question, options, correctOption));
            }
        } catch (Exception e) {
            System.out.println("Error at question fetching: "
                    + e.getMessage());
        }

        return questions;
    }

    public void loadQOnFrame(int questionIndex) {

        bGroup.clearSelection();
        Question q = questions.get(questionIndex);

        questionLabel.setText(q.question);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);

        String[] options = q.options;
        for (int i = 0; i < 4; i++) {
            optionB[i].setText(options[i]);
            optionB[i].setSelected(false);
        }

        String seletectedAns = answers.get(questionIndex);
        for (JRadioButton op: optionB) {
            if (op.getText().equals(seletectedAns)) {
                op.setSelected(true);
            }
        }
    }

    public void saveAns() {
        String selectedAns = null;
        for (JRadioButton op: optionB) {
            if (op.isSelected()) {
                selectedAns = op.getText();
            }
        }

        // Saves the selecteed ans in answers ArrayList
        if (selectedAns != null) {
            answers.set(questionIndex, selectedAns);
        }
    }

    public int score() {
        int score = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equals(questions.get(i).correctOp)) score++;
        }

        return score;
    }
}
