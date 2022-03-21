public class User implements Runnable {
    private final int SLEEP_TIME = 2000;
    private final int OPENS_LIMIT = 10;

    @Override
    public void run() {
        try {
            for (int i = 0; i < OPENS_LIMIT; i++) {
                if (!Tumbler.tumbler) {
                    System.out.println("Пользователь включил тумблер.");
                    Tumbler.tumbler = true;
                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}