package lesson2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* РЕЗУЛЬТАТЫ ТЕСТОВ -
Время доступа к серединному элементу массива
              10 1000  100000  10000000
   ArrayList  2    1       1         1
  LinkedList  2   11    2674      <не дождался>
Время удаления половины массива
              10 1000  100000  10000000
   ArrayList  0    1    1061      <не дождался>
  LinkedList  1    0       7        91
 */


public class TestsLesson2 {
    public static void main(String[] args) {
        ArrayList<Integer> array10 = new ArrayList<>(10);
        ArrayList<Integer> array1000 = new ArrayList<>(1000);
        ArrayList<Integer> array100000 = new ArrayList<>(100000);
        ArrayList<Integer> array10000000 = new ArrayList<>(10000000);

        LinkedList<Integer> linked10 = new LinkedList<Integer>();
        LinkedList<Integer> linked1000 = new LinkedList<Integer>();
        LinkedList<Integer> linked10000 = new LinkedList<Integer>();
        LinkedList<Integer> linked10000000 = new LinkedList<Integer>();

        fillSomeValues(array10, linked10, 10);
        fillSomeValues(array1000, linked1000,1000);
        fillSomeValues(array100000, linked10000,100000);
        fillSomeValues(array10000000, linked10000000,10000000);

        System.out.println("Время доступа к серединному элементу массива");
        System.out.println(String.format("%12s %3s %4s %7s %9s", " ", "10", "1000", "100000", "10000000"));

        System.out.print(String.format("%12s", "ArrayList"));

        calculateExecuteTime(array10, null, 3);
        calculateExecuteTime(array1000, null, 4);
        calculateExecuteTime(array100000, null, 7);
        calculateExecuteTime(array10000000, null, 9);

        System.out.println();

        System.out.print(String.format("%12s", "LinkedList"));

        calculateExecuteTime(null, linked10, 3);
        calculateExecuteTime(null, linked1000, 4);
        calculateExecuteTime(null, linked10000, 7);
        //calculateExecuteTime(null, linked10000000, 9);

        System.out.println();

        System.out.println("Время удаления половины массива");
        System.out.println(String.format("%12s %3s %4s %7s %9s", " ", "10", "1000", "100000", "10000000"));

        System.out.print(String.format("%12s", "ArrayList"));

        calculateDeleteTime(array10, null, 3);
        calculateDeleteTime(array1000, null, 4);
        calculateDeleteTime(array100000, null, 7);
        //calculateDeleteTime(array10000000, null, 9);

        System.out.println();

        System.out.print(String.format("%12s", "LinkedList"));

        calculateDeleteTime(null, linked10, 3);
        calculateDeleteTime(null, linked1000, 4);
        calculateDeleteTime(null, linked10000, 7);
        calculateDeleteTime(null, linked10000000, 9);

        System.out.println();
    }

    public static void fillSomeValues(ArrayList<Integer> arrayToTest, LinkedList<Integer> linkedList, int size) {
        // Блажь, конечно, но значение лучше выбирать из диапазона int.minValue до int.maxValue, потому как
        //  либо 32 бит, либо 64 - диааазон разный, платформы тоже, а называется одинаково - целочисленное.
        for(int i=0; i<size; i++) {
            int rndValue = (int) Math.round(Math.random() * 32768);
            arrayToTest.add(rndValue);
            linkedList.add(rndValue);
        }
    }

    public static void calculateDeleteTime(ArrayList<Integer> arrayToTest, LinkedList<Integer> linkedToTest, int numberFormatter) {

        int itemPosition = 0;

        if (arrayToTest != null) {
            itemPosition = arrayToTest.size() / 2;
        } else if (linkedToTest != null) {
            itemPosition = linkedToTest.size() / 2;
        }

        long time = System.currentTimeMillis();

        if (arrayToTest != null) {
            for(int i=0; i< itemPosition && arrayToTest.size() > 0; i++) {
                arrayToTest.remove(0);
            }
        } else if (linkedToTest != null) {
            for(int i=0; i< itemPosition && linkedToTest.size() > 0; i++) {
                linkedToTest.remove(0);
            }
        }

        long timeEnd = System.currentTimeMillis() - time;

        String formatter = String.format("%%%dd ",numberFormatter);
        System.out.print(
                String.format(formatter, timeEnd));
    }

    public static  void calculateExecuteTime(ArrayList<Integer> arrayToTest, LinkedList<Integer> linkedToTest, int numberFormatter) {

        int itemPosition = 0;

        if (arrayToTest != null) {
            itemPosition = arrayToTest.size() / 2;
        } else {
            itemPosition = linkedToTest.size() / 2;
        }

        long time = System.currentTimeMillis();

        if (arrayToTest != null) {
            for (int i = 0; i < 10000; i++) {
                int dummyItem = arrayToTest.get(itemPosition);
            }
        } else if (linkedToTest != null) {
            for (int i = 0; i < 10000; i++) {
                int dummyItem = linkedToTest.get(itemPosition);
            }
        }

        long timeEnd = System.currentTimeMillis() - time;

        String formatter = String.format("%%%dd ",numberFormatter);
        System.out.print(
                String.format(formatter, timeEnd));
    }

}
