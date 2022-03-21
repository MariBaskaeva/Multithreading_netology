public class Tumbler {
    public static volatile boolean tumbler = false;

    public void start() throws InterruptedException {
        Toy toy = new Toy();
        User user = new User();

        Thread userThread = new Thread(user);
        Thread toyThread = new Thread(toy);

        userThread.start();
        toyThread.start();

        userThread.join();
        toyThread.interrupt();
    }
}