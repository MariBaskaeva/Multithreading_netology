import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class Shop implements Runnable{
    LongAdder revenue;
    private Integer[] array = new Integer[10];

    public Shop(LongAdder revenue){
        this.revenue = revenue;
    }

    @Override
    public void run(){
        setArray();
        int result = 0;

        for(int i = 0; i < array.length; i++){
            result += array[i];
        }
        System.out.println(Thread.currentThread().getName() + " получил выручку " + result);
        revenue.add(result);
    }

    public void setArray(){
        Random rand = new Random();

        for(int i = 0; i < 10; i++){
            array[i] = rand.nextInt(100);
        }
    }
}
