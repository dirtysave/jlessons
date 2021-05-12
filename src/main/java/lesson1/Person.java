package lesson1;

import lesson1.Moveable;

public class Person {
    private String name;

    public Person(String aName) {
        this.name = aName;
    }

    public void Start(Moveable driveIt) {
        System.out.print(this.name);
        System.out.print(" на ");
        driveIt.Start();
    }

    public void Stop(Moveable stopIt) {
        System.out.print(this.name);
        System.out.print(" на ");
        stopIt.Stop();
    }

}
