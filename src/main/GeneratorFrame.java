package main;

import main.GeneratorPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Jacob on 10/2/2016.
 */
class GeneratorFrame extends JFrame
{
    GeneratorPanel panel;
    public GeneratorFrame() throws IOException, CloneNotSupportedException
    {
        panel = new GeneratorPanel();
        this.add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024+16, 512+32));
        pack();
        setVisible(true);
    }

}
