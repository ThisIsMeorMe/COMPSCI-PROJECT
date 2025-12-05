import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class QuestionPanel {

    private JPanel questionPanel; // the small floating panel
    private JLayeredPane layeredPane; // to place panel above main panel
    private boolean isVisible = false; // track visibility
    private Random rand = new Random();
    private JLabel easy;
    private JLabel hard;
    private JLabel extreme;
    private JLabel back;
    private JTextField answerField;
    private JLabel resultLabel;
    private int correctAnswer;
    private int num;

    public QuestionPanel(JFrame frame) {
        this.layeredPane = frame.getLayeredPane();
    }

    // Call this to toggle the panel
    public void toggleQuestionPanel() {

        if (questionPanel == null) {
            // First-time creation
            questionPanel = new JPanel();
            questionPanel.setLayout(null);
            questionPanel.setSize(600, 400);
            questionPanel.setBackground(Color.WHITE);
            questionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

            // Center panel in the frame
            Dimension frameSize = layeredPane.getSize();
            questionPanel.setBounds((frameSize.width - 800) / 2, (frameSize.height - 400) / 2, 800, 400);

            layeredPane.add(questionPanel, JLayeredPane.POPUP_LAYER);
            loadDifficultyOptions();
        }

        // Toggle visibility
        isVisible = !isVisible;
        questionPanel.setVisible(isVisible);
        questionPanel.revalidate();
        questionPanel.repaint();
    }

    public void loadDifficultyOptions() {
        questionPanel.removeAll();
        // Add difficulty images
        easy = new JLabel("EASY", SwingConstants.CENTER);
        easy.setForeground(Color.GREEN.darker());
        easy.setFont(new Font("Arial", Font.BOLD, 70));
        easy.setBounds(150, 70, 200, 150);
        easy.setHorizontalAlignment(SwingConstants.CENTER);
        easy.setVerticalAlignment(SwingConstants.CENTER);
        questionPanel.add(easy);

        hard = new JLabel("HARD", SwingConstants.CENTER);
        hard.setFont(new Font("Arial", Font.BOLD, 70));
        hard.setForeground(Color.ORANGE.darker());
        hard.setBounds(450, 70, 250, 150);
        hard.setHorizontalAlignment(SwingConstants.CENTER);
        hard.setVerticalAlignment(SwingConstants.CENTER);
        questionPanel.add(hard);

        extreme = new JLabel("EXTREME", SwingConstants.CENTER);
        extreme.setFont(new Font("Arial", Font.BOLD, 70));
        extreme.setForeground(Color.RED.darker());
        extreme.setBounds(150, 200, 500, 150);
        extreme.setHorizontalAlignment(SwingConstants.CENTER);
        extreme.setVerticalAlignment(SwingConstants.CENTER);
        questionPanel.add(extreme);

        // Difficulty click handlers
        easy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadEasyQuestion();
            }
        });
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadHardQuestion();
            }
        });
        extreme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadExtremeQuestion();
            }
        });
    }

    // -------------------- Load Easy Question --------------------
    private void loadEasyQuestion() {
        num = 1;
        questionPanel.removeAll();
        loadBackButton();
        int x = rand.nextInt(100);
        int y = rand.nextInt(100);
        correctAnswer = x + y;

        JLabel question = new JLabel(x + " + " + y + " = ?");
        question.setFont(new Font("Arial", Font.BOLD, 100));
        question.setBounds(170, 0, 800, 300);
        questionPanel.add(question);
        createAnswerInput();

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    // -------------------- Load Hard Question --------------------
    private void loadHardQuestion() {
        num = 2;
        questionPanel.removeAll();
        loadBackButton();
        int x = rand.nextInt(21);
        int y = rand.nextInt(21);

        correctAnswer = x * y;

        JLabel question = new JLabel(x + " × " + y + " = ?");
        question.setFont(new Font("Arial", Font.BOLD, 100));
        question.setBounds(170, 0, 800, 300);
        questionPanel.add(question);
        createAnswerInput();

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    // -------------------- Load Extreme Question --------------------
    private void loadExtremeQuestion() {
        num = 3;
        questionPanel.removeAll();
        loadBackButton();
        int a = rand.nextInt(8) + 2;

        JLabel question = new JLabel("∫ " + a + "x dx = ?");
        question.setFont(new Font("Arial", Font.BOLD, 100));
        question.setBounds(170, 00, 800, 300);
        questionPanel.add(question);

        correctAnswer = (a / 2);
        createAnswerInput();

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    private void loadBackButton() {
        back = new JLabel("BACK", SwingConstants.CENTER);
        back.setFont(new Font("Arial", Font.BOLD, 50));
        back.setForeground(Color.BLUE.darker());
        back.setBounds(550, 330, 300, 50);
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.setVerticalAlignment(SwingConstants.CENTER);
        questionPanel.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                questionPanel.removeAll();
                loadDifficultyOptions();
                questionPanel.revalidate();
                questionPanel.repaint();
            }
        });
    }

    private void createAnswerInput() 
    {
        // Text field
        answerField = new JTextField();
        answerField.setBounds(280, 330, 180, 40);
        answerField.setFont(new Font("Arial", Font.PLAIN, 22));
        questionPanel.add(answerField);

        // Result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 60));
        resultLabel.setBounds(100, 230, 600, 70);
        resultLabel.setVisible(false);
        questionPanel.add(resultLabel);

        // Handle answer checking
        answerField.addActionListener(e -> checkAnswer());
    }

    private void checkAnswer() 
    {
        String text = answerField.getText().trim();
        try 
        {
            int userAns = Integer.parseInt(text);

            if (userAns == correctAnswer) {
                resultLabel.setText("Correct!");
                resultLabel.setForeground(Color.GREEN.darker());
                if (num == 1)
                {
                    Main.pointAmount += 10;
                } 
                else if (num == 2) 
                {
                    Main.pointAmount += 20;
                } 
                else if (num == 3) 
                {
                    Main.pointAmount += 30;
                }
            } 
            else 
            {
                resultLabel.setText("Incorrect");
                resultLabel.setForeground(Color.RED);
            }

            resultLabel.setVisible(true);

            Timer timer = new Timer(1000, e -> 
            {
                if (num == 1) 
                {
                    loadEasyQuestion();
                } 
                else if (num == 2) 
                {
                    loadHardQuestion();
                } 
                else if (num == 3) 
                {
                    loadExtremeQuestion();
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
        catch (NumberFormatException ex) 
        {
            resultLabel.setText("Enter a valid number");
            resultLabel.setForeground(Color.ORANGE.darker());
            resultLabel.setVisible(true);

            new Timer(1000, e -> resultLabel.setVisible(false)).start();
        }
    }
}
