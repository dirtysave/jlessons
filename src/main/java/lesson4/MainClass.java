package lesson4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainClass {

    static final int SIZE = 100_000_000;
    static final int CHUNK_SIZE = MainClass.SIZE / 100;

    private float[] inputArray = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {
        MainClass testClass = new MainClass();

        testClass.SetArrayItems();

        testClass.Calcuate1Phase();

        testClass.SetArrayItems();

        testClass.Calculate2Phase(testClass.inputArray, 10);
    }

    public void SetArrayItems() {
        System.out.println("Начало подготовки массива к работе (установка элементов в 1)...");
        for(int i=0; i<SIZE; i++) {
            inputArray[i] = 1.0f;
        }
        System.out.println("Массив подготовлен");
    }

    public void Calcuate1Phase() throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Начало обработки массива, процедура 1");
                long time = System.currentTimeMillis();

                for(int i=0; i< MainClass.SIZE; i++) {
                    inputArray[i] = (float)(inputArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }

                long timeEnd = System.currentTimeMillis() - time;
                System.out.println(String.format("Время выполнения %d сек", timeEnd / 1000));
            }
        });

        try {
            thread1.start();
            thread1.join();
        }
        catch(InterruptedException interruptedException) {
            // В тесте не надо ничего делать, хотя за пустой CATCH обычно бьют по голове канделябром
            interruptedException.printStackTrace();
        }
    }

    public void Calculate2Phase(float[] inputArray, int taskCount)  throws InterruptedException  {

        try {
            System.out.println("Начало обработки массива, процедура 2");
            long time = System.currentTimeMillis();

            ExecutorService service = Executors.newFixedThreadPool(taskCount);
            for (int i = 0; i < MainClass.SIZE; i += MainClass.CHUNK_SIZE) {
                service.execute(new PooledTask(inputArray, i));
            }
            service.shutdown();
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            long timeEnd = System.currentTimeMillis() - time;
            System.out.println(String.format("Время выполнения %d сек", timeEnd / 1000));
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
