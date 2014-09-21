/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author Kairat
 */
public class TextRecognizer {
    
    private Tesseract instance;
    
    public TextRecognizer()
    {
        instance = Tesseract.getInstance();
        instance.setLanguage("mkn");
        instance.setTessVariable("tessedit_char_whitelist", "0123456789/,-");
    }
    
    // Text recognition
    public String recognizeText(opencv_core.IplImage img)
    {
        try {
            String result = clearString(instance.doOCR(img.getBufferedImage()));
            //if (result.isEmpty())
                //result = "111111111";
            
            return clearString(result);
            //System.out.println("Recognized Result: <"+result+">");
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }
    
    private String clearString(String str)
    {
        String strResult = "";
        
        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if(c >= 47 && c <= 57 || c == 44) // check 0-9/,
            {
                strResult += c;
            }                
        }
        
        return strResult;
    }
}
