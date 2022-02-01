import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private final int TIMEOUT = 1500;
    private static int num = 0;
    private Dealership dealership;

    public Seller(Dealership dealership){
        this.dealership = dealership;
    }

    public synchronized void receiveAuto(){
            try{
                Thread.sleep(TIMEOUT);
                dealership.getAutos().add(new Auto());
                System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
                notify();
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
    }
    public synchronized Auto sellAuto(){
        try{
            System.out.println(Thread.currentThread().getName() + " пришел в салон");
            while(dealership.getAutos().size() == 0){
                System.out.println("Машин нет");
                wait();
            }
            Thread.sleep(TIMEOUT);
            num++;
            System.out.println(num + ") " + Thread.currentThread().getName() + " уехал на новеньком авто.");
        }catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }
        return dealership.getAutos().remove(0);
    }
}
