package lesson7;

@AppTable(name="example")
public class ExampleTable {

    @AppColumn(isPrimaryKey = true)
    public int id;

    @AppColumn()
    public String name;

    public ExampleTable() {}

    public ExampleTable(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
