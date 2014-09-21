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
public class PMInitializer 
{
    public static PatternMatching pMFloor;
    public static PatternMatching pMMatType;
    public static PatternMatching pMPrice;
    public static PatternMatching pMRooms;
    public static PatternMatching pMSquareKit;
    public static PatternMatching pMSquareLife;
    
    public static void PMInitialize()
    {
        InitPMFloor();
        InitPMMatType();
        InitPMPrice();
        InitPMRooms();
        InitPMSquareKit();
        InitPMSquareLife();
    }
    
    public static void InitPMFloor()
    {
        pMFloor = new PatternMatching(FileManager.patternsFloor, FileManager.valuesFloor);
    }
    
    public static void InitPMMatType()
    {
        pMMatType = new PatternMatching(FileManager.patternsMatType, FileManager.valuesMatType);
    }
    
    public static void InitPMPrice()
    {
        pMPrice = new PatternMatching(FileManager.patternsPrice, FileManager.valuesPrice);
    }
    
    public static void InitPMRooms()
    {
        pMRooms = new PatternMatching(FileManager.patternsRooms, FileManager.valuesRooms);
    }
    
    public static void InitPMSquareKit()
    {
        pMSquareKit = new PatternMatching(FileManager.patternsSquareKit, FileManager.valuesSquareKit);
    }
    
    public static void InitPMSquareLife()
    {
        pMSquareLife = new PatternMatching(FileManager.patternsSquareLife, FileManager.valuesSquareLife);
    }
}
