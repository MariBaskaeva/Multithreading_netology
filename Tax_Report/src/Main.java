import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LongAdder revenue = new LongAdder();

        Shop shop1 = new Shop(revenue);
        Shop shop2 = new Shop(revenue);
        Shop shop3 = new Shop(revenue);

        Thread thread1 = new Thread(shop1, "Магазин №1");
        Thread thread2 = new Thread(shop2, "Магазин №2");
        Thread thread3 = new Thread(shop3, "Магазин №3");

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(revenue);
    }
}
