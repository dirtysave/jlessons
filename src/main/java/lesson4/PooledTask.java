package lesson4;

public class PooledTask implements Runnable {

    private int counter;
    private float[] floatArray;

    public PooledTask(float[] floatArray, int counter) {
        this.floatArray = floatArray;
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=counter; i< counter + MainClass.CHUNK_SIZE; i++) {
            floatArray[i] = (float)(floatArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
