import java.util.Random;

public class NormalUser extends RegisteredUser{
    private final String password;
    public NormalUser(String username, String password) {
        super(username);
        this.password = password;
        this.type = "Normal";
    }
    public boolean authorize(String password){
        return password.equals(this.password);
    }
    @Override
    public void generateCoupons() {
    }
    @Override
    public double calculateDeliveryCharges(double amount) {
        return 100 + 0.05*amount;
    }
    @Override
    public int calculateDeliveryTime() {
        Random random = new Random();
        return 7 + random.nextInt(4);
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
        }else if(newStatus.equals("Prime")){
            if(this.balance < 200){
                System.out.println("Insufficient balance");
                return false;
            }
            PrimeUser newCustomer = new PrimeUser(this.name, this.password);
            newCustomer.balance = this.balance - 200;
            newCustomer.productCart = this.productCart;
            newCustomer.dealCart = this.dealCart;
            newCustomer.coupons = this.coupons;
            flipzon.users.remove(this);
            flipzon.users.add(newCustomer);
            System.out.println("The Status has been upgraded to Prime.");
            return true;
        }else{
            System.out.println("Invalid Customer Type");
            return false;
        }
    }

}
