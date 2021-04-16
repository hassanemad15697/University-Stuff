package DataBaseAPI;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
    private ArrayList< Table.Column> columnsHolder = new ArrayList<>();
    private ArrayList< String> dataHolder = new ArrayList<>();

    public Connection connectToDataBase() throws SQLException {
        conn = DriverManager.getConnection(host, username, password);
        //execute("");
        dataBaseStructure();
        System.out.println("Connected!");
        return conn;
    }

    public void closeDataBaseConnection() throws SQLException {

            if (stmnt != null) {
                stmnt.close();
                System.out.println("Prepared Statement colsed!");
            }
            if (conn != null) {
                conn.close();
                System.out.println("Connection colsed!");
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

    private ArrayList<String> getColumnsNames(String columnsName) {
        ArrayList<String> columnsNames = new ArrayList<>();
        for (String string : new ArrayList<>(Arrays.asList(columnsName.split(",")))) {
            columnsNames.add(string.trim());
        }
        return columnsNames;
    }

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
            if (table.getTableName().equals(tableName.trim())) {

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
        columnsHolder = getAllTableData(tableName.trim());
        if (columnsHolder == null) {
            System.err.println("null");
        } else {
            for (Table.Column col : columnsHolder) {
                System.out.print(col.getColumnName() + "\t\t");
                for (String columnsData : col.getColumnsDatas()) {
                    System.out.print(columnsData + "\t");
                }
                System.out.println("");
            }
        }

    }

    public ArrayList<String> getOneColumnData(String tableName, String columnName) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                for (Table.Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columnName.trim())) {
                        rslt = executeQuery("SELECT " + columnName.trim() + " FROM " + tableName.trim());
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

    public void showOneColumnData(String tableName, String columnName) throws SQLException {
        dataHolder = getOneColumnData(tableName.trim(), columnName.trim());
        if (dataHolder == null) {
            System.err.println("null");
        } else {
            for (String d : dataHolder) {
                System.out.println(d);
            }
        }

    }

    public ArrayList<String> getOneColumnDataWithCondition(String tableName, String columnName, String condition) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                for (Table.Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columnName.trim())) {
                        rslt = executeQuery("SELECT " + columnName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim());
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

    public void showOneColumnDataWithCondition(String tableName, String columnName, String condition) throws SQLException {
        dataHolder = getOneColumnDataWithCondition(tableName.trim(), columnName.trim(), condition.trim());
        if (dataHolder == null) {
            System.err.println("null");
        } else {
            for (String d : dataHolder) {
                System.out.println(d);
            }
        }

    }

    public ArrayList<String> getOneColumnDataWithConditionByOrder(String tableName, String columnName, String condition, String Order) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                for (Table.Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columnName.trim())) {
                        rslt = executeQuery("SELECT " + columnName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim() + " ORDER BY " + Order.trim());
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

    public void showOneColumnDataWithConditionByOrder(String tableName, String columnName, String condition, String Order) throws SQLException {
        dataHolder = getOneColumnDataWithConditionByOrder(tableName.trim(), columnName.trim(), condition.trim(), Order.trim());
        if (dataHolder == null) {
            System.err.println("null");
        } else {
            for (String d : dataHolder) {
                System.out.println(d);
            }
        }

    }

    public ArrayList<Table.Column> getColumnsData(String tableName, String columnsName) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                rslt = executeQuery("SELECT " + columnsName.trim() + " FROM " + tableName.trim());
                if (rslt.next() == false) {
                    return null;
                } else {
                    //clear old data
                    for (Table.Column column : table.getColumnsNames()) {
                        if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                            column.getColumnsDatas().clear();
                        }
                    }
                    do {
                        for (Table.Column column : table.getColumnsNames()) {
                            if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                                column.addData(rslt.getString(column.getColumnName()));
                            }
                        }
                    } while (rslt.next());
                    return table.getColumnsNames();

                }

            }
        }
        return null;
    }

    public void showColumnsData(String tableName, String columnsName) throws SQLException {
        columnsHolder = getColumnsData(tableName.trim(), columnsName.trim());
        if (columnsHolder == null) {
            System.err.println("null");
        } else {
            for (Table.Column col : columnsHolder) {
                if (!col.getColumnsDatas().isEmpty()) {
                    System.out.print(col.getColumnName() + "\t\t");
                    for (String columnsData : col.getColumnsDatas()) {
                        System.out.print(columnsData + "\t");
                    }
                }
                System.out.println("");
            }
        }

    }

    public ArrayList<Table.Column> getColumnsDataWithCondition(String tableName, String columnsName, String condition) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                rslt = executeQuery("SELECT " + columnsName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim());
                if (rslt.next() == false) {
                    return null;
                } else {
                    //clear old data
                    for (Table.Column column : table.getColumnsNames()) {
                        if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                            column.getColumnsDatas().clear();
                        }
                    }
                    do {
                        for (Table.Column column : table.getColumnsNames()) {
                            if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                                column.addData(rslt.getString(column.getColumnName()));
                            }
                        }
                    } while (rslt.next());
                    return table.getColumnsNames();

                }

            }
        }
        return null;
    }

    public void showColumnsDataWithCondition(String tableName, String columnName, String condition) throws SQLException {
        columnsHolder = getColumnsDataWithCondition(tableName.trim(), columnName.trim(), condition.trim());
        if (columnsHolder == null) {
            System.err.println("null");
        } else {
            for (Table.Column col : columnsHolder) {
                if (!col.getColumnsDatas().isEmpty()) {
                    System.out.print(col.getColumnName() + "\t\t");
                    for (String columnsData : col.getColumnsDatas()) {
                        System.out.print(columnsData + "\t");
                    }
                }
                System.out.println("");
            }
        }
    }

    public ArrayList<Table.Column> getColumnsDataWithConditionByOrder(String tableName, String columnsName, String condition, String Order) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                rslt = executeQuery("SELECT " + columnsName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim() + " ORDER BY " + Order.trim());
                if (rslt.next() == false) {
                    return null;
                } else {
                    //clear old data
                    for (Table.Column column : table.getColumnsNames()) {
                        if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                            column.getColumnsDatas().clear();
                        }
                    }
                    do {
                        for (Table.Column column : table.getColumnsNames()) {
                            if (getColumnsNames(columnsName.trim()).contains(column.getColumnName())) {
                                column.addData(rslt.getString(column.getColumnName()));
                            }
                        }
                    } while (rslt.next());
                    return table.getColumnsNames();

                }

            }
        }
        return null;
    }

    public void showColumnsDataWithConditionByOrder(String tableName, String columns, String condition, String Order) throws SQLException {
        columnsHolder = getColumnsDataWithConditionByOrder(tableName.trim(), columns.trim(), condition.trim(), Order.trim());
        if (columnsHolder == null) {
            System.err.println("null");
        } else {
            for (Table.Column col : columnsHolder) {
                if (!col.getColumnsDatas().isEmpty()) {
                    System.out.print(col.getColumnName() + "\t\t");
                    for (String columnsData : col.getColumnsDatas()) {
                        System.out.print(columnsData + "\t");
                    }
                }
                System.out.println("");
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
            if (table.getTableName().equals(tableName.trim())) {
                for (Table.Column col : table.getColumnsNames()) {
                    m.addColumn(col.getColumnName());
                }
            }
        }

        ////delete old columns data
        m.setRowCount(0);

        columnsHolder = getAllTableData(tableName.trim());
        if (columnsHolder != null) {
            //set columns data
            Object[] os = new Object[columnsHolder.size()];

            for (int i = 0; i < columnsHolder.get(0).getColumnsDatas().size(); i++) {
                for (int j = 0; j < columnsHolder.size(); j++) {
                    os[j] = (Object) columnsHolder.get(j).getColumnsDatas().get(i);
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
