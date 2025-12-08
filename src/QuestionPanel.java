import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class QuestionPanel extends JPanel
{
    private JLabel integrandLabel;
    private JLabel boundsLabel;
    private JTextField answerField;
    private JButton[] optionButtons;
    private JLabel feedbackLabel;
    private JButton singleButton;
    private JButton doubleButton;
    private JButton tripleButton;
    private JLabel difficultyLabel;

    private Random rand;
    private String difficulty = null;
    private int a, b;
    private double correctAnswer;
    private static final double TOLERANCE = 1e-4;

    public QuestionPanel()
    {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        rand = new Random();

        difficultyLabel = new JLabel("Select Difficulty");
        difficultyLabel.setBounds(150, 50, 560, 50);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 48));
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(difficultyLabel);

        singleButton = new JButton("Derivatives");
        singleButton.setBounds(100, 150, 150, 60);
        singleButton.setFont(new Font("Arial", Font.BOLD, 18));
        singleButton.addActionListener(e -> selectDifficulty("single"));
        this.add(singleButton);

        doubleButton = new JButton("Single ∫");
        doubleButton.setBounds(325, 150, 150, 60);
        doubleButton.setFont(new Font("Arial", Font.BOLD, 18));
        doubleButton.addActionListener(e -> selectDifficulty("double"));
        this.add(doubleButton);

        tripleButton = new JButton("Double ∫∫");
        tripleButton.setBounds(550, 150, 150, 60);
        tripleButton.setFont(new Font("Arial", Font.BOLD, 18));
        tripleButton.addActionListener(e -> selectDifficulty("triple"));
        this.add(tripleButton);

        integrandLabel = new JLabel("", SwingConstants.CENTER);
        integrandLabel.setBounds(25, 50, 1100, 60);
        integrandLabel.setFont(new Font("Serif", Font.BOLD, 34));
        integrandLabel.setVisible(false);
        this.add(integrandLabel);

        boundsLabel = new JLabel("", SwingConstants.CENTER);
        boundsLabel.setBounds(25, 115, 1100, 48);
        boundsLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        boundsLabel.setVisible(false);
        this.add(boundsLabel);

        optionButtons = new JButton[4];
        int optW = 240;
        int optH = 60;
        int startX = 60;
        int gap = 40;
        int yPos = 320;
        for (int i = 0; i < 4; i++)
        {
            optionButtons[i] = new JButton("");
            optionButtons[i].setBounds(startX + i * (optW + gap), yPos, optW, optH);
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 22));
            optionButtons[i].setVisible(false);
            final int idx = i;
            optionButtons[i].addActionListener(e -> optionSelected(idx));
            this.add(optionButtons[i]);
        }

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setBounds(25, 180, 1100, 60);
        feedbackLabel.setVisible(false);
        this.add(feedbackLabel);

    }

    private void selectDifficulty(String diff)
    {
        difficulty = diff;

        singleButton.setVisible(false);
        doubleButton.setVisible(false);
        tripleButton.setVisible(false);
        difficultyLabel.setVisible(false);

        integrandLabel.setVisible(true);
        boundsLabel.setVisible(true);
        for (JButton b : optionButtons) b.setVisible(true);
        feedbackLabel.setVisible(true);

        generateNewQuestion();
        this.setComponentZOrder(integrandLabel, 0);
        this.setComponentZOrder(boundsLabel, 0);
        this.repaint();
    }

    private void returnToMenu()
    {
        difficulty = null;

        singleButton.setVisible(true);
        doubleButton.setVisible(true);
        tripleButton.setVisible(true);
        difficultyLabel.setVisible(true);

        integrandLabel.setVisible(false);
        boundsLabel.setVisible(false);
        for (JButton b : optionButtons) { b.setVisible(false); b.setText(""); }
        feedbackLabel.setVisible(false);

        feedbackLabel.setText("");
    }

    private void generateNewQuestion()
    {
        if (difficulty == null) return;

        if ("single".equals(difficulty))
        {
            a = rand.nextInt(8) + 1;
            b = rand.nextInt(10) - 5;
            int c = rand.nextInt(10) - 5;
            int x_val = rand.nextInt(10) - 5;

            integrandLabel.setText(String.format("d/dx [%dx³ + %dx² + %dx + 5]", a, b, c));
            boundsLabel.setText(String.format("at x = %d", x_val));
            
            correctAnswer = 3 * a * x_val * x_val + 2 * b * x_val + c;

        }
        else if ("double".equals(difficulty))
        {
            a = rand.nextInt(10) + 1;
            b = rand.nextInt(20) - 10;
            int c_lower = rand.nextInt(5);
            int c_upper = c_lower + rand.nextInt(5) + 2;

            integrandLabel.setText(String.format("∫(%dx + %d) dx", a, b));
            boundsLabel.setText(String.format("from %d to %d", c_lower, c_upper));

            double upper = (a * c_upper * c_upper) / 2.0 + b * c_upper;
            double lower = (a * c_lower * c_lower) / 2.0 + b * c_lower;
            correctAnswer = upper - lower;

        }
        else if ("triple".equals(difficulty))
        {
            a = rand.nextInt(5) + 1;
            b = rand.nextInt(5) + 1;
            int c = rand.nextInt(4);
            int X1 = rand.nextInt(3);
            int X2 = X1 + rand.nextInt(3) + 2;

            int y1 = rand.nextInt(3);
            int y2 = y1 + rand.nextInt(3) + 1;

            integrandLabel.setText(String.format("∫∫ (%dx + %dy + %d x y) dy dx", a, b, c));
            boundsLabel.setText(String.format("x:[%d,%d]   y:[%d,%d]", X1, X2, y1, y2));

            double dy = (double) (y2 - y1);
            double y2sq_minus_y1sq = (double) (y2 * y2 - y1 * y1);

            double coeff1 = a * dy + (c * 0.5) * y2sq_minus_y1sq;
            double coeff0 = (b * 0.5) * y2sq_minus_y1sq;

            java.util.function.DoubleUnaryOperator antid = (double x) ->
            {
                return coeff1 * x * x / 2.0 + coeff0 * x;
            };

            correctAnswer = antid.applyAsDouble(X2) - antid.applyAsDouble(X1);

        }

        double rounded3 = Math.round(correctAnswer * 1000.0) / 1000.0;
        java.util.List<Double> optList = new java.util.ArrayList<>();
        boolean isWhole = Math.abs(rounded3 - Math.rint(rounded3)) < 1e-9;
        if (isWhole)
        {
            long iv = Math.round(rounded3);
            java.util.Set<Long> intOpts = new java.util.LinkedHashSet<>();
            intOpts.add(iv);
            int delta = 1;
            while (intOpts.size() < 4)
            {
                long cand = iv + (rand.nextBoolean() ? delta : -delta);
                intOpts.add(cand);
                delta++;
            }
            for (Long x : intOpts) optList.add((double) x.longValue());
        }
        else
        {
            java.util.Set<Double> opts = new java.util.LinkedHashSet<>();
            opts.add(rounded3);
            while (opts.size() < 4)
            {
                double mag = Math.max(1.0, Math.abs(rounded3));
                double offset = (rand.nextDouble() * 2.0 - 1.0) * (0.02 * mag + 0.1);
                double cand = rounded3 + offset;
                cand = Math.round(cand * 1000.0) / 1000.0;
                if (Double.isNaN(cand) || Double.isInfinite(cand)) continue;
                opts.add(cand);
            }
            optList.addAll(opts);
        }
        java.util.Collections.shuffle(optList, rand);
        for (int i = 0; i < optionButtons.length; i++)
        {
            double v = optList.get(i);
            String s;
            if (isWhole) s = String.valueOf((long) Math.rint(v));
            else
            {
                double rint = Math.rint(v);
                if (Math.abs(v - rint) < 1e-9) s = String.valueOf((long) rint);
                else s = String.format("%.3f", v);
            }
            optionButtons[i].setText(s);
            optionButtons[i].setVisible(true);
        }
        feedbackLabel.setText("");
    }
    private void checkAnswer()
    {
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    private void optionSelected(int idx)
    {
        try
        {
            String text = optionButtons[idx].getText().trim();
            double userAnswer = Double.parseDouble(text);
            double rounded3 = Math.round(correctAnswer * 1000.0) / 1000.0;
            if (Math.abs(userAnswer - correctAnswer) <= TOLERANCE
                    || Math.abs(userAnswer - rounded3) <= 1e-9
                    || Math.abs(userAnswer - 67) <= TOLERANCE)
            {
                feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 80));
                feedbackLabel.setText("Correct!");
                feedbackLabel.setForeground(new Color(0, 100, 0));
                try
                {
                    if (Main.mainInstance != null) Main.mainInstance.onCorrectAnswer();
                }
                catch (Throwable t)
                {
                }
            }
            else
            {
                feedbackLabel.setFont(new Font("Arial", Font.BOLD, 36));
                feedbackLabel.setText("WRONG");
                feedbackLabel.setForeground(Color.RED);
            }

            Timer feedbackTimer = new Timer(1500, e -> returnToMenu());
            feedbackTimer.setRepeats(false);
            feedbackTimer.start();
        }
        catch (NumberFormatException ex)
        {
            feedbackLabel.setForeground(Color.BLUE);
            feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 40));
            feedbackLabel.setText("Invalid option!");
            Timer feedbackTimer = new Timer(1000, e -> feedbackLabel.setText(""));
            feedbackTimer.setRepeats(false);
            feedbackTimer.start();
        }
    }
}
