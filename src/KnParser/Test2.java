/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

/**
 *
 * @author Kairat
 */

import java.util.Arrays;    
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Test2{
/*    public static void main (String args[]){
        IplImage src = cvLoadImage("src/phone2.jpg",0);
        IplImage tmp = cvLoadImage("src/temp4.1.jpg",0);	

        IplImage result = cvCreateImage(cvSize(src.width()-tmp.width()+1, src.height()-tmp.height()+1), IPL_DEPTH_32F, 1);
        cvZero(result);

        //Match Template Function from OpenCV
        cvMatchTemplate(src, tmp, result, CV_TM_CCOEFF_NORMED);

        double[] min_val = new double[2];
        double[] max_val = new double[2];

        int[] point1 = new int[2];
        int[] point2 = new int[2];
        
        //Get the Max or Min Correlation Value		
        //cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);
        cvMinMaxLoc(result, min_val, max_val, point1, point2, null);

        System.out.println(Arrays.toString(min_val));
        System.out.println(Arrays.toString(max_val));
      
        CvPoint minLoc = new CvPoint();
        minLoc.x(point1[0]);
        minLoc.y(point1[1]);
        
        CvPoint maxLoc = new CvPoint();
        maxLoc.x(point2[0]);
        maxLoc.y(point2[1]);
        
        CvPoint point = new CvPoint();
        point.x(maxLoc.x()+tmp.width());
        point.y(maxLoc.y()+tmp.height());

        cvRectangle(src, maxLoc, point, CvScalar.WHITE, 2, 8, 0);//Draw a Rectangle for Matched Region

        cvShowImage("Number matching", src);
        cvWaitKey(0);
        cvReleaseImage(src);
        cvReleaseImage(tmp);
        cvReleaseImage(result);
    }
*/
}
