/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseAPI;

import java.util.ArrayList;

/**
 *
 * @author Hassan
 */
public class Column {

    private final String columnName;
    private final String columnDataType;

    private final ArrayList<String> columnsDatas = new ArrayList<>();

    public Column() {
        this.columnName="";
        this.columnDataType="";
    }

    public Column(String columnName, String columnDataType) {
        this.columnName = columnName;
        this.columnDataType = columnDataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public ArrayList<String> getColumnsDatas() {
        return columnsDatas;
    }

    public void addData(String data) {
        columnsDatas.add(data);
    }
}
