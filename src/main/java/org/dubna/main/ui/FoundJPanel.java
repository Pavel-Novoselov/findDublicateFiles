package org.dubna.main.ui;

import org.dubna.main.utils.FileTwinsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dubna.main.utils.FileTwinsUtils.showFile;

public class FoundJPanel extends JPanel {
    private final Map<String, Boolean> _2Delete = new HashMap<>();

    public FoundJPanel(JFrame found, List<List<JLabel>> labels, int offset, List<List<JLabel>> fullLabels) {
        if (labels.isEmpty()) {
            return;
        }
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        int i = labels.stream().mapToInt(Collection::size).sum();

        JPanel panel1 = new JPanel();
//        panel1.setLayout(new GridLayout(i + labels.size(), 3));
//        labels.forEach(labelGroup -> {
//            panel1.add(new JLabel("Группа файлов с именем: " + labelGroup.get(0).getText().substring(labelGroup.get(0).getText().lastIndexOf("\\") + 1)));
//            panel1.add(new JLabel(""));
//            panel1.add(new JLabel(""));
//            labelGroup.forEach(l -> {
//                if (l.getText().length() > 73){
//                    l.setText(l.getText().substring(0, 25) + "..." + l.getText().substring(l.getText().length() - 45));
//                }
//                panel1.add(l);
//                Checkbox checkboxDelete = new Checkbox("Удалить файл");
//                checkboxDelete.addItemListener(e -> {
//                    if (e.getStateChange() == ItemEvent.DESELECTED) {
//                        _2Delete.remove(l.getText());
//                    }
//                    if (e.getStateChange() == ItemEvent.SELECTED) {
//                        _2Delete.put(l.getText(), Boolean.FALSE);
//                    }
//                });
//                panel1.add(checkboxDelete);
//                JButton buttonShow = new JButton("Просмотр файла");
//                buttonShow.setSize(new Dimension(10, 7));
//                buttonShow.addActionListener(e -> showFile(l.getText()));
//                panel1.add(buttonShow);
//            });
//        });
        /////////////////
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weighty = 1.0;   //request any extra vertical space
        c.insets = new Insets(0,2,0,2);  //top padding
        int j = 0;
        for (List<JLabel> labelGroup : labels) {
            c.gridx = 0;
            c.gridy = j;
            c.gridwidth = 3;
            c.ipady = 20;
            JLabel label = new JLabel("Группа файлов с именем: " + labelGroup.get(0).getText().substring(labelGroup.get(0).getText().lastIndexOf("\\") + 1));
            label.setBackground(Color.YELLOW);
            panel1.add(label, c);
            int k = 1;
            for (JLabel l : labelGroup) {
                int row = j + k;
                c.gridx = 0;
                c.gridy = row;
                c.gridwidth = 1;
                c.ipady = 0;
                panel1.add(l, c);
                Checkbox checkboxDelete = new Checkbox("Удалить файл");
                checkboxDelete.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        _2Delete.remove(l.getText());
                    }
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        _2Delete.put(l.getText(), Boolean.FALSE);
                    }
                });
                c.gridx = 1;
                c.gridy = row;
                panel1.add(checkboxDelete, c);
                JButton buttonShow = new JButton("Просмотр файла");
                buttonShow.setSize(new Dimension(10, 7));
                buttonShow.addActionListener(e -> showFile(l.getText()));
                c.gridx = 2;
                c.gridy = row;
                panel1.add(buttonShow, c);
                k++;
            }
            j+=k;
        }

        /////////////////////
        this.add(panel1, BorderLayout.NORTH);

        JPanel panel2 = new JPanel();
        final JButton deleteButton = new JButton("Удалить");
        panel2.add(deleteButton);
        deleteButton.addActionListener(e -> deleteFiles());

        final JButton nextButton = new JButton("Еще результаты");
        panel2.add(nextButton);
        nextButton.addActionListener(e -> nextChunkView(found, offset, fullLabels));
        this.add(panel2, BorderLayout.SOUTH);
        this.repaint();
    }

    private void deleteFiles() {
        if (_2Delete.isEmpty()) {
            JOptionPane.showMessageDialog(FoundJPanel.this,
                    "Выберите файлы для удаления!");
            return;
        }
        _2Delete.forEach((name, result) -> _2Delete.put(name, FileTwinsUtils.deleteFiles(name)));
        final JFrame deleted = new DeletedInfoJFrame();
        final JPanel deletedPanel = new DeletedInfoJPanel(_2Delete);
        final JScrollPane jScrollPane = new JScrollPane(deletedPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        deleted.getContentPane().add(jScrollPane);
        deleted.pack();
        deleted.setVisible(true);
    }

    private void nextChunkView(JFrame found, int offset, List<List<JLabel>> listListFull){
        TwinJPanel.resultView(listListFull, found, offset);
    }
}
