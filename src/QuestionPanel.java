import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class QuestionPanel extends JPanel {
    private JLabel questionLabel;
    private JTextField answerField;
    private JLabel feedbackLabel;
    private int x, y; // the random numbers for the addition
    private Random rand;

    public QuestionPanel() {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        rand = new Random();

        // Question label
        questionLabel = new JLabel();
        questionLabel.setBounds(150, 65, 560, 70); // wide enough for the panel
        questionLabel.setFont(new Font("Arial", Font.BOLD, 65));
        this.add(questionLabel);

        // Answer input field
        answerField = new JTextField();
        answerField.setBounds(250, 400, 300, 50);
        this.add(answerField);

        // Feedback label (for "correct"/"incorrect")
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setBounds(60, 230, 700, 60);
        this.add(feedbackLabel);

        // Generate the first question
        generateNewQuestion();

        // Handle Enter key in the text field
        answerField.addActionListener(e -> checkAnswer());
    }

    private void generateNewQuestion() {
        x = rand.nextInt(100); // 0-99
        y = rand.nextInt(100);
        questionLabel.setText("What is " + x + " + " + y + "?");
        answerField.setText("");
        feedbackLabel.setText("");
        answerField.requestFocusInWindow();
    }

    private void checkAnswer() 
    {
        try 
        {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (userAnswer == x + y) 
                {
                feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 80));
                feedbackLabel.setText("Correct!");
                feedbackLabel.setForeground(new Color(0,100,0)); // darker green (#006400)
            } 
            else {
                feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 80));
                feedbackLabel.setText("Incorrect!");
                feedbackLabel.setForeground(Color.RED);
            }

            // Hide feedback after 1 second and generate a new question
            Timer feedbackTimer = new Timer(1000, e -> generateNewQuestion());
            feedbackTimer.setRepeats(false);
            feedbackTimer.start();

        } catch (NumberFormatException ex) {
            feedbackLabel.setForeground(Color.BLUE);
            feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            feedbackLabel.setText("Enter a valid number!");
            // Clear after 1 second
            Timer feedbackTimer = new Timer(1000, e -> feedbackLabel.setText(""));
            feedbackTimer.setRepeats(false);
            feedbackTimer.start();
        }
    }
}
