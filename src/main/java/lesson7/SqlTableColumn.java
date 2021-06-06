package lesson7;

public class SqlTableColumn {
    public String sqlName;
    public String sqlType;
    public boolean isPrimary;

    public SqlTableColumn() {}

    public SqlTableColumn(String sqlName, String sqlType, boolean isPrimary) {
        this.sqlName = sqlName;
        this.sqlType = sqlType
                .replace("java.lang.String", "text")
                .replace(int.class.getName(), "integer");
        this.isPrimary = isPrimary;
    }
}
