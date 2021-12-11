package org.dubna.main.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public final class DeletedInfoJPanel extends JPanel {
    private static final long serialVersionUID = -5879911268897607359L;

    public DeletedInfoJPanel(final Map<String, Boolean> _2Delete) {
        this.setLayout(new BorderLayout());
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(_2Delete.size(), 1));
        _2Delete.forEach((file, result) -> {
            final JLabel label = new JLabel();
            final String fileResult = file + (result ? "  удален" : " не удален");
            label.setText(fileResult);
            mainPanel.add(label);
        });
        this.add(mainPanel, BorderLayout.NORTH);
    }
}
