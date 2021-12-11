package org.dubna.main.ui;

import org.dubna.main.utils.FileTwinsUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TwinJPanel extends JPanel {
    public static final int CHANK_SIZE = 7;
    private static final long serialVersionUID = 8933121962595768028L;

    public TwinJPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        final JTextField textField1 = new JTextField(20);
        final JTextField textField2 = new JTextField(20);
        final JButton button1 = new JButton("Выберите папку 1");
        final JButton button2 = new JButton("Выберите папку 2");
        panel.add(textField1);
        panel.add(button1);
        panel.add(textField2);
        panel.add(button2);
        this.add(panel);
        final JFileChooser fileChooser = new JFileChooser();

        final JButton button = new JButton("Искать");
        this.add(button);

        button1.addActionListener(e -> {
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            final int result = fileChooser.showOpenDialog(TwinJPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                textField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        button2.addActionListener(e -> {
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            final int result = fileChooser.showOpenDialog(TwinJPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                textField2.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        button.addActionListener(e -> onClick(textField1.getText(), textField2.getText()));
    }

    private void onClick(final String root1, final String root2) {
        if (root1 == null || root2 == null || root1.isEmpty() || root2.isEmpty()) {
            JOptionPane.showMessageDialog(TwinJPanel.this,
                    "Выберите папки для сравнения файлов!");
            return;
        }
        final List<List<String>> result = new ArrayList<>();
        final FileTwinsUtils fileTwinsUtils = new FileTwinsUtils(root1, root2);
        final Map<FileTwinsUtils.CompareFile, List<FileTwinsUtils.CompareFile>> resultMap = fileTwinsUtils.compareFiles();
        resultMap.forEach((compareFile1, compareFile2) -> {
            final List<String> twins = new ArrayList<>();
            twins.add(compareFile1.getAbsPath());
            compareFile2.forEach(file -> twins.add(file.getAbsPath()));
            result.add(twins);
        });
        final List<List<JLabel>> labels = new ArrayList<>();
        for (List<String> list : result) {
            final List<JLabel> groupeTwinsLabels = new ArrayList<>();
            list.forEach(s -> {
                final JLabel label = new JLabel();
                label.setText(s);
                groupeTwinsLabels.add(label);
            });
            labels.add(groupeTwinsLabels);
        }
        if (labels.isEmpty()) {
            JOptionPane.showMessageDialog(TwinJPanel.this,
                    "Одинаковых файлов не найдено");
            return;
        }
        resultView(labels, null, 0);
    }

    public static void resultView(final List<List<JLabel>> labelListFull, final JFrame previouseFrame, final int offset) {
        if (previouseFrame != null){
            previouseFrame.dispose();
        }
        int nextChunkStart = offset + CHANK_SIZE;
        if (nextChunkStart > labelListFull.size()){
            nextChunkStart = labelListFull.size();
        }
        final List<List<JLabel>> labelListForView = labelListFull.subList(offset, nextChunkStart);
        if (labelListForView.isEmpty()){
            JOptionPane.showMessageDialog(new JPanel(),
                    "Не найдено больше файлов!");
            return;
        }
        final JFrame found = new FoundJFrame();
        final JPanel foundPanel = new FoundJPanel(found, labelListForView, nextChunkStart, labelListFull);
        final JScrollPane jScrollPane = new JScrollPane(foundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        found.getContentPane().add(jScrollPane);
        found.pack();
        found.setVisible(true);
    }

}
