package org.dubna.main.ui;

import org.dubna.main.utils.FileTwinsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dubna.main.utils.FileTwinsUtils.showFile;

public class FoundJPanel extends JPanel {
    private final Map<String, Boolean> _2Delete = new HashMap<>();

    public FoundJPanel(int i, List<List<JLabel>> labels) {
        if (labels.isEmpty()) {
            return;
        }
        this.setLayout(new GridLayout(i + 1, 3));
        labels.forEach(labelGroup -> labelGroup.forEach(l -> {
            this.add(l);
            Checkbox checkboxDelete = new Checkbox("Удалить файл");
            checkboxDelete.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    _2Delete.remove(l.getText());
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _2Delete.put(l.getText(), Boolean.FALSE);
                }
            });
            this.add(checkboxDelete);
            JButton buttonShow = new JButton("Просмотр файла");
            buttonShow.addActionListener(e -> showFile(l.getText()));
            this.add(buttonShow);
        }));
        final JButton deleteButton = new JButton("Удалить");
        this.add(deleteButton);
        deleteButton.addActionListener(e -> deleteFiles());
        this.repaint();
    }

    private void deleteFiles() {
        _2Delete.forEach((name, result) -> _2Delete.put(name, FileTwinsUtils.deleteFiles(name)));
        JFrame deleted = new DeletedInfoJFrame();
        JPanel deletedPanel = new DeletedInfoJPanel(_2Delete);
        JScrollPane jScrollPane = new JScrollPane(deletedPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        deleted.getContentPane().add(jScrollPane);
        deleted.setVisible(true);
    }
}
