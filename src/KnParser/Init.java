/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Kairat
 */
public class Init 
{
    
    public static void main(String[] args) 
    {
        FileManager.InitFileManager();
        PMInitializer.PMInitialize();
        ImageParser.InitTextRecognizer();
        DatabaseManager dbm = new DatabaseManager();
        LineDetector ld = new LineDetector(dbm);

        //ld.Detect("Scan1.png");
        
        
        while(true)
        {
            JFileChooser openFile = new JFileChooser(System.getProperty("user.home") + "\\" + "Desktop");
            int stat = openFile.showOpenDialog(null);

            if (stat == JFileChooser.APPROVE_OPTION) {
                String file = openFile.getSelectedFile().getAbsolutePath();
                //System.out.println(file);

                int response = JOptionPane.showConfirmDialog(null, "I you sure that you want to load? \n" + file);
                
                if(response == 0)
                    ld.Detect(file);
            }
            else
            {
                break;
            }
        }
    }
}
