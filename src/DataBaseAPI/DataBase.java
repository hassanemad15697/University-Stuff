package DataBaseAPI;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hassan Askar
 */
public class DataBase {

    private final String dataBaseName = "university_stuff";
    private final String username = "DBHassan",
            password = "ELKBEER36647126358#",
            host = "jdbc:mysql://localhost/" + dataBaseName;

    private Connection conn;
    private PreparedStatement stmnt;
    private ResultSet rslt;
    private DatabaseMetaData md;
    private ArrayList<Table> DataBase = new ArrayList<>();
    private ArrayList< Table.Column> column = new ArrayList<>();
    private ArrayList< String> data = new ArrayList<>();

    public ArrayList<Table> dataBaseStructure() throws SQLException {
        DataBase.clear();
        md = conn.getMetaData();
        ResultSet tables = md.getTables(null, null, "%", null);
        while (tables.next()) {
            DataBase.add(new Table(tables.getString("TABLE_NAME")));
        }

        for (Table table : DataBase) {
            ResultSet columns = md.getColumns(null, null, table.getTableName(), null);
            while (columns.next()) {
                table.addColumn(columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME"));
            }
        }

        return DataBase;

    }

    public void showDataBaseStructure() throws SQLException {

        for (Table table : dataBaseStructure()) {
            System.out.print(table.getTableName() + "\t:");
            for (Table.Column columns : table.getColumnsNames()) {
                System.out.print(columns.getColumnName() + "(" + columns.getColumnDataType() + ")" + "\t");
            }
            System.out.println();
        }
    }

    private ResultSet executeQuery(String query) throws SQLException {
        stmnt = conn.prepareStatement(query);
        return stmnt.executeQuery();
    }

    private void execute(String query) throws SQLException {
        stmnt = conn.prepareStatement(query);
        stmnt.execute();
    }

    public Connection connectToDataBase() throws SQLException {
        conn = DriverManager.getConnection(host, username, password);
        //execute("");
        dataBaseStructure();
        System.out.println("colsed!");
        return conn;
    }

    public void closeDataBaseConnection() throws SQLException {
        dataBaseStructure();
        conn.close();
        stmnt.close();
        System.out.println("Connected!");
    }

    public void createTable(String tableName, String columnsValues) throws SQLException {
        execute("CREATE TABLE " + tableName + "( " + columnsValues + " )");
        System.out.println("Table Created!!");
        dataBaseStructure();
    }

    public void renameTable(String oldTableName, String newTableName) throws SQLException {
        execute("ALTER TABLE " + oldTableName + " RENAME TO " + newTableName);
        System.out.println("Table name updated!!");
        dataBaseStructure();
    }

    public void deleteTable(String tableName) throws SQLException {
        execute("DROP TABLE " + tableName);
        System.out.println("Table deleted!!");
        dataBaseStructure();
    }

    public void renameColumn(String tableName, String oldColumnName, String newColumnNameWithDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " CHANGE " + oldColumnName + " " + newColumnNameWithDataType);
        System.out.println("Column name updated!!");
        dataBaseStructure();
    }

