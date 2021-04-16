/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.stuff;

import DataBaseAPI.DataBase;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassan
 */
public class UniversityStuff {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

            SignIn s = new SignIn();
            s.setVisible(true);

//        DataBase myDB = new DataBase();
//        try {
//            myDB.connectToDataBase();
//            //myDB.showColumnsDataWithCondition(" user", "responsibility , password", "id=1");
//            
//        } catch (SQLException ex) {
//            
//        }finally{
//            try {
//                myDB.closeDataBaseConnection();
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }

    }

}
