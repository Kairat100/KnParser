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
public class Flat 
{
    private int rooms;
    private int floor;
    private int floorTotal;
    private float squareLife;
    private float squareKitchen;
    private int price;
    private String materialType;    
    private String phone1;
    private String phone2;

    public Flat() { }
    
    public int getRooms() {
        return rooms;
    }

    public void setFloors(String str)
    {
        if(str.contains("/"))
        {
            String[] floorsStr = str.split("/");

            try{
                this.setFloor(Integer.parseInt(floorsStr[0]));
            }catch(NumberFormatException e){
                System.err.println(e);
            }

            try{
                this.setFloorTotal(Integer.parseInt(floorsStr[1]));
            }catch(NumberFormatException e){
                System.err.println(e);
            }
        }
    }
    
    public void setRooms(String str) {
        try{
            this.rooms = Integer.parseInt(str);    
        }catch(NumberFormatException e){
            System.err.println(e);
        }
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloorTotal() {
        return floorTotal;
    }

    public void setFloorTotal(int floorTotal) {
        
        
        
        this.floorTotal = floorTotal;
    }

    public float getSquareLife() {
        return squareLife;
    }

    public void setSquareLife(String str) {
        try{           
            this.squareLife = Float.parseFloat(str.replace(",", "."));
        }catch(NumberFormatException e){
            System.err.println(e);
        }        
    }

    public float getSquareKitchen() {
        return squareKitchen;
    }

    public void setSquareKitchen(String str) {
        try{           
            this.squareKitchen = Float.parseFloat(str.replace(",", "."));
        }catch(NumberFormatException e){
            System.err.println(e);
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(String str) {
        try{
            this.price = Integer.parseInt(str);    
        }catch(NumberFormatException e){
            System.err.println(e);
        }
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhones(String phones)
    {
        if(phones.contains(","))      // if two phones
        {
            String[] phonesStr = phones.split(","); 
            this.setPhone1(checkPhone(phonesStr[0]));
            this.setPhone2(checkPhone(phonesStr[1]));
        }else                           // if one phone
        {
            this.setPhone1(checkPhone(phones));
        }
    }
    
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }        
    
    //Check phone
    private String checkPhone(String phone)
    {
        if(phone.length()!= 11 && phone.length()!= 6)
        {
            if(phone.length()>11)
            {
                phone = phone.substring(phone.length()-11, phone.length());
            }else if(phone.length()>6)
            {
                phone = phone.substring(phone.length()-6, phone.length());
            }
        }
        
        return phone;
    }
    
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        result.append("Rooms: ");
        result.append(this.getRooms() + "\t");
        
        result.append("Floor: ");
        result.append(this.getFloor() + "\t");
        
        result.append("FloorTotal: ");
        result.append(this.getFloorTotal()+ "\t");
        
        result.append("Material Type: ");
        result.append(this.getMaterialType()+ "\t");
        
        result.append("SquareLife: ");
        result.append(this.getSquareLife()+ "\t");
        
        result.append("SquareKitchen: ");
        result.append(this.getSquareKitchen()+ "\t");
        
        result.append("Price: ");
        result.append(this.getPrice()+ "\t");
        
        result.append("Phone1: ");
        result.append(this.getPhone1()+ "\t");
        
        result.append("Phone2: ");
        result.append(this.getPhone2()+ "");
        
        return result.toString();
    }
}
