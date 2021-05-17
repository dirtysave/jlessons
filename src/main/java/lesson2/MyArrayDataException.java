package lesson2;

public class MyArrayDataException extends NumberFormatException {
    private int row;
    private int column;

    public int getRow() {
        return  row;
    }

    public int getColumn() {
        return column;
    }

    public MyArrayDataException(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
