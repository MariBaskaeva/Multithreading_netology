public class Main {
    public static void main(String[] args) {
        final Dealership dealership = new Dealership();
        for(int i = 0; i < 10; i++){
            new Thread(null, dealership::sellAuto, "Покупатель " + (i + 1)).start();
            new Thread(null, dealership::receiveAuto, "Поставщик " + (i + 1)).start();
        }
    }
}