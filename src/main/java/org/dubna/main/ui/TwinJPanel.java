package org.dubna.main.ui;

import org.dubna.main.utils.FileTwinsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TwinJPanel extends JPanel {

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
        JFileChooser fileChooser = new JFileChooser();

        final JButton button = new JButton("Искать");
        this.add(button);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор директории");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(TwinJPanel.this);
                if (result == JFileChooser.APPROVE_OPTION ){
                    textField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        button2.addActionListener(e -> {
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(TwinJPanel.this);
            if (result == JFileChooser.APPROVE_OPTION ){
                textField2.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        button.addActionListener(e -> onClick(textField1.getText(), textField2.getText()));
    }

    private void onClick(String root1, String root2) {
        if (root1 == null || root2 == null || root1.isEmpty() || root2.isEmpty()){
            JOptionPane.showMessageDialog(TwinJPanel.this,
                   "Выберите папки для сравнения файлов!");
            return;
        }
        final List<List<String>> result = new ArrayList<>();
        final FileTwinsUtils fileTwinsUtils = new FileTwinsUtils(root1, root2);
        Map<FileTwinsUtils.CompareFile, List<FileTwinsUtils.CompareFile>> resultMap = fileTwinsUtils.compareFiles();
        resultMap.forEach((compareFile1, compareFile2) -> {
            final List<String> twins = new ArrayList<>();
            twins.add(compareFile1.getAbsPath());
            compareFile2.forEach(file -> twins.add(file.getAbsPath()));
            result.add(twins);
        });
        final List<List<JLabel>> labels = new ArrayList<>();
        for (List<String> list : result) {
            List<JLabel> groupeTwinsLabels = new ArrayList<>();
            list.forEach(s -> {
                JLabel label = new JLabel();
                label.setText(s);
                groupeTwinsLabels.add(label);
            });
            labels.add(groupeTwinsLabels);
        }
        if (labels.isEmpty()){
            JOptionPane.showMessageDialog(TwinJPanel.this,
                    "Одинаковых файлов не найдено");
            return;
        }
        resultView(labels);
    }

    private void resultView(List<List<JLabel>> labels) {
        JFrame found = new FoundJFrame();
        int i = labels.stream().mapToInt(Collection::size).sum();
        JPanel foundPanel = new FoundJPanel(i, labels);
        JScrollPane jScrollPane = new JScrollPane(foundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        found.getContentPane().add(jScrollPane);
        found.setVisible(true);
    }

}
