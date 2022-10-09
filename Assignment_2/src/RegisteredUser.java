import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public abstract class RegisteredUser implements User{
    public String name;
    public String type;
    public double balance = 1000;
    ArrayList<buyProduct> productCart = new ArrayList<buyProduct>();
    ArrayList<buyDeal> dealCart = new ArrayList<buyDeal>();
    ArrayList<Coupon> coupons = new ArrayList<Coupon>();

    public RegisteredUser(String username){
        this.name = username;
    }
    public abstract boolean authorize(String password);
    public void displayBalance() {
        System.out.println("The current balance is Rs." + this.balance);
    }
    public void updateBalance(Scanner sc) {
        System.out.print("Enter the amount to add: ");
        this.balance += sc.nextDouble();
        sc.nextLine();
    }
    public void browseProducts(Flipzon flipzon){
        if(flipzon.categories.size() == 0){
            System.out.println("There are no Categories available");
            return;
        }
        for(Map.Entry<Integer, Category> categoryEntry: flipzon.categories.entrySet()){
            System.out.println("Category name: " + categoryEntry.getValue().name);
            if(categoryEntry.getValue().products.size() == 0){
                System.out.println("There are no products in this Category");
                continue;
            }
            for(Map.Entry<Integer, Product> productEntry: categoryEntry.getValue().products.entrySet()){
                System.out.println("ID: " + categoryEntry.getValue().id + "." + productEntry.getValue().id);
                productEntry.getValue().displayDetails(this.type);
            }
        }
    }
    public void browseDeals(Flipzon flipzon){
        if(flipzon.deals.size() == 0){
            System.out.println("There are no deals available");
            return;
        }
        for(Map.Entry<Integer, Deal> dealEntry:flipzon.deals.entrySet()){
            System.out.println("ID: " + dealEntry.getKey());
            dealEntry.getValue().displayDetails(this.type);
        }
    }
    public void addToCart(Product product, int quantity){
        productCart.add(new buyProduct(product, quantity));
    }
    public void addToCart(Deal deal, int quantity){
        dealCart.add(new buyDeal(deal, quantity));
    }
    public void displayCoupons(){
        if(coupons.size() == 0){
            System.out.println("There are no coupons available");
            return;
        }
        for(Coupon coupon:coupons){
            coupon.display();
        }
    }
    public Coupon getHighestCoupon(){
        Coupon maxCoupon = new Coupon(0);
        for(Coupon coupon:coupons){
            if(Double.compare(coupon.getDiscount(), maxCoupon.getDiscount()) > 0) maxCoupon = coupon;
        }
        return maxCoupon;
    }
    public void displayCart(){
        if(productCart.size() == 0){
            System.out.println("There are no products in the Cart");
        }else {
            System.out.println("The products added to cart are:-");
            for(buyProduct productRecord:productCart){
                productRecord.product().displayDetails(this.type);
                System.out.println("Quantity: " + productRecord.quantity());
                System.out.println();
            }
        }
        if(dealCart.size() == 0){
            System.out.println("There are no deals in the Cart");
        }else {
            System.out.println("The deals added to cart are:-");
            for(buyDeal dealRecord:dealCart){
                dealRecord.deal().displayDetails(this.type);
                System.out.println("Quantity: " + dealRecord.quantity());
                System.out.println();
            }
        }
    }
    public void emptyCart(){
        productCart.clear();
        dealCart.clear();
        System.out.println("The Cart has been emptied");
    }
    public boolean checkoutCart(Flipzon flipzon){
        this.displayCart();
        Coupon coupon = this.getHighestCoupon();
        System.out.println("Max Coupon discount available: " + coupon.getDiscount());
        double amount = 0;
        for(buyProduct productRecord:productCart){
            if(!productRecord.product().checkAvailability(productRecord.quantity())){
                System.out.println("Some products have insufficient stock, please remove them from cart.");
                return false;
            }
            amount += (productRecord.product().getAmount(this.type, coupon.getDiscount()))*(productRecord.quantity());
            productRecord.product().sell(productRecord.quantity());
        }
        for(buyDeal dealRecord:dealCart){
            if(!dealRecord.deal().checkAvailability(dealRecord.quantity())){
                System.out.println("Some deals have insufficient stock, please remove them from cart.");
                return false;
            }
            amount += (dealRecord.deal().getAmount(this.type))*(dealRecord.quantity());
            dealRecord.deal().sell(dealRecord.quantity());
        }
        double deliveryCharges = this.calculateDeliveryCharges(amount);
        System.out.println("Order Value: " + amount);
        System.out.println("Delivery Charges: " + deliveryCharges);
        double totalAmount = amount + deliveryCharges;
        System.out.println("Total Cost: " + totalAmount);
        if(this.balance >= totalAmount){
            this.balance -= totalAmount;
            System.out.println("The items will be delivered within " + this.calculateDeliveryTime() + " working days");
            if(coupon.getDiscount() > 0) coupons.remove(coupon);
            if(totalAmount > 5000) this.generateCoupons();
            return true;
        }else{
            System.out.println("Transaction failed due to Insufficient funds");
            return false;
        }
    }
    public abstract void generateCoupons();
    public abstract double calculateDeliveryCharges(double amount);
    public abstract int calculateDeliveryTime();
    public abstract boolean upgradeStatus(String newStatus, Flipzon flipzon);
}

record buyProduct(Product product, int quantity){}
record buyDeal(Deal deal, int quantity){}

