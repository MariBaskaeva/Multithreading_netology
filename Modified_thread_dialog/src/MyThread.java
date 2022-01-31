import java.util.concurrent.Callable;

public class MyThread extends Thread implements Callable<Integer> {
    private final int SLEEP = 2500;
    private final int CURR = 4;

    public MyThread(String name){
        this.setName(name);
    }

    @Override
    public Integer call() {
        int count = 0;
        try {
            while(count < CURR){
                Thread.sleep(SLEEP);
                System.out.println(getName() + ": Всем привет!");
                count++;
            }
        } catch (InterruptedException err) {

        } finally{
            System.out.printf("%s завершен.\n", getName());
        }
        return count;
    }
}