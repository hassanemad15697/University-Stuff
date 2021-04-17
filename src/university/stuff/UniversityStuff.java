/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.stuff;

import DataBaseAPI.*;

import java.sql.SQLException;
import java.util.ArrayList;
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

//            SignIn s = new SignIn();
//            s.setVisible(true);
        Table columnsHolder;
        DataBase myDB = new DataBase();
        try {
            myDB.connectToDataBase();
            //myDB.showAllTableData("user");
            //myDB.showColumnsDataWithConditionByOrder("personal_info", "name,mobile","id<4","birth");
  //              myDB.showColumnsData("staff", "id,job");
            //myDB.showColumnsDataWithJoin("personal_info , staff", "name ,department , job", "personal_info.id=staff.id and staff.id=2");
            
            //myDB.showOneColumnDataWithConditionByOrder("personal_info", "name","id<6","birth");
//            
            myDB.showColumnsDataWithJoin("personal_info,staff", "department,name,job", "personal_info.id=staff.id and staff.id=2");
//
//            if (columnsHolder == null) {
//                System.err.println("null");
//            } else {
//                for (Column col : columnsHolder.getColumnsNames()) {
//                    if (col.getColumnsDatas() != null) {
//                        System.out.print(col.getColumnName() + "\t\t");
//                        for (String columnsData : col.getColumnsDatas()) {
//                            System.out.print(columnsData + "\t");
//                        }
//                    }
//                    System.out.println("");
//                }
//            }
//            
            
            
        } catch (SQLException ex) {

        } finally {
            try {
                myDB.closeDataBaseConnection();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
