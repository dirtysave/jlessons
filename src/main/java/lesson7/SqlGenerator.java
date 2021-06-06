package lesson7;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class  SqlGenerator<T extends Object> {
    private Class pocoClass;

    private String pocoSqlCreate;
    private String pocoSqlUpdate;
    private String pocoSqlInsert;
    private String dbUrl;

    private String tableName;
    private ArrayList<SqlTableColumn> columns = new ArrayList<>();

    public SqlGenerator(Class poco, String db) throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new org.sqlite.JDBC());
        this.pocoClass = poco;
        this.dbUrl = "jdbc:sqlite:" + db;
        generateSql();
    }


    public void createTable() throws ClassNotFoundException {
        if (pocoSqlCreate != null) {
            ExecuteSql("DROP TABLE IF EXISTS " + tableName);
            ExecuteSql(pocoSqlCreate);
        } else {
            // Генерируем исключение
            throw new ClassNotFoundException();
        }
    }

    public void store(T userClass) throws IllegalAccessException {
        // Сначала определим, есть ли значение в поле первичного ключа
        var primary = getPrimaryValue(userClass);

        Connection conn = null;
        PreparedStatement prep = null;

        try {
            conn = DriverManager.getConnection( dbUrl);

            boolean usePrimary = primary != null && ((int)primary) != 0;
            if (usePrimary) {
                // update
                prep = conn.prepareStatement(pocoSqlUpdate);
            } else {
                // insert
                prep = conn.prepareStatement(pocoSqlInsert);
            }

            int preparedNumber = 1;
            Field[] fields = pocoClass.getDeclaredFields();
            for (Field f : fields) {
                AppColumn columnAnnotation = f.getAnnotation(AppColumn.class);
                if (columnAnnotation != null && !columnAnnotation.isPrimaryKey()) {
                    prep.setObject(preparedNumber, f.get(userClass));
                    preparedNumber++;
                }
            }

            if (usePrimary) {
                prep.setObject(preparedNumber, primary);
            }

            prep.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Object getPrimaryValue(T userClass) {
        Object result = null;

        Field[] fields = pocoClass.getDeclaredFields();
        for (Field f : fields) {
            AppColumn columnAnnotation = f.getAnnotation(AppColumn.class);
            if (columnAnnotation != null && columnAnnotation.isPrimaryKey())  {
                try {
                    result = f.get(userClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void generateSql() throws ClassNotFoundException {
        // Если класс/и/или поля не размечен(ы), будем вываливать исключения
        Annotation[] anns = pocoClass.getAnnotations();
        AppTable annotationTable = null;
        for(Annotation ann : anns) {
            if (ann.annotationType().getName().equals("lesson7.AppTable")) {
                annotationTable = (AppTable)ann;
            }
        }

        if (annotationTable != null) {
            tableName = annotationTable.name();

            // Для начала определим все помеченнные столбцы
            Field[] fields = pocoClass.getDeclaredFields();
            for (Field f : fields) {
                AppColumn columnAnnotation = f.getAnnotation(AppColumn.class);
                if (columnAnnotation != null) {
                    if (f.getType().getName().equals("int") || f.getType().getName().equals("java.lang.String")) {
                        columns.add(new SqlTableColumn(f.getName(), f.getType().getName(), columnAnnotation.isPrimaryKey()));
                    }
                }
            }
            if (columns.size() == 0) {
                throw new ClassNotFoundException();
            }
        } else {
            throw new ClassNotFoundException();
        }

        generateSqlTexts();
    }

    private void generateSqlTexts() {
        StringBuilder sbCreate = new StringBuilder();
        StringBuilder sbUpdate = new StringBuilder();
        StringBuilder sbInsert = new StringBuilder();
        StringBuilder sbInsertColumns = new StringBuilder();

        sbCreate.append("create table ").append(tableName).append(" (");
        sbUpdate.append("update ").append(tableName).append(" set ");
        sbInsert.append("insert into ").append(tableName).append(" (");

        String updateWhere = null;

        for (int i=0; i< columns.size(); i++) {
            SqlTableColumn col = columns.get(i);

            sbCreate.append(col.sqlName + " " + col.sqlType);
            if (col.isPrimary) {
                sbCreate.append(" primary key");
                updateWhere = " WHERE " + col.sqlName+"=?";
            } else {
                sbUpdate.append(col.sqlName).append("=?,");
                sbInsert.append(col.sqlName).append(",");
                sbInsertColumns.append("?,");
            }

            if (i == columns.size()-1) {
                sbCreate.append(")");
                sbUpdate.setLength(sbUpdate.length()-1);
                sbUpdate.append(updateWhere );
                sbInsertColumns.setLength(sbInsertColumns.length()-1);
                sbInsert.setLength(sbInsert.length()-1);
                sbInsert
                        .append(") VALUES (")
                        .append(sbInsertColumns.toString())
                        .append(")");

            } else {
                sbCreate.append(",");
            }
        }

        pocoSqlCreate = sbCreate.toString();
        pocoSqlUpdate = sbUpdate.toString();
        pocoSqlInsert = sbInsert.toString();
    }

    private void ExecuteSql(String sql) {
        Connection conn = null;
        PreparedStatement prepStmt = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
            prepStmt = conn.prepareStatement(sql);
            prepStmt.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
