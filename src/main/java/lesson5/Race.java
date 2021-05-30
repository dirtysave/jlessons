package lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Race {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }

    private ArrayList<Car> racers;

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public void SetRacers(Car[] racers) {
        this.racers = new ArrayList<>(Arrays.asList(racers));
    }

    public void Prepare() {
        // Здесь подготовка к соревнованию
        final CountDownLatch latch = new CountDownLatch(racers.size());

        try {
            for (int i = 0; i < racers.size(); i++) {
                final int carNumber = i;
                new Thread(() -> {
                    racers.get(carNumber).PrepareForRace();
                    latch.countDown();
                }).start();
            }
            latch.await();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void Start() throws InterruptedException {
        Thread[] threads = new Thread[racers.size()];

        for (int i = 0; i < racers.size(); i++) {
            final int carNumber = i;
            Thread racer = new Thread(racers.get(carNumber));
            threads[carNumber] = racer;
            racer.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }
    }

    private Car winner = null;
    final private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void TrySetWinner(Car racer) {
        System.out.println(String.format("%s закончил гонку", racer.getName()));

        if (winner == null) {
            try {
                lock.writeLock().lock();
                winner = racer;
                System.out.println(String.format("%s выиграл гонку", racer.getName()));
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}