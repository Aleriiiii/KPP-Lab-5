package ua.khnu.kpp.lab5;

import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm form = new MainForm();
            form.setVisible(true);
        });
    }
}
