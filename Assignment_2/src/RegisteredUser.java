import java.util.Scanner;

public abstract class RegisteredUser implements User{
    public String name;
    public String type;
    private String password;
    private int balance = 1000;

    public RegisteredUser(String username, String password){
        this.name = username;
        this.password = password;
    }
    public boolean authorize(String password){
        return password.equals(this.password);
    }
    public void displayBalance() {
        System.out.println("The current balance is Rs." + this.balance);
    }
    public void updateBalance(Scanner sc) {
        System.out.print("Enter the amount to add: ");
        this.balance += sc.nextInt();
        sc.nextLine();
        return;
    }
    public void browseProducts(Flipzon flipzon){
        for(Category category:flipzon.categories){

        }
    }
    public void browseDeals(Flipzon flipzon){

    }
    public void addToCart(Product product, int quantity){

    }
    public void addToCart(Deal deal, int quantity){

    }
    public void displayCoupons(){

    }
    public void displayCart(){

    }
    public void emptyCart(){

    }
    public void checkoutCart(){

    }
    public abstract void upgradeStatus(String newStatus);
}

