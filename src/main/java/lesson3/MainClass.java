package lesson3;

import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        Integer[] testArray = new Integer[] { 1, 2, 4, 5};

        System.out.println(Arrays.toString(testArray));
        Swap(testArray, 0, 3);
        System.out.println(Arrays.toString(testArray));

        ArrayList<Integer> integerList = ToArrayList(testArray);

        System.out.println(Arrays.toString(integerList.toArray()));

        // Задание 3
        Box<Orange> orangeBox = new Box<Orange>();
        for(int i=0; i<5; i++)
            orangeBox.Add(new Orange());

        System.out.println(orangeBox.toString());

        Box<Apple> appleBox = new Box<Apple>();
        for(int j=0; j<3; j++)
            appleBox.Add(new Apple());

        System.out.println(appleBox.toString());

        System.out.println(String.format("Orange box weight = %f, Apple box weight = %f",
                orangeBox.getWeight(),
                appleBox.getWeight()));

        System.out.println(String.format("Compare OrangeBox with AppleBox = %s", orangeBox.Compare(appleBox)));

        Box<Apple> appleBox2 = new Box<Apple>();
        appleBox2.Add(new Apple());

        System.out.println(appleBox2.toString());

        appleBox.Move(appleBox2);

        System.out.println(appleBox2.toString());

        // Задание 4
        String phrase = "Sample simple simple words of Sample worlds";
        String[] words = phrase.split(" ");
        Set<String> uniqueWords = new HashSet();
        uniqueWords.addAll(Arrays.asList(words));

        System.out.println(Arrays.toString(uniqueWords.toArray()));
    }

    // Задание 1
    public static void Swap(Object[] array, int item1, int item2) {
        Object dummyItem = array[item1];
        array[item1] = array[item2];
        array[item2] = dummyItem;
    }

    // Задание 2
    public static <T> ArrayList<T> ToArrayList(T[] array) {
        return new ArrayList<T>(Arrays.asList(array));
    }

}
