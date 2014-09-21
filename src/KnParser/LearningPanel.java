/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import javax.swing.*;
import java.awt.*;
import org.bytedeco.javacpp.opencv_core.*;

/**
 *
 * @author Kairat
 */
public class LearningPanel extends JFrame
{
    JPanel panel;
    Font font1 = new Font("SansSerif", Font.PLAIN, 20);
    
    public LearningPanel()
    {
        super("Learning Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel(new GridLayout(0, 3));
        
        JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(pane);
    }
    
    public void addLine(IplImage img)
    {
        JLabel label = new JLabel(new ImageIcon(img.getBufferedImage()));
        
        JTextField textF = new JTextField();
        textF.setFont(font1);
        
        JCheckBox checkbox = new JCheckBox();
        
        panel.add(label);
        panel.add(textF);
        panel.add(checkbox);
    }
    
    public void showPanel()
    {
        pack();
        setSize(getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70);                       
        setVisible(true);
    }
}
