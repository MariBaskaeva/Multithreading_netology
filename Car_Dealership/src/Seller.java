import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private final int TIMEOUT = 3000;
    private static int num = 0;
    private Dealership dealership;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public Seller(Dealership dealership){
        this.dealership = dealership;
    }

    public void receiveAuto(){
            lock.lock();
            dealership.getAutos().add(new Auto());
            System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
            condition.signal();
            lock.unlock();
    }
    public Auto sellAuto(){
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " пришел в салон");
            while(dealership.getAutos().size() == 0){
                System.out.println("Машин нет");
                condition.await();
            }
            Thread.sleep(TIMEOUT);
            num++;
            System.out.println(num + ") " + Thread.currentThread().getName() + " уехал на новеньком авто.");
        }catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }finally{
            lock.unlock();
        }
        return dealership.getAutos().remove(0);
    }
}
