package lesson5;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    /* Вообще, по идее, подготовка обьекта к работе тоже может быть длинной. Но вставлять ее вместе с основной логикой работы было как то странно.
            То что, что логика подготовки вызывается в асинхроне, который снаружи этого класса, тоже странно
     */

    public void PrepareForRace() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        race.TrySetWinner(this);
    }
}
