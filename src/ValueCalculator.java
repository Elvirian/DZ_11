import java.util.Arrays;
import java.util.stream.DoubleStream;

public class ValueCalculator implements Runnable {
    private double[] doubleList;
    private final int arraySize = 1000000;
    private final int halfArraySize =arraySize/2;

    void doCalc() throws InterruptedException  {
        long start = System.currentTimeMillis();
        doubleList = DoubleStream.generate(() -> 1).limit(arraySize).toArray();

        double[] doubleListClone1 = new double[halfArraySize];
        double[] doubleListClone2 = new double[halfArraySize];

        System.arraycopy(doubleList,0,doubleListClone1,0,halfArraySize);
        System.arraycopy(doubleList,halfArraySize,doubleListClone2,0,halfArraySize);

        ValueCalculator valueCalculator1 = new ValueCalculator();
        valueCalculator1.doubleList = doubleListClone1;
        ValueCalculator valueCalculator2 = new ValueCalculator();
        valueCalculator2.doubleList = doubleListClone2;

        Thread thread1 = new Thread(valueCalculator1);
        Thread thread2 = new Thread(valueCalculator2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        double[] result = DoubleStream.concat(Arrays.stream(doubleListClone1), Arrays.stream(doubleListClone2)).toArray();


        System.out.println(System.currentTimeMillis() - start + " milliseconds.");
        //System.out.println(Arrays.toString(result));



    }


    @Override
    public void run() {

        for (int i = 0; i < halfArraySize; i++) {
            doubleList[i] = (float) (doubleList[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

}




