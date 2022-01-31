public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread firstThread = new MyThread();
        MyThread secondThread = new MyThread();
        MyThread thirdThread = new MyThread();
        MyThread fourthThread = new MyThread();

        firstThread.setName("First thread");
        secondThread.setName("Second thread");
        thirdThread.setName("Third thread");
        fourthThread.setName("Fourth thread");

        ThreadGroup group = new ThreadGroup("Group");
        Thread thread1 = new Thread(group, firstThread);
        Thread thread2 = new Thread(group, secondThread);
        Thread thread3 = new Thread(group, thirdThread);
        Thread thread4 = new Thread(group, fourthThread);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        Thread.sleep(15000);

        group.interrupt();
    }
}
