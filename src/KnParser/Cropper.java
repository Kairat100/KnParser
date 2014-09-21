/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

/**
 *
 * @author Kairat
 */

public class Cropper 
{    
    private static String PATH_FOLDER = "Debug";
    private ArrayList<Flat> Flats;
    CvFont font;
    private int dx = 3;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy(HH#mm#ss)");
    DatabaseManager dbm;
    
    public Cropper(DatabaseManager dbm) 
    {   
        this.dbm = dbm;
        
        font = new CvFont();
        cvInitFont(font, CV_FONT_HERSHEY_SIMPLEX, 0.5, 0.5, 1.0, 0, 0); // cvInitFont(font, fontFace, fontScale, fontScale, 0, thickness, CV_AA);
    }       
    
    public void Crop(IplImage src, ArrayList<Integer> linesV, ArrayList<Integer> linesH)
    {
        IplImage image = cvCloneImage(src);
        
        Flats = new ArrayList<Flat>();        
        
        
        // Start  text recognition
        for(int i = 0; i < linesH.size() ; i+=2)
        {
            Flat flat = new Flat();
            IplImage img1;
            
            String tempString = "";
            //CvPoint point1 = new CvPoint();
            //CvPoint point2 = new CvPoint();
            
            int x;
            int y = linesH.get(i) + dx;
            int width;
            int height = linesH.get(i+1) - y - dx;            
            
                        
            //======== Get Room count
            x = linesV.get(0) + dx;
            width = linesV.get(1) - x - dx;
                        
            cvSetImageROI(image,cvRect(x, y, width, height));            
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);
            //System.out.println("Rooms: " + recognizeText(img1));

            tempString = ImageParser.getText(img1, PMInitializer.pMRooms);
            flat.setRooms(tempString);
            //System.out.println("Rooms" + flat.getRooms());
            
            cvResetImageROI(image);            
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);            
            
            
            
            
            //======== Get Floor count
            x = linesV.get(2) + dx;
            width = linesV.get(3) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
        
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);
            //System.out.println("Floor:" + recognizeText(img1));
            
            tempString = ImageParser.getText(img1, PMInitializer.pMFloor);
            flat.setFloors(tempString);
            //System.out.println("Floor: " + flat.getFloor() + " ,total: " + flat.getFloorTotal());
            
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //======== Get Material type
            x = linesV.get(6) + dx;
            width = linesV.get(7) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);
            
            //tempString = "patterns\\patt" + i + ".jpg";
            //cvSaveImage(tempString, img1);
            
            // Check string
            tempString = ImageParser.getText(img1, PMInitializer.pMMatType);//PMInitializer.pMMatType.pmMatDetection(img1);
            flat.setMaterialType(tempString);
            
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //======== Get Square Life
            x = linesV.get(8) + dx;
            width = linesV.get(9) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);

            //System.out.println("SquareLife: " + recognizeText(img1));
            tempString = ImageParser.getText(img1, PMInitializer.pMSquareLife);
            flat.setSquareLife(tempString); 
            
            //System.out.println("SquareLife: " + flat.getSquareLife());
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //======== Get Square Kitchen
            x = linesV.get(10) + dx;
            width = linesV.get(11) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);

            //System.out.println("SquareKitchen: " + recognizeText(img1));
            tempString = ImageParser.getText(img1, PMInitializer.pMSquareKit);
            flat.setSquareKitchen(tempString);     
            
            //System.out.println("SquareKitchen: " + flat.getSquareKitchen());
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //======== Get Price
            x = linesV.get(14) + dx;
            width = linesV.get(15) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);

            //System.out.println("Price: " + recognizeText(img1));
            tempString = ImageParser.getText(img1, PMInitializer.pMPrice);
            flat.setPrice(tempString);
                        
            //System.out.println("Price: " + flat.getPrice());
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, tempString, opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //======== Get Phones
            x = linesV.get(18) + dx;
            width = linesV.get(19) - x - dx;
            
            cvSetImageROI(image,cvRect(x, y, width, height));
            
            img1 = cvCreateImage(cvSize(width,height),image.depth(), 1);
            cvCopy(image,img1);
                        
            //System.out.println("Phones: " + recognizeText(img1));
            tempString = ImageParser.getText(img1, null);
            flat.setPhones(tempString);
                       
            //System.out.println("Phone1: " + flat.getPhone1()+ " ,Phone2: " + flat.getPhone2());
            cvResetImageROI(image);
            //For debugging
            cvRectangle(image, opencv_core.cvPoint(x,y), opencv_core.cvPoint(x+width,y+height), CvScalar.RED, 1, 8, 0);
            cvPutText(image, flat.getPhone1() + "," + flat.getPhone2(), opencv_core.cvPoint(x+1,y+12), font, CvScalar.BLACK);
            
            
            
            
            
            //==========================
            Flats.add(flat);         
        }
        
        dbm.putToDB(Flats);
        
        /*
        //print all results
        for (int i = 0; i < Flats.size(); i++)
            System.out.println(Flats.get(i));

        System.out.println("\n\n\n");
        */
        
        //draw lines for debuging
        for (int i = 0; i < linesH.size(); i++)
        {
            cvLine(image, opencv_core.cvPoint(linesV.get(0),linesH.get(i)), opencv_core.cvPoint(linesV.get(linesV.size()-1),linesH.get(i)), CvScalar.WHITE, 1, CV_AA, 0);
        }
        
        for(int j = 0; j < linesV.size(); j++)
        {
            cvLine(image, opencv_core.cvPoint(linesV.get(j),linesH.get(0)), opencv_core.cvPoint(linesV.get(j),linesH.get(linesH.size()-1)), CvScalar.WHITE, 1, CV_AA, 0);
        }
        
        cvSaveImage(PATH_FOLDER + "/" + dateFormat.format(new Date()) + "_Debug.jpg", image);
        ImageParser.DisposeLD();
        //lp.showPanel();
        //end of Constructor
    }
}
