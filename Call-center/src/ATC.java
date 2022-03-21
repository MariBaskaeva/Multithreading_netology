import java.util.concurrent.LinkedBlockingQueue;

public class ATC extends Thread{
    private final LinkedBlockingQueue<String> calls;
    private final int AMOUNT_OF_CALLS = 20;
    private final int SLEEP_TIME = 1000;
    private final int NUM_OF_ITERATION = 3;

    public ATC(LinkedBlockingQueue<String> calls){
        this.calls = calls;
    }

    @Override
    public void run(){
        try {
            for(int j = 0; j < NUM_OF_ITERATION; j++){
                for(int i = 0; i < AMOUNT_OF_CALLS; i++){
                    calls.put("Call №" + (i + 1));
                }
                    Thread.sleep(SLEEP_TIME);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
