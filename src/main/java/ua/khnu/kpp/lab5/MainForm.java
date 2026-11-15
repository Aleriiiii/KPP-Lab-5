package ua.khnu.kpp.lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Графічний інтерфейс (наслідує JFrame).
 * Рівень 1: 4 JTextField, кнопка створення об'єкта, JLabel-лічильник (початково 0).
 * Рівень 2: об'єкти зберігаються в колекції, JLabel = розмір колекції.
 * Рівень 3: друга кнопка зберігає вміст колекції у файл.
 */
public class MainForm extends JFrame {

    private final UsageStorage storage = new UsageStorage();

    private final JTextField previousField = new JTextField(15);
    private final JTextField currentField = new JTextField(15);
    private final JTextField tariffField = new JTextField(15);
    private final JTextField daysField = new JTextField(15);

    private final JButton createButton = new JButton("Створити запис");
    private final JButton saveButton = new JButton("Зберегти у файл");

    private final JLabel counterLabel = new JLabel("0");
    private final JTextArea outputArea = new JTextArea(10, 50);

    public MainForm() {
        super("ЛР № 5 — Облік електроенергії");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 500);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані"));
        inputPanel.add(new JLabel("Попередні показники (кВт·год):"));
        inputPanel.add(previousField);
        inputPanel.add(new JLabel("Поточні показники (кВт·год):"));
        inputPanel.add(currentField);
        inputPanel.add(new JLabel("Тариф (грн за кВт·год):"));
        inputPanel.add(tariffField);
        inputPanel.add(new JLabel("Кількість днів:"));
        inputPanel.add(daysField);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.add(createButton);
        controlPanel.add(saveButton);
        controlPanel.add(new JLabel("Кількість об'єктів у колекції:"));
        counterLabel.setFont(counterLabel.getFont().deriveFont(Font.BOLD, 16f));
        controlPanel.add(counterLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.SOUTH);

        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Створені об'єкти"));

        Container root = getContentPane();
        root.setLayout(new BorderLayout(10, 10));
        root.add(topPanel, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);

        createButton.addActionListener(new ButtonEventListener());
        saveButton.addActionListener(new SaveButtonListener());
    }

    /** Кнопка «Створити запис»: зчитує поля, створює об'єкт через конструктор, додає в колекцію. */
    private class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double previous;
            double current;
            double tariff;
            int days;
            try {
                previous = parseDouble(previousField);
                current = parseDouble(currentField);
                tariff = parseDouble(tariffField);
                days = Integer.parseInt(daysField.getText().trim());
            } catch (NumberFormatException ex) {
                showError("Введіть числа у всі поля (дробові — через крапку або кому, дні — ціле число).");
                return;
            }

            ElectricityUsage usage;
            try {
                usage = new ElectricityUsage(previous, current, tariff, days);
            } catch (IllegalArgumentException ex) {
                showError("Некоректні дані: показники ≥ 0, поточні ≥ попередніх, тариф > 0, днів > 0.");
                return;
            }

            storage.add(usage);
            counterLabel.setText(String.valueOf(storage.size()));
            outputArea.append(storage.size() + ". " + usage + "\n");
            clearFields();
        }
    }

    /** Кнопка «Зберегти у файл»: записує вміст колекції у вибраний файл. */
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (storage.isEmpty()) {
                showError("Колекція порожня — немає чого зберігати.");
                return;
            }
            JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
            chooser.setDialogTitle("Зберегти колекцію");
            chooser.setSelectedFile(new File("electricity.csv"));
            if (chooser.showSaveDialog(MainForm.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = chooser.getSelectedFile();
            try {
                storage.saveToFile(file.toPath());
                JOptionPane.showMessageDialog(MainForm.this,
                        "Збережено " + storage.size() + " об'єкт(ів) у файл:\n" + file.getAbsolutePath(),
                        "Успіх", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                showError("Помилка запису у файл: " + ex.getMessage());
            }
        }
    }

    private static double parseDouble(JTextField field) {
        return Double.parseDouble(field.getText().trim().replace(',', '.'));
    }

    private void clearFields() {
        previousField.setText("");
        currentField.setText("");
        tariffField.setText("");
        daysField.setText("");
        previousField.requestFocus();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Помилка", JOptionPane.ERROR_MESSAGE);
    }
}
