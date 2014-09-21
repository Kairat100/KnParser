/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 *
 * @author Kairat
 */
public class ImageParser {
 
    private static TextRecognizer textRec;
    private static LearningDialog ld;
    
    public static void InitTextRecognizer()
    {
        textRec = new TextRecognizer();
        ld = new LearningDialog();
    }
            
    public static String getText(IplImage img, PatternMatching pm)
    {
        String result;
        
        if(pm == PMInitializer.pMMatType)
        {
            result = pm.pmMatDetection(img);
        }
        else if(pm == PMInitializer.pMRooms)
            {
                result = pm.pmMatDetection(img);
            }
            else
            {
                result = textRec.recognizeText(img);
                
                if(result.isEmpty() || result.equals(""))
                {
                    result = pm.pmMatDetection(img);
                }
            }
        
        if(result.isEmpty() || result.equals(""))
        {
            result = ld.getUserInput(img);
            UpdatePatters(pm, img, result);
        }
        
        return result;
    }
    
    private static void UpdatePatters(PatternMatching pm, IplImage img, String value)
    {
        IplImage imgTemp = cropImage(img);
        
        if(pm == PMInitializer.pMRooms)
        {
            cvSaveImage(FileManager.pathRooms + "/mat"+ FileManager.patternsRooms.size() + ".jpg", imgTemp);
            
            FileManager.patternsRooms.add(imgTemp);
            FileManager.valuesRooms.add(value);                                  
            
            PMInitializer.InitPMRooms();
                        
        }else if(pm == PMInitializer.pMFloor)
        {            
            cvSaveImage(FileManager.pathFloor + "/mat"+ FileManager.patternsFloor.size() + ".jpg", imgTemp);
            
            FileManager.patternsFloor.add(imgTemp);
            FileManager.valuesFloor.add(value);                                  
            
            PMInitializer.InitPMFloor();

        }else if(pm == PMInitializer.pMMatType)
        {
            cvSaveImage(FileManager.pathMatType + "/mat"+ FileManager.patternsMatType.size() + ".jpg", imgTemp);
            
            FileManager.patternsMatType.add(imgTemp);
            FileManager.valuesMatType.add(value);                                  
            
            PMInitializer.InitPMMatType();
            
        }else if(pm == PMInitializer.pMPrice)
        {
            cvSaveImage(FileManager.pathPrice + "/mat"+ FileManager.patternsPrice.size() + ".jpg", imgTemp);
            
            FileManager.patternsPrice.add(imgTemp);
            FileManager.valuesPrice.add(value);                                  
            
            PMInitializer.InitPMPrice();
            
        }else if(pm == PMInitializer.pMSquareKit)
        {
            cvSaveImage(FileManager.pathSquareKit + "/mat"+ FileManager.patternsSquareKit.size() + ".jpg", imgTemp);
            
            FileManager.patternsSquareKit.add(imgTemp);
            FileManager.valuesSquareKit.add(value);                                  
            
            PMInitializer.InitPMSquareKit();
            
        }else if(pm == PMInitializer.pMSquareLife)
        {
            cvSaveImage(FileManager.pathSquareLife + "/mat"+ FileManager.patternsSquareLife.size() + ".jpg", imgTemp);
            
            FileManager.patternsSquareLife.add(imgTemp);
            FileManager.valuesSquareLife.add(value);                                  
            
            PMInitializer.InitPMSquareLife();
        }
    }
    
