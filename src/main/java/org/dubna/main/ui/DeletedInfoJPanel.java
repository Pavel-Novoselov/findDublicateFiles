package org.dubna.main.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DeletedInfoJPanel extends JPanel {
    public DeletedInfoJPanel(Map<String, Boolean> _2Delete) {
        this.setLayout(new BorderLayout());
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(_2Delete.size(), 1));
        _2Delete.forEach((file, result) -> {
            JLabel label = new JLabel();
            String fileResult = file + (result ? "  удален" : " не удален");
            label.setText(fileResult);
            mainPanel.add(label);
        });
        this.add(mainPanel, BorderLayout.NORTH);
    }
}
