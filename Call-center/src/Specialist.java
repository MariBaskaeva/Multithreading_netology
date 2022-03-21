import java.util.concurrent.LinkedBlockingQueue;

public class Specialist extends Thread{
    private final int WORK_TIME = 3000;
    private final LinkedBlockingQueue<String> calls;

    public Specialist(LinkedBlockingQueue<String> calls){
        this.calls = calls;
    }

    @Override
    public void run(){
        try {
            while(!calls.isEmpty()){
                System.out.println(currentThread().getName() + " started working with " + calls.take());

                    Thread.sleep(WORK_TIME);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
