package KnParser;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacv.CanvasFrame;

public class LineDetector {

    private static String PATH_FOLDER = "Debug";
    private Cropper cropper;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy(HH#mm#ss)");
    
    public LineDetector(DatabaseManager dbm)
    {
        cropper = new Cropper(dbm);
    }
    
    public void Detect(String fileName) 
    {
        
        IplImage src = cvLoadImage(fileName, 0);
        IplImage dst;
        IplImage colorDst;
        CvMemStorage storage = cvCreateMemStorage(0);
        CvSeq lines = new CvSeq();

        ArrayList<Integer> linesVertical = new ArrayList<Integer>(); 
        ArrayList<Integer> linesHorizontal = new ArrayList<Integer>();                 
        

        if (src == null) {
            System.out.println("Couldn't load source image.");
            return;
        }

        //Drawing left and top lines for line detection
        cvLine(src, opencv_core.cvPoint(0,0), opencv_core.cvPoint(src.width()-10, 0), CvScalar.BLACK, 6, CV_AA, 0);
        cvLine(src, opencv_core.cvPoint(0,0), opencv_core.cvPoint(0, src.height()-10), CvScalar.BLACK, 6, CV_AA, 0);
        
        dst = cvCreateImage(cvGetSize(src), src.depth(), 1);
        colorDst = cvCreateImage(cvGetSize(src), src.depth(), 3);

        cvCanny(src, dst, 50, 200, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);
        
        
        lines = cvHoughLines2(dst, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 18, 50, 1000, 20);     //All lines // (..., 50, 1000, 20)           
        
        linesHorizontal = SortLines(lines, 0);
        linesVertical = SortLines(lines, 1);                
        
        
        //System.out.println("Final ArrayHor" + linesHorizontal);
        //System.out.println("Final ArrayVer" + linesVertical);
        
        
        // Draw all green lines
        for (int i = 0; i < lines.total(); i++) {
            // Based on JavaCPP, the equivalent of the C code:
            // CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
            // CvPoint first=line[0], second=line[1]
            // is:
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);

            cvLine(colorDst, pt1, pt2, CV_RGB(0, 255, 0), 3, CV_AA, 0); // draw the segment on the image                    
        }
        
        
        cvSaveImage(PATH_FOLDER + "/" + dateFormat.format(new Date()) + "_Source.jpg", src);
        cvSaveImage(PATH_FOLDER + "/" + dateFormat.format(new Date()) + "_Line.jpg", colorDst);
        
        cropper.Crop(src, linesVertical, linesHorizontal);
    }
    
    private static ArrayList<Integer> SortLines(CvSeq lines, int orientation) // 0 - Vertical, 1 - Horizontal
    { 
        ArrayList<Integer> linesArr = new ArrayList<Integer>();                        
        ArrayList<Integer> tempArr = new ArrayList<Integer>();                        
        ArrayList<Integer> linesFinalArr = new ArrayList<Integer>();                
        
        for (int i = 0; i < lines.total(); i++) 
        {            
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            
            /*
            System.out.print("Line spotted: ");
            System.out.print("\t pt1: " + pt1);
            System.out.println("\t pt2: " + pt2);
            */
            
            if (orientation == 1){
                if(pt1.x() == pt2.x())
                {
                    linesArr.add(pt1.x());
                }
            }
            else
            {
                if(pt1.y() == pt2.y())
                {
                    linesArr.add(pt1.y());
                }
            }
        }
        
        Collections.sort(linesArr);                
        //System.out.println("ArrayList" + linesArr);
        
        //Calculate average dx for lines
        int dx = 0;        
        
        for (int i = 0; i < linesArr.size()-1; i++)
        {
            dx += linesArr.get(i+1) - linesArr.get(i);
        }        
        
        int dxAvarage = dx / linesArr.size();
        //System.out.println("dxAvarage: " + dxAvarage);
        
        // ==================
        tempArr.add(linesArr.get(0));
        
        for (int i = 1, j = 0; i < linesArr.size(); i++, j++)
        {
            if( linesArr.get(i) - tempArr.get(j) < dxAvarage)
            {
                tempArr.add(linesArr.get(i));
                
                if (i == linesArr.size() -1)
                {
                    //System.out.println("Temp" + tempArr);

                    linesFinalArr.add(Collections.min(tempArr));
                }
            }
            else
            {
                //System.out.println("Temp" + tempArr);
                
                if(linesFinalArr.isEmpty())
                {
                    linesFinalArr.add(Collections.max(tempArr));                                        
                }                
                else
                {
                    linesFinalArr.add(Collections.min(tempArr));
                    linesFinalArr.add(Collections.max(tempArr));
                }
                
                tempArr.clear();                
                tempArr.add(linesArr.get(i));
                j = -1;
            }
        }                
        
        return linesFinalArr;
    }
}