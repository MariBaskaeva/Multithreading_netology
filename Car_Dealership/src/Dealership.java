import java.util.ArrayList;
import java.util.List;

public class Dealership {
    final int TIMEOUT = 3000;
    final Seller seller = new Seller(this);
    List<Auto> autos = new ArrayList<>(10);

    public Auto sellAuto(){
        return seller.sellAuto();
    }

    public void receiveAuto(){
        seller.receiveAuto();
    }

    public List<Auto> getAutos(){
        return autos;
    }
}
