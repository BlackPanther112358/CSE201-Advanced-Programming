import java.util.Random;

public class Coupon {
    public final double discount;

    public Coupon(double discount) {
        this.discount = discount;
    }
    public Coupon() {
        Random random = new Random();
        this.discount = 0.05 + (0.15 - 0.05)*random.nextDouble();
    }

    public double getDiscount(){
        return this.discount;
    }
    public void display(){
        System.out.println("The Coupon discount is " + this.discount*100 + "%.");
    }
}
