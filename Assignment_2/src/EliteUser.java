import java.util.Map;
import java.util.Random;

public class EliteUser extends RegisteredUser{
    private final String password;
    public EliteUser(String username, String password) {
        super(username);
        this.password = password;
        this.type = "Elite";
    }
    public boolean authorize(String password){
        return password.equals(this.password);
    }
    @Override
    public boolean checkoutCart(Flipzon flipzon) {
        if(super.checkoutCart(flipzon)){
            Random random = new Random();
            if(random.nextDouble() > 0.9){
                System.out.println("Congratulations, you have won a free Product.");
                for(Map.Entry<Integer, Category> categoryEntry:flipzon.categories.entrySet()){
                    if(categoryEntry.getValue().products.size() == 0) continue;
                    for(Map.Entry<Integer, Product> productEntry:categoryEntry.getValue().products.entrySet()){
                        productEntry.getValue().displayDetails(this.type);
                        productEntry.getValue().sell(1);
                        break;
                    }
                    break;
                }
            }
        }
        return true;
    }
    @Override
    public void generateCoupons() {
        Random random = new Random();
        int cnt = 3 + random.nextInt(2);
        for(int i = 0; i < cnt; i++){
            Coupon newCoupon = new Coupon();
            coupons.add(newCoupon);
        }
    }
    @Override
    public double calculateDeliveryCharges(double amount) {
        return 100;
    }
    @Override
    public int calculateDeliveryTime() {
        Random random = new Random();
        return 1 + random.nextInt(2);
    }
    @Override
    public boolean upgradeStatus(String newStatus, Flipzon flipzon) {
        System.out.println("Cannot upgrade status any further.");
        return false;
    }
}
