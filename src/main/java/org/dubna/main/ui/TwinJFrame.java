package org.dubna.main.ui;

import javax.swing.*;
import java.awt.*;

public class TwinJFrame extends JFrame {
    public TwinJFrame() throws HeadlessException {
        this.setSize(500, 200);
        this.setLocation(500, 300);
        this.setTitle("Поиск дубликатов файлов");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new TwinJPanel());
    }
}
