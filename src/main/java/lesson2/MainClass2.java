package lesson2;

public class MainClass2 {
    public static void main(String[] args) {
        String strArray[][] =
                {
                        {"0", "1", "2", "3"},               // 6
                        {"10", "11", "12", "13"},           // 46
                        {"100", "101", "102", "103"},       // 406
                        {"1000", "1001", "1002", "1003"}    // 4006
                };  // ИТОГО 4464

        try {
            System.out.println(String.format("Сумма элементов = %d", translateStringArrayToSum(strArray)));
        }
        catch(MyArraySizeException e) {
            System.out.println("Размерность массива не совпадает с 4x4");
        }
        catch(MyArrayDataException e) {
            System.out.println(String.format("Ошибка преобразования данных в строке %d, столбце %d", e.getRow(), e.getColumn()));
        }
    }

    private static int translateStringArrayToSum(String[][] inputArray) {
        if (inputArray.length != 4) {
            throw new MyArraySizeException();
        } else {
            for (String[] arrayline: inputArray) {
                if (arrayline.length != 4)
                    throw new MyArraySizeException();
            }
        }

        int result = 0;

        for(int row = 0; row < 4; row++)
            for(int col = 0; col < 4; col++) {
                try {
                    result += Integer.parseInt(inputArray[row][col]);
                }
                catch(NumberFormatException e) {
                    MyArrayDataException dataException = new MyArrayDataException(row, col);
                    throw dataException;
                }
            }

        return result;
    }
}