    private static IplImage cropImage(IplImage img)
    {
        IplImage dst;
        IplImage colorDst;
        
        dst = cvCreateImage(cvGetSize(img), img.depth(), 1);
        colorDst = cvCreateImage(cvGetSize(img), img.depth(), 3);

        cvCanny(img, dst, 50, 200, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);
        
        ByteBuffer buffer = dst.getByteBuffer();

        int dxLeft = 0;
        int dxRight = 0;
        int dyTop = 0;
        int dyBottom = 0;
        
        // Find top Limit
        for(int y = 0; y < dst.height(); y++) 
        {
            for(int x = 0; x < dst.width(); x++) 
            {
                int index = y * dst.widthStep() + x * dst.nChannels();
                int value = buffer.get(index) & 0xFF;               
                
                if(value == 255){
                    dyTop = y;                    
                    break;
                }
            }
            
            if(dyTop != 0)
            {
                dyTop = dyTop/3*2;
                //cvLine(img, opencv_core.cvPoint(0,dyTop), opencv_core.cvPoint(img.width(), dyTop), CvScalar.BLACK, 1, CV_AA, 0);
                break;
            }
        }
        
        // Find bottom Limit
        for(int y = dst.height()-1; y >=0; y--) 
        {
            for(int x = 0; x < dst.width(); x++) 
            {
                int index = y * dst.widthStep() + x * dst.nChannels();
                int value = buffer.get(index) & 0xFF;               
                
                if(value == 255){
                    dyBottom = y;                    
                    break;
                }
            }
            
            if(dyBottom != 0)
            {
                dyBottom = (dst.height() - dyBottom)/3 + dyBottom;
                //cvLine(img, opencv_core.cvPoint(0,dyBottom), opencv_core.cvPoint(img.width(), dyBottom), CvScalar.BLACK, 1, CV_AA, 0);
                break;
            }
        }
        
        // Find Left Limit
        for(int x = 0; x < dst.width(); x++) 
        {
            for(int y = 0; y < dst.height(); y++) 
            {
                int index = y * dst.widthStep() + x * dst.nChannels();
                int value = buffer.get(index) & 0xFF;               
                
                if(value == 255){
                    dxLeft = x;                    
                    break;
                }
            }
            
            if(dxLeft != 0)
            {
                dxLeft /= 2;
                //cvLine(img, opencv_core.cvPoint(dxLeft,0), opencv_core.cvPoint(dxLeft, img.height()), CvScalar.BLACK, 1, CV_AA, 0);
                break;
            }
        }
        
         // Find Right Limit
        for(int x = dst.width() - 1; x >= 0; x--) 
        {
            for(int y = 0; y < dst.height(); y++) 
            {
                int index = y * dst.widthStep() + x * dst.nChannels();
                int value = buffer.get(index) & 0xFF;               
                
                if(value == 255){
                    dxRight = x;                    
                    break;
                }
            }
            
            if(dxRight != 0)
            {
                dxRight = (dst.width() - dxRight)/2 + dxRight;
                //cvLine(img, opencv_core.cvPoint(dxRight,0), opencv_core.cvPoint(dxRight, img.height()), CvScalar.BLACK, 1, CV_AA, 0);
                break;
            }
        }
        
        IplImage imgCropped;
        cvSetImageROI(img,cvRect(dxLeft, dyTop, dxRight - dxLeft, dyBottom - dyTop));
        
        imgCropped = cvCreateImage(cvSize(dxRight - dxLeft, dyBottom - dyTop), img.depth(), 1);
        cvCopy(img,imgCropped);
        
        //cvSaveImage("Mat111.jpg", dst);
        //cvSaveImage("Mat112.jpg", imgCropped);
        
        return imgCropped;
    }
    
    public static void DisposeLD()
    {
        SaveIndexFile(FileManager.pathRooms, FileManager.valuesRooms);
        SaveIndexFile(FileManager.pathFloor, FileManager.valuesFloor);
        SaveIndexFile(FileManager.pathMatType, FileManager.valuesMatType);
        SaveIndexFile(FileManager.pathPrice, FileManager.valuesPrice);
        SaveIndexFile(FileManager.pathSquareKit, FileManager.valuesSquareKit);
        SaveIndexFile(FileManager.pathSquareLife, FileManager.valuesSquareLife);
        
        ld.Close();
    }
    
    private static void SaveIndexFile(String path, ArrayList<String> values)
    {
        try
        {
            PrintWriter out = new PrintWriter(path + "/index.txt");
            
            for(int i = 0; i < values.size(); i++)
            {
                out.println("mat" + i + ".jpg-" + values.get(i));
            }
            
            out.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File not found: " + e);
        }
    }
}
