package org.dubna.main.ui;

import javax.swing.*;
import java.awt.*;

public class TwinJFrame extends JFrame {
    public TwinJFrame() throws HeadlessException {
        this.setSize(500, 200);
        this.setLocation(500, 500);
        this.setTitle("Поиск дубликатов файлов");
     //   this.setFont(new Font());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // this.setLayout(new GridLayout(3, 1));
        this.getContentPane().add(new TwinJPanel());
    }
}
