/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KnParser;

import com.mysql.jdbc.*;
import com.sun.corba.se.impl.util.Version;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kairat
 */
public class DatabaseManager 
{
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
        
    public DatabaseManager()
    {
        String url = "jdbc:mysql://localhost:3306/kndb";
        String user = "root";
        String password = "";

        try {
            con = (Connection) DriverManager.getConnection(url, user, password);
            st = (Statement) con.createStatement();
            /*rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }*/
            
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
    
    public void putToDB(ArrayList<Flat> Flats)
    {
        try {
            
            Flat flat;
            String request;
                    
            for(int i = 0; i < Flats.size(); i++)
            {
                flat = Flats.get(i);
                
                request = "select count(*) from flat where rooms = " + flat.getRooms() + " and floor = " + flat.getFloor() 
                        + " and floortotal = " + flat.getFloorTotal() + " and price = " + flat.getPrice() 
                        + " and (phone1 = '" + flat.getPhone1() + "' or phone2 = '" + flat.getPhone1() + "')";
                
                rs = st.executeQuery(request);
                int count = 0;
                
                if (rs.next()) {
                    count = rs.getInt(1);
                    //System.out.println(count);
                }
                                
            
                request = "INSERT INTO flat (duplication, rooms, floor, floortotal, squareLife, squareKitchen, price, materialType, phone1, phone2) "
                                        + "VALUES (" + count + "," + flat.getRooms() + "," + flat.getFloor() + "," + flat.getFloorTotal() + ","
                                        + flat.getSquareLife() + "," + flat.getSquareKitchen() + "," + flat.getPrice() + ", '"
                                        + flat.getMaterialType()+ "','" + flat.getPhone1() + "','" + flat.getPhone2() + "')";

                st.executeUpdate(request);
            }
            
        }catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
    public void CloseDBConnection()
    {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
