import lesson1.*;

public class MainClass {
    public static void main(String[] args) {
        Car jag = new Car();
        Scate scate = new Scate();
        Motorcycle bike = new Motorcycle();

        Person human = new Person("Иван Иваныч");
        human.Start(jag);
        human.Stop(jag);

        human.Start(scate);
        human.Stop(scate);

        human.Start(bike);
        human.Stop(bike);
    }
}