    public void modifyColumn(String tableName, String ColumnName, String newColumnDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " MODIFY " + ColumnName + " " + newColumnDataType);
        System.out.println("Column data types updated!!");
        dataBaseStructure();
    }

    public void addOneColumn(String tableName, String NewColumnName, String newColumnDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD " + NewColumnName + " " + newColumnDataType);
        System.out.println("Column " + NewColumnName + " added!!");
        dataBaseStructure();
    }

    public void addMultipleColumns(String tableName, String NewColumnsNames) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD " + "( " + NewColumnsNames + " )");
        System.out.println("Columns  added!!");
        dataBaseStructure();
    }

    public void addPrimaryKey(String tableName, String constraintName, String ColumnNames) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD PRIMARY KEY ( " + ColumnNames + " )");
        System.out.println("primary key  added!!");
        dataBaseStructure();
    }

    public void addForeignKey(String tableName, String columnName, String foreignTableName, String foreignColumnName) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD FOREIGN KEY ( " + columnName + " ) REFERENCES " + foreignTableName + "( " + foreignColumnName + " )");
        System.out.println("foreign key  added!!");
        dataBaseStructure();
    }

    public void DeleteColumn(String tableName, String ColumnName) throws SQLException {
        execute("ALTER TABLE " + tableName + " DROP " + ColumnName);
        System.out.println("Column " + ColumnName + " deleted!!");
        dataBaseStructure();
    }

    public void insertDataIntoSpecficColumns(String tableName, String columnsNames, String columnsValues) throws SQLException {
        execute("INSERT INTO " + tableName + " ( " + columnsNames + " ) " + " VALUES " + " ( " + columnsValues + " ) ");
        System.out.println("Row Inserted!!");
        dataBaseStructure();
    }

    public void insertDataIntoAllColumns(String tableName, String columnsValues) throws SQLException {
        execute("INSERT INTO " + tableName + " VALUES " + " ( " + columnsValues + " ) ");
        System.out.println("Row Inserted!!");
        dataBaseStructure();
    }

    public void updateDataIntoSpecficColumns(String tableName, String columnNameWithUpdatedData, String condition) throws SQLException {
        execute("UPDATE " + tableName + " SET " + columnNameWithUpdatedData + " WHERE " + condition);
        System.out.println("Data Updated!!");
        dataBaseStructure();
    }

    public void deleteRow(String tableName, String condition) throws SQLException {
        execute("DELETE FROM " + tableName + " WHERE " + condition);
        System.out.println("Row Data DELETED!!");
        dataBaseStructure();
    }

    public ArrayList<Table.Column> getAllTableData(String tableName) throws SQLException {

        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName)) {

                rslt = executeQuery("SELECT * FROM " + table.getTableName());
                if (rslt.next() == false) {
                    return null;
                } else {

                    //clear old data
                    for (Table.Column column : table.getColumnsNames()) {
                        column.getColumnsDatas().clear();
                    }
                    //adding new data
                    do {
                        for (Table.Column column : table.getColumnsNames()) {
                            column.addData(rslt.getString(column.getColumnName()));
                        }
                    } while (rslt.next());
                    return table.getColumnsNames();
                }
            }
        }
        return null;
    }

    public void showAllTableData(String tableName) throws SQLException {
        column = getAllTableData(tableName);
        if (column == null) {
            System.err.println("null");
        } else {
            for (Table.Column col : column) {
                System.out.print(col.getColumnName() + "\t\t");
                for (String columnsData : col.getColumnsDatas()) {
                    System.out.print(columnsData + "\t");
                }
                System.out.println("");
            }
        }

    }

    public ArrayList<String> getSpecificTableData(String tableName, String columns) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName)) {
                for (Table.Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columns)) {
                        rslt = executeQuery("SELECT " + columns + " FROM " + tableName);
                        if (rslt.next() == false) {
                            return null;
                        } else {
                            //delete old data
                            column.getColumnsDatas().clear();
                            //add new data
                            do {
                                column.addData(rslt.getString(column.getColumnName()));
                            } while (rslt.next());
                            return column.getColumnsDatas();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void showSpecificTableData(String tableName, String columns) throws SQLException {
        data = getSpecificTableData(tableName, columns);
        if (data == null) {
            System.err.println("null");
        } else {
            for (String d : data) {
                System.out.println(d);
            }
        }

    }

    public ArrayList<String> getSpecificTableDataWithCondition(String tableName, String columns, String condition) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName)) {
                for (Table.Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columns)) {
                        rslt = executeQuery("SELECT " + columns + " FROM " + tableName + " WHERE " + condition);
                        if (rslt.next() == false) {
                            return null;
                        } else {
                            //delete old data
                            column.getColumnsDatas().clear();
                            //add new data
                            do {
                                column.addData(rslt.getString(column.getColumnName()));
                            } while (rslt.next());
                            return column.getColumnsDatas();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void showSpecificTableDataWithCondition(String tableName, String columns, String condition) throws SQLException {
        data = getSpecificTableDataWithCondition(tableName, columns, condition);
        if (data == null) {
            System.err.println("null");
        } else {
            for (String d : data) {
                System.out.println(d);
            }
        }

    }

    public ArrayList<String> getSpecificTableDataWithConditionByOrder(String tableName, String columns, String condition, String Order) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName)) {
                for (Table.Column column : table.getColumnsNames()) {

                    if (column.getColumnName().equals(columns)) {
                        rslt = executeQuery("SELECT " + columns + " FROM " + tableName + " WHERE " + condition + " ORDER BY " + Order);
                        if (rslt.next() == false) {
                            return null;
                        } else {
                            //delete old data
                            column.getColumnsDatas().clear();
                            //add new data
                            do {
                                column.addData(rslt.getString(column.getColumnName()));
                            } while (rslt.next());
                            return column.getColumnsDatas();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void showSpecificTableDataWithConditionByOrder(String tableName, String columns, String condition, String Order) throws SQLException {
        data = getSpecificTableDataWithConditionByOrder(tableName, columns, condition, Order);
        if (data == null) {
            System.err.println("null");
        } else {
            for (String d : data) {
                System.out.println(d);
            }
        }
    }

    public void makeJTable(String tableName, JTable tableOnForm) throws SQLException {
        DefaultTableModel m = (DefaultTableModel) tableOnForm.getModel();

        //delete old columns
        m.setColumnCount(0);

        //set new columns
        DataBase = dataBaseStructure();
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName)) {
                for (Table.Column col : table.getColumnsNames()) {
                    m.addColumn(col.getColumnName());
                }
            }
        }
        
        ////delete old columns data
        m.setRowCount(0);

        column = getAllTableData(tableName);
        if (column != null) {
            //set columns data
            Object[] os = new Object[column.size()];

            for (int i = 0; i < column.get(0).getColumnsDatas().size(); i++) {
                for (int j = 0; j < column.size(); j++) {
                    os[j] = (Object) column.get(j).getColumnsDatas().get(i);
                }
                m.addRow(os);
            }
        }

    }
    public String makeJTree(JTree tree) throws SQLException {
        DataBase = dataBaseStructure();
        String firstTable = DataBase.get(0).getTableName();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(dataBaseName);
        model.setRoot(root);

        ArrayList<DefaultMutableTreeNode> tablesTreeNode = new ArrayList<>();

        for (Table table : DataBase) {
            tablesTreeNode.add(new DefaultMutableTreeNode(table.getTableName()));
        }
        int i = 0;
        for (DefaultMutableTreeNode colTreeNode : tablesTreeNode) {
            for (Table.Column column : DataBase.get(i).getColumnsNames()) {
                colTreeNode.add(new DefaultMutableTreeNode(column.getColumnName()));
            }
            i++;
        }

        for (DefaultMutableTreeNode table : tablesTreeNode) {
            root.add(table);
        }

        model.reload();
        return firstTable;
    }

}
