package lesson7;


import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SqlGenerator<ExampleTable> sqlGen = new SqlGenerator<>(ExampleTable.class,"example.db");
        sqlGen.createTable();
        try {
            sqlGen.store(new ExampleTable(0, "Example1"));
            sqlGen.store(new ExampleTable(0, "Example2"));
            sqlGen.store(new ExampleTable(1, "Sample22"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
