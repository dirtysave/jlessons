package lesson3;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit>{
    private ArrayList<T> objectsInBox = new ArrayList<T>();

    public void Add(T item) {
        if (!objectsInBox.contains(item))
            objectsInBox.add(item);
    }

    public float getWeight() {
        float f = 0f;
        int boxSize = objectsInBox.size();
        if ( boxSize == 0)
            return 0f;
        f = objectsInBox.get(0).getWeight();
        return f*boxSize;   // Здесь возможна потеря точности, если масса будет велика. Лучше использовать double.
    }

    public boolean Compare(Box<? extends Fruit> box) {
        if (box == this)
            return true;

        float thisWeight = getWeight();
        float comparedWeight = box.getWeight();
        return thisWeight == comparedWeight;
    }

    public void Move(Box<T> boxTarget) {
        if (objectsInBox.size() == 0)
            return;

        for(int i=0; i<objectsInBox.size(); i++) {
            T currentItem = objectsInBox.get(i);
            boxTarget.Add(currentItem);
        }
        objectsInBox.clear();
    }

    @Override
    public String toString() {
        return Arrays.toString(objectsInBox.toArray());
    }
}
