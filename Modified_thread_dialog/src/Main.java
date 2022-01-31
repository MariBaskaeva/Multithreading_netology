import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<MyThread> myThreads = new ArrayList<>();

        myThreads.add(new MyThread("First"));
        myThreads.add(new MyThread("Second"));
        myThreads.add(new MyThread("Third"));
        myThreads.add(new MyThread("Fourth"));

        try{
            Integer res = threadPool.invokeAny(myThreads);
            System.out.println("Количество выведенных в консоль сообщений от самого быстрого потока: " + res);
        }
        catch(InterruptedException | ExecutionException ex){
            System.out.println(ex.getMessage());
        }


        System.out.println("\nInvokeAll\n");
        try{
            List<Future<Integer>> list = threadPool.invokeAll(myThreads);
            threadPool.shutdown();
            for(Future<Integer> num : list){
                System.out.println("Количество выведенных в консоль сообщений: " + num.get());
            }
        }
        catch(InterruptedException  ex) {
            System.out.println(ex.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
