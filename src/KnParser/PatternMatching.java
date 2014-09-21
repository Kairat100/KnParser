/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 *
 * @author Kairat
 */
public class PatternMatching 
{
    private ArrayList<IplImage> patterns;
    private ArrayList<String> values;
    
    public PatternMatching(ArrayList<IplImage> patImgs, ArrayList<String> patValues)
    {
        patterns = patImgs;
        values = patValues;
    }
    
    public String pmMatDetection(IplImage img)
    {
        int index = 0;
        double maxVal = 0.0;
        double val = 0.0;
        
        for(int i = 0; i < patterns.size(); i++)
        {
            val = pmMatDetect(img, patterns.get(i));
            
            if(val > maxVal)
            {
                maxVal = val;
                index = i;
            }
        }
        
        //System.out.println("Maximum value: " + maxVal);
        
        if(maxVal > 0.9)
            return values.get(index);
        else
            return "";
    }
    
    private double pmMatDetect(IplImage src, IplImage pat)
    {
        if(src.width() > pat.width() && src.height() > pat.height())
        {
            IplImage result = cvCreateImage(    cvSize(src.width() - pat.width() + 1, src.height() - pat.height() + 1), 
                                                IPL_DEPTH_32F, 
                                                1);


            cvMatchTemplate(src, pat, result, CV_TM_CCOEFF_NORMED);

            double[] max_val = new double[2];
            int[] point = new int[2];

            cvMinMaxLoc(result, null, max_val, null, point, null);

            //System.out.println("PM max value: " + max_val[0]);

            return max_val[0];
        }
        else
        {
            return 0.0;
        }
    }
}
