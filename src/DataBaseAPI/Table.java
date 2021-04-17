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
public class Table {

    private String tableName;
    private final ArrayList<Column> columnsNames = new ArrayList<>();

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void addColumn(String columnName, String columnDataType) {
        columnsNames.add(new Column(columnName, columnDataType));
    }

    public ArrayList<Column> getColumnsNames() {
        return columnsNames;
    }

}
