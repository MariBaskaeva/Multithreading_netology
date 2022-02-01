import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private final int TIMEOUT = 3000;
    private static int num = 0;
    private final Dealership dealership;
    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    public Seller(Dealership dealership){
        this.dealership = dealership;
    }

    public void receiveAuto(){
        try {
            lock.lock();
            dealership.getAutos().add(new Auto());
            System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
            condition.signal();
        }finally{
            lock.unlock();
        }
    }
    public Auto sellAuto(){
        Auto result = null;
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
            result = dealership.getAutos().remove(0);
        }catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }finally{
            lock.unlock();
        }
        return result;
    }
}
