import java.util.Random;

public class PrimeUser extends RegisteredUser{
    private final String password;
    public PrimeUser(String username, String password) {
        super(username);
        this.password = password;
        this.type = "Prime";
    }
    public boolean authorize(String password){
        return password.equals(this.password);
    }
    @Override
    public void generateCoupons() {
        Random random = new Random();
        int cnt = random.nextInt(2);
        for(int i = 0; i < cnt; i++){
            Coupon newCoupon = new Coupon();
            coupons.add(newCoupon);
        }
    }
    @Override
    public double calculateDeliveryCharges(double amount) {
        return 100 + 0.02*amount;
    }
    @Override
    public int calculateDeliveryTime() {
        Random random = new Random();
        return 3 + random.nextInt(4);
    }
    @Override
    public boolean upgradeStatus(String newStatus, Flipzon flipzon) {
        if(newStatus.equals("Elite")){
            if(this.balance < 300){
                System.out.println("Insufficient balance");
                return false;
            }
            EliteUser newCustomer = new EliteUser(this.name, this.password);
            newCustomer.balance = this.balance - 300;
            newCustomer.productCart = this.productCart;
            newCustomer.dealCart = this.dealCart;
            newCustomer.coupons = this.coupons;
            flipzon.users.remove(this);
            flipzon.users.add(newCustomer);
            System.out.println("The Status has been upgraded to Elite.");
            return true;
        }else{
            System.out.println("Cannot upgrade.");
            return false;
        }
    }
}
