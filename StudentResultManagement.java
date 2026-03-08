import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;

public class StudentResultManagement extends JFrame {
    private static final String RESULTS_FILE = "results.txt";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel rootPanel = new JPanel(cardLayout);

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JTextField nameField;
    private JTextField rollField;
    private JTextField mathField;
    private JTextField scienceField;
    private JTextField englishField;
    private JTextArea resultArea;

    public StudentResultManagement() {
        setTitle("Student Result Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);

        rootPanel.add(createLoginPanel(), "login");
        rootPanel.add(createMainPanel(), "main");
        add(rootPanel);

        cardLayout.show(rootPanel, "login");
    }

    private JPanel createLoginPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(236, 242, 255));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome to SRMS");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(43, 73, 122));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(new Color(90, 90, 90));
        gbc.gridy = 1;
        card.add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        card.add(new JLabel("Username"), gbc);

        usernameField = new JTextField(16);
        gbc.gridx = 1;
        card.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(new JLabel("Password"), gbc);

        passwordField = new JPasswordField(16);
        gbc.gridx = 1;
        card.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(43, 73, 122));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        card.add(loginButton, gbc);

        wrapper.add(card);
        return wrapper;
    }

    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBorder(new EmptyBorder(15, 15, 15, 15));
        main.setBackground(new Color(245, 247, 250));

        JLabel header = new JLabel("Student Result Dashboard");
        header.setFont(new Font("SansSerif", Font.BOLD, 26));
        header.setForeground(new Color(32, 55, 92));
        main.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setOpaque(false);

        centerPanel.add(createInputPanel());
        centerPanel.add(createResultPanel());

        main.add(centerPanel, BorderLayout.CENTER);
        return main;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        nameField = new JTextField();
        rollField = new JTextField();
        mathField = new JTextField();
        scienceField = new JTextField();
        englishField = new JTextField();

        addField(panel, gbc, 0, "Student Name", nameField);
        addField(panel, gbc, 1, "Roll Number", rollField);
        addField(panel, gbc, 2, "Math", mathField);
        addField(panel, gbc, 3, "Science", scienceField);
        addField(panel, gbc, 4, "English", englishField);

        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 8, 8));
        buttonRow.setOpaque(false);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> calculateResult());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveResult());
        JButton viewButton = new JButton("View Saved");
        viewButton.addActionListener(e -> viewResults());

        buttonRow.add(calculateButton);
        buttonRow.add(saveButton);
        buttonRow.add(viewButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(buttonRow, gbc);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Result Output");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if ("admin".equals(username) && "1234".equals(password)) {
            cardLayout.show(rootPanel, "main");
            return;
        }

        JOptionPane.showMessageDialog(this, "Invalid credentials", "Login failed", JOptionPane.ERROR_MESSAGE);
    }

    private void calculateResult() {
        try {
            int math = Integer.parseInt(mathField.getText().trim());
            int science = Integer.parseInt(scienceField.getText().trim());
            int english = Integer.parseInt(englishField.getText().trim());

            if (!isMarkValid(math) || !isMarkValid(science) || !isMarkValid(english)) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                return;
            }

            int total = math + science + english;
            double percentage = total / 3.0;
            String status = percentage >= 40 ? "PASS" : "FAIL";

            resultArea.setText(
                    "Student: " + nameField.getText().trim() + "\n" +
                    "Roll No: " + rollField.getText().trim() + "\n\n" +
                    "Total Marks : " + total + " / 300\n" +
                    "Percentage  : " + String.format("%.2f", percentage) + "%\n" +
                    "Final Result: " + status
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter numeric marks only.");
        }
    }

    private boolean isMarkValid(int mark) {
        return mark >= 0 && mark <= 100;
    }

    private void saveResult() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String resultText = resultArea.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || resultText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill details and calculate result before saving.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
            writer.write("--------------------------------------------------\n");
            writer.write(resultText + "\n");
            writer.flush();
            JOptionPane.showMessageDialog(this, "Result saved.");
            clearFields();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save result file.");
        }
    }

    private void viewResults() {
        File file = new File(RESULTS_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No saved results found yet.");
            return;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
            resultArea.setText(content.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not read result file.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        mathField.setText("");
        scienceField.setText("");
        englishField.setText("");
        resultArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentResultManagement app = new StudentResultManagement();
            app.setVisible(true);
        });
    }
}
