package org.dubna.main.ui;

import javax.swing.*;

public final class FoundJFrame extends JFrame {
    private static final long serialVersionUID = 9056634724571813891L;

    public FoundJFrame() {
        this.setSize(1200, 700);
        this.setResizable(true);
        this.setLocation(500, 100);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Найденные файлы");
    }
}
