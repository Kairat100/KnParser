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
public class LearningDialog extends JFrame{
    
    public LearningDialog()
    {
        super("Learning Dialog");
    }
    
    public String getUserInput(IplImage img)
    {
        return (String)JOptionPane.showInputDialog(
                this,
                "Input Number on the Picture",
                "Dialog",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(img.getBufferedImage()),
                null,
                null);
    }
    
    public void Close()
    {
        this.setVisible(false); //you can't see me!
        this.dispose(); //Destroy the JFrame object
    }
}
