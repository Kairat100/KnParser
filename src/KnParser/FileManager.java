/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import java.io.*;
import java.util.*;
import org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;

/**
 *
 * @author Kairat
 */
public class FileManager 
{
    public static String pathFloor = "patts/Floor";
    public static ArrayList<IplImage> patternsFloor;
    public static ArrayList<String> valuesFloor;
    
    public static String pathMatType = "patts/MatType";
    public static ArrayList<IplImage> patternsMatType;
    public static ArrayList<String> valuesMatType;
    
    public static String pathPrice = "patts/Price";
    public static ArrayList<IplImage> patternsPrice;
    public static ArrayList<String> valuesPrice;
    
    public static String pathRooms = "patts/Rooms";
    public static ArrayList<IplImage> patternsRooms;
    public static ArrayList<String> valuesRooms;
    
    public static String pathSquareKit = "patts/SquareKit";
    public static ArrayList<IplImage> patternsSquareKit;
    public static ArrayList<String> valuesSquareKit;
    
    public static String pathSquareLife = "patts/SquareLife";
    public static ArrayList<IplImage> patternsSquareLife;
    public static ArrayList<String> valuesSquareLife;
    
    public static void InitFileManager()
    {
        ArrayList<ArrayList<String>> arrays;

        arrays = getArrays(pathFloor);
        patternsFloor = loadImages(arrays.get(0));
        valuesFloor = arrays.get(1);
        
        arrays = getArrays(pathMatType);
        patternsMatType = loadImages(arrays.get(0));
        valuesMatType = arrays.get(1);
        
        arrays = getArrays(pathPrice);
        patternsPrice = loadImages(arrays.get(0));
        valuesPrice = arrays.get(1);
        
        arrays = getArrays(pathRooms);
        patternsRooms = loadImages(arrays.get(0));
        valuesRooms = arrays.get(1);
        
        arrays = getArrays(pathSquareKit);
        patternsSquareKit = loadImages(arrays.get(0));
        valuesSquareKit = arrays.get(1);
        
        arrays = getArrays(pathSquareLife);
        patternsSquareLife = loadImages(arrays.get(0));
        valuesSquareLife = arrays.get(1);
    }
    
    
    private static ArrayList<ArrayList<String>> getArrays(String path)
    {
        ArrayList<ArrayList<String>> arrays = new ArrayList<ArrayList<String>>();
        ArrayList<String> pMatUrls = new ArrayList<String>();
        ArrayList<String> pMatValues = new ArrayList<String>();
    
        try(BufferedReader br = new BufferedReader(new FileReader(path + "/index.txt"))) 
        {
            for(String line; (line = br.readLine()) != null; ) 
            {
                String[] temp = line.split("-");
                pMatUrls.add(path + "/" +temp[0]);
                pMatValues.add(temp[1]);
                
                //System.out.println(pMatUrls.get(pMatUrls.size()-1) + " -> " + temp[1]);
            }
            // line is not visible here.
        }catch(IOException err)
        {
            System.err.println("Loadint Pattern file error: " + err);
        }
        
        arrays.add(pMatUrls);
        arrays.add(pMatValues);
        
        return arrays;
    }
    
    
    private static ArrayList<IplImage> loadImages(ArrayList<String> urls)
    {
        ArrayList<IplImage> imgs = new ArrayList<IplImage>();
        
        for(int i = 0; i < urls.size(); i++)
        {
            imgs.add(cvLoadImage(urls.get(i), 0));            
            
            if (imgs.get(i) == null) {
                System.out.println("Couldn't load source image: " + urls.get(i));
                break;
            }
        }
        
        return imgs;
    } 
}
