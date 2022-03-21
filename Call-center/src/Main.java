import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        final int SLEEP_TIME = 1000;
        final int NUM_OF_SPECIALISTS = 3;
        LinkedBlockingQueue<String> calls = new LinkedBlockingQueue<>();
        ThreadGroup group = new ThreadGroup("Specialists");
        ATC atc = new ATC(calls);

        atc.start();
        for(int i = 0; i < NUM_OF_SPECIALISTS; i++){
            new Thread(group, new Specialist(calls), (i + 1) + " Specialist: ").start();
        }
        while(group.activeCount() > 0){
            try{
                Thread.sleep(SLEEP_TIME);
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
        group.interrupt();
    }
}
