package GUI;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame {
    private JPanel panel;
    private JFrame frame;
    public SimulationFrame(){
        instantiateViews();
        createFrame();
        declaringListeners();
    }
    private void createFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculator");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(25, 24, 24));
    }
    private void instantiateViews() {
        panel = new JPanel();
        frame = new JFrame();
    }
    private void declaringListeners() {
    }
}
