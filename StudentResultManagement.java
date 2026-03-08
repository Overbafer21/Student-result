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
        setTitle("Система учёта результатов студентов");
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

        JLabel title = new JLabel("Добро пожаловать в СРС");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(43, 73, 122));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        JLabel subtitle = new JLabel("Войдите, чтобы продолжить");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(new Color(90, 90, 90));
        gbc.gridy = 1;
        card.add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        card.add(new JLabel("Логин"), gbc);

        usernameField = new JTextField(16);
        gbc.gridx = 1;
        card.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(new JLabel("Пароль"), gbc);

        passwordField = new JPasswordField(16);
        gbc.gridx = 1;
        card.add(passwordField, gbc);

        JButton loginButton = new JButton("Войти");
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

        JLabel header = new JLabel("Панель результатов студентов");
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

        addField(panel, gbc, 0, "ФИО студента", nameField);
        addField(panel, gbc, 1, "Номер зачётки", rollField);
        addField(panel, gbc, 2, "Математика", mathField);
        addField(panel, gbc, 3, "Наука", scienceField);
        addField(panel, gbc, 4, "Английский", englishField);

        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 8, 8));
        buttonRow.setOpaque(false);

        JButton calculateButton = new JButton("Рассчитать");
        calculateButton.addActionListener(e -> calculateResult());
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveResult());
        JButton viewButton = new JButton("Показать сохранённые");
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

        JLabel title = new JLabel("Результат");
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

        JOptionPane.showMessageDialog(this, "Неверный логин или пароль", "Ошибка входа", JOptionPane.ERROR_MESSAGE);
    }

    private void calculateResult() {
        try {
            int math = Integer.parseInt(mathField.getText().trim());
            int science = Integer.parseInt(scienceField.getText().trim());
            int english = Integer.parseInt(englishField.getText().trim());

            if (!isMarkValid(math) || !isMarkValid(science) || !isMarkValid(english)) {
                JOptionPane.showMessageDialog(this, "Оценки должны быть в диапазоне от 0 до 100.");
                return;
            }

            int total = math + science + english;
            double percentage = total / 3.0;
            String status = percentage >= 40 ? "СДАЛ" : "НЕ СДАЛ";

            resultArea.setText(
                    "Студент: " + nameField.getText().trim() + "\n" +
                    "№ зачётки: " + rollField.getText().trim() + "\n\n" +
                    "Сумма баллов: " + total + " / 300\n" +
                    "Процент: " + String.format("%.2f", percentage) + "%\n" +
                    "Итог: " + status
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введите только числовые значения оценок.");
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
            JOptionPane.showMessageDialog(this, "Заполните данные и сначала выполните расчёт.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
            writer.write("--------------------------------------------------\n");
            writer.write(resultText + "\n");
            writer.flush();
            JOptionPane.showMessageDialog(this, "Результат сохранён.");
            clearFields();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Не удалось сохранить файл с результатами.");
        }
    }

    private void viewResults() {
        File file = new File(RESULTS_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Сохранённые результаты пока отсутствуют.");
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
            JOptionPane.showMessageDialog(this, "Не удалось прочитать файл с результатами.");
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
