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
    private ArrayList<Table> tablesHolder = new ArrayList<>();
    private ArrayList<Column> columnsHolder = new ArrayList<>();
    private ArrayList<String> dataHolder = new ArrayList<>();

    public Connection connectToDataBase() throws SQLException {
        conn = DriverManager.getConnection(host, username, password);
        getDataBaseStructure();
        System.out.println("Connected!");
        return conn;
    }

    public void closeDataBaseConnection() throws SQLException {
        getDataBaseStructure();
        if (rslt != null) {
            rslt.close();
            System.out.println("Result Set colsed!");
        }
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

    private ArrayList<String> getSeparatedNames(String Names) {
        ArrayList<String> columnsNames = new ArrayList<>();
        for (String string : new ArrayList<>(Arrays.asList(Names.split(",")))) {
            columnsNames.add(string.trim());
        }
        return columnsNames;
    }

    private Table getCustomTableStructure(String tablesNames, String columnsNames, String customTableName) {
        Table customTable = new Table(customTableName);
        int tablesCounter = 0, columnsCounter = 0;
        for (Table table : DataBase) {
            if (getSeparatedNames(tablesNames.trim()).contains(table.getTableName())) {
                tablesCounter++;
                //System.err.println("T : " + tablesCounter + "\t" + table.getTableName());
                for (Column columnName : table.getColumnsNames()) {
                    if (getSeparatedNames(columnsNames.trim()).contains(columnName.getColumnName())) {
                        columnsCounter++;
                        customTable.addColumn(columnName.getColumnName(), columnName.getColumnDataType());
                        //System.err.println("C : " + columnsCounter + "\t" + columnName.getColumnName());
                    }
                }
            }
        }

        if (getSeparatedNames(tablesNames.trim()).size() == tablesCounter && getSeparatedNames(columnsNames.trim()).size() == columnsCounter) {
            return customTable;
        }

        return null;
    }

    public void createTable(String tableName, String columnsValues) throws SQLException {
        execute("CREATE TABLE " + tableName + "( " + columnsValues + " )");
        System.out.println("Table Created!!");
        getDataBaseStructure();
    }

    public void renameTable(String oldTableName, String newTableName) throws SQLException {
        execute("ALTER TABLE " + oldTableName + " RENAME TO " + newTableName);
        System.out.println("Table name updated!!");
        getDataBaseStructure();
    }

    public void deleteTable(String tableName) throws SQLException {
        execute("DROP TABLE " + tableName);
        System.out.println("Table deleted!!");
        getDataBaseStructure();
    }

    public void renameColumn(String tableName, String oldColumnName, String newColumnNameWithDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " CHANGE " + oldColumnName + " " + newColumnNameWithDataType);
        System.out.println("Column name updated!!");
        getDataBaseStructure();
    }

    public void modifyColumn(String tableName, String ColumnName, String newColumnDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " MODIFY " + ColumnName + " " + newColumnDataType);
        System.out.println("Column data types updated!!");
        getDataBaseStructure();
    }

    public void addOneColumn(String tableName, String NewColumnName, String newColumnDataType) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD " + NewColumnName + " " + newColumnDataType);
        System.out.println("Column " + NewColumnName + " added!!");
        getDataBaseStructure();
    }

    public void addMultipleColumns(String tableName, String NewColumnsNames) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD " + "( " + NewColumnsNames + " )");
        System.out.println("Columns  added!!");
        getDataBaseStructure();
    }

    public void addPrimaryKey(String tableName, String constraintName, String ColumnNames) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD PRIMARY KEY ( " + ColumnNames + " )");
        System.out.println("primary key  added!!");
        getDataBaseStructure();
    }

    public void addForeignKey(String tableName, String columnName, String foreignTableName, String foreignColumnName) throws SQLException {
        execute("ALTER TABLE " + tableName + " ADD FOREIGN KEY ( " + columnName + " ) REFERENCES " + foreignTableName + "( " + foreignColumnName + " )");
        System.out.println("foreign key  added!!");
        getDataBaseStructure();
    }

    public void DeleteColumn(String tableName, String ColumnName) throws SQLException {
        execute("ALTER TABLE " + tableName + " DROP " + ColumnName);
        System.out.println("Column " + ColumnName + " deleted!!");
        getDataBaseStructure();
    }

    public void insertDataIntoSpecficColumns(String tableName, String columnsNames, String columnsValues) throws SQLException {
        execute("INSERT INTO " + tableName + " ( " + columnsNames + " ) " + " VALUES " + " ( " + columnsValues + " ) ");
        System.out.println("Row Inserted!!");
        getDataBaseStructure();
    }

    public void insertDataIntoAllColumns(String tableName, String columnsValues) throws SQLException {
        execute("INSERT INTO " + tableName + " VALUES " + " ( " + columnsValues + " ) ");
        System.out.println("Row Inserted!!");
        getDataBaseStructure();
    }

    public void updateDataIntoSpecficColumns(String tableName, String columnNameWithUpdatedData, String condition) throws SQLException {
        execute("UPDATE " + tableName + " SET " + columnNameWithUpdatedData + " WHERE " + condition);
        System.out.println("Data Updated!!");
        getDataBaseStructure();
    }

    public void deleteRow(String tableName, String condition) throws SQLException {
        execute("DELETE FROM " + tableName + " WHERE " + condition);
        System.out.println("Row Data DELETED!!");
        getDataBaseStructure();
    }

    public ArrayList<Table> getDataBaseStructure() throws SQLException {
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

        for (Table table : getDataBaseStructure()) {
            System.out.print(table.getTableName() + "\t:");
            for (Column columns : table.getColumnsNames()) {
                System.out.print(columns.getColumnName() + "(" + columns.getColumnDataType() + ")" + "\t");
            }
            System.out.println();
        }
    }

    public Table getAllTableData(String tableName) throws SQLException {

        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {

                rslt = executeQuery("SELECT * FROM " + table.getTableName());
                if (rslt.next() == false) {
                    return null;
                } else {

                    //clear old data
                    for (Column column : table.getColumnsNames()) {
                        column.getColumnsDatas().clear();
                    }
                    //adding new data
                    do {
                        for (Column column : table.getColumnsNames()) {
                            column.addData(rslt.getString(column.getColumnName()));
                        }
                    } while (rslt.next());
                    return table;
                }
            }
        }
        return null;
    }

    public void showAllTableData(String tableName) throws SQLException {
        Table table = getAllTableData(tableName.trim());
        if (table == null) {
            System.err.println("null");
        } else {
            System.out.println("________________________\nTable : " + table.getTableName());
            for (Column col : table.getColumnsNames()) {
                System.out.println("________________________\nColumn : " + col.getColumnName() + "\nData : ");
                for (String columnsData : col.getColumnsDatas()) {
                    System.out.print(columnsData + " , ");
                }
                System.out.println("");
            }
        }

    }

    private Column getOneColumn(String tableName, String columnName, String Query) throws SQLException {
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                for (Column column : table.getColumnsNames()) {
                    if (column.getColumnName().equals(columnName.trim())) {
                        rslt = executeQuery(Query);
                        if (rslt.next() == false) {
                            return null;
                        } else {
                            //delete old data
                            column.getColumnsDatas().clear();
                            //add new data
                            do {
                                column.addData(rslt.getString(column.getColumnName()));
                            } while (rslt.next());
                            return column;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void showOneColumn(Column column) throws SQLException {
        if (column == null) {
            System.err.println("null");
        } else {
            System.out.println("________________________\nColumn : " + column.getColumnName() + "\nData : ");
            for (String data : column.getColumnsDatas()) {
                System.out.println(data);
            }
            System.out.println("________________________\n");
        }
    }

    public Column getOneColumnData(String tableName, String columnName) throws SQLException {
        return getOneColumn(tableName, columnName, "SELECT " + columnName.trim() + " FROM " + tableName.trim());
    }

    public void showOneColumnData(String tableName, String columnName) throws SQLException {
        Column column = getOneColumnData(tableName.trim(), columnName.trim());
        showOneColumn(column);

    }

    public Column getOneColumnDataWithCondition(String tableName, String columnName, String condition) throws SQLException {
        return getOneColumn(tableName, columnName, "SELECT " + columnName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim());
    }

    public void showOneColumnDataWithCondition(String tableName, String columnName, String condition) throws SQLException {
        Column column = getOneColumnDataWithCondition(tableName.trim(), columnName.trim(), condition.trim());
        showOneColumn(column);
    }

    public Column getOneColumnDataWithConditionByOrder(String tableName, String columnName, String condition, String Order) throws SQLException {
        return getOneColumn(tableName, columnName, "SELECT " + columnName.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim() + " ORDER BY " + Order.trim());
    }

    public void showOneColumnDataWithConditionByOrder(String tableName, String columnName, String condition, String Order) throws SQLException {
        Column column = getOneColumnDataWithConditionByOrder(tableName.trim(), columnName.trim(), condition.trim(), Order.trim());
        showOneColumn(column);

    }

    private Table getColumns(String tableName, String columnsNames, String Query) throws SQLException {
        Table customTable = getCustomTableStructure(tableName, columnsNames, tableName);;
        if (customTable != null) {
            rslt = executeQuery(Query);
            if (rslt.next() == false) {
                return null;
            } else {
                do {
                    for (Column column : customTable.getColumnsNames()) {
                        column.addData(rslt.getString(column.getColumnName()));
                    }
                } while (rslt.next());
                return customTable;
            }

        }

        return null;
    }

    private void showColumns(Table customTable) {
        if (customTable == null) {
            System.err.println("null");
        } else {
            System.out.println("________________________\nTable : " + customTable.getTableName());
            for (Column col : customTable.getColumnsNames()) {
                System.out.println("________________________\nColumn : " + col.getColumnName() + "\nData : ");
                if (col.getColumnsDatas() != null) {
                    for (String columnsData : col.getColumnsDatas()) {
                        System.out.print(columnsData + " , ");
                    }
                }
                System.out.println("");
            }
            System.out.println("________________________");
        }
    }

    public Table getColumnsData(String tableName, String columnsNames) throws SQLException {
        return getColumns(tableName, columnsNames, "SELECT " + columnsNames.trim() + " FROM " + tableName.trim());

    }

    public void showColumnsData(String tableName, String columnsNames) throws SQLException {
        Table customTable = getColumnsData(tableName.trim(), columnsNames.trim());
        showColumns(customTable);

    }

    public Table getColumnsDataWithCondition(String tableName, String columnsNames, String condition) throws SQLException {
        return getColumns(tableName, columnsNames, "SELECT " + columnsNames.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim());
    }

    public void showColumnsDataWithCondition(String tableName, String columnNames, String condition) throws SQLException {
        Table customTable = getColumnsDataWithCondition(tableName.trim(), columnNames.trim(), condition.trim());
        showColumns(customTable);
    }

    public Table getColumnsDataWithConditionByOrder(String tableName, String columnsNames, String condition, String Order) throws SQLException {
        return getColumns(tableName, columnsNames, "SELECT " + columnsNames.trim() + " FROM " + tableName.trim() + " WHERE " + condition.trim() + " ORDER BY " + Order.trim());
    }

    public void showColumnsDataWithConditionByOrder(String tableName, String columnNames, String condition, String Order) throws SQLException {
        Table customTable = getColumnsDataWithConditionByOrder(tableName.trim(), columnNames.trim(), condition.trim(), Order.trim());
        showColumns(customTable);
    }

    public Table getColumnsDataWithJoin(String tablesNames, String columnsNames, String condition) throws SQLException {
        return getColumns(tablesNames, columnsNames, "SELECT " + columnsNames.trim() + " FROM " + tablesNames.trim() + " WHERE " + condition.trim());
    }

    public void showColumnsDataWithJoin(String tablesNames, String columnsNames, String condition) throws SQLException {
        Table customTable = getColumnsDataWithJoin(tablesNames.trim(), columnsNames.trim(), condition.trim());
        showColumns(customTable);
    }

    public void makeJTable(String tableName, JTable tableOnForm) throws SQLException {
        DefaultTableModel m = (DefaultTableModel) tableOnForm.getModel();
        //delete old columns
        m.setColumnCount(0);
        //set new columns
        DataBase = getDataBaseStructure();
        for (Table table : DataBase) {
            if (table.getTableName().equals(tableName.trim())) {
                for (Column col : table.getColumnsNames()) {
                    m.addColumn(col.getColumnName());
                }
            }
        }
        ////delete old columns data
        m.setRowCount(0);
        columnsHolder = getAllTableData(tableName.trim()).getColumnsNames();
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
        DataBase = getDataBaseStructure();
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
            for (Column column : DataBase.get(i).getColumnsNames()) {
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
