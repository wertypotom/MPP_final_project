package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class ExpenseSearchBar extends JPanel {
    private final JTextField searchField = new JTextField(20);

    public ExpenseSearchBar(Consumer<String> onSearchChanged) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("Search:"));
        add(searchField);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { notifyChange(); }
            public void removeUpdate(DocumentEvent e) { notifyChange(); }
            public void changedUpdate(DocumentEvent e) { notifyChange(); }

            private void notifyChange() {
                onSearchChanged.accept(searchField.getText().trim().toLowerCase());
            }
        });
    }
}