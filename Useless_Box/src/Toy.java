public class Toy implements Runnable {

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (Tumbler.tumbler) {
                Tumbler.tumbler = false;
                System.out.println("Злой котик выключил тумблер лапкой.\n");
            }
        }
    }
}
