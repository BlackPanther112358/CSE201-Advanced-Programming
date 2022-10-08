import java.util.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mainMenu();
        System.out.println("Program Terminated");
    }
}

class Menu {

    public final Flipzon flipzon = new Flipzon();
    private final Admin admin = new Admin();
    Scanner sc = new Scanner(System.in);

    public void mainMenu(){
        UnregisteredUser tempCustomer = new UnregisteredUser();
        ArrayList<String> menuArgs = new ArrayList<String>();
        menuArgs.add("Welcome to FLIPZON");
        menuArgs.add("Enter as Admin");
        menuArgs.add("Explore the Product Catalog");
        menuArgs.add("Show Available Deals");
        menuArgs.add("Enter as Customer");
        menuArgs.add("Exit the Application");
        int choice = this.makeMenu(menuArgs);
        switch (choice){
            case 1 -> this.adminLogin();
            case 2 -> tempCustomer.browseProducts(flipzon);
            case 3 -> tempCustomer.browseDeals(flipzon);
            case 4 -> this.customerMainMenu();
            case 5 -> {return;}
        }
        this.mainMenu();
    }

    private void adminLogin(){
        System.out.println("Dear Admin,\nPlease enter your username and password.");
        System.out.print("Enter the username: ");
        String username = sc.nextLine();
        System.out.print("Enter the password: ");
        String password = sc.nextLine();
        if(admin.authorize(username, password)) this.adminMenu();
        else{
            System.out.println("Invalid username or password");
            return;
        }
    }
    private void adminMenu(){
        ArrayList<String> menuArgs = new ArrayList<String>();
        menuArgs.add("Welcome " + admin.username + "\nPlease choose any one of the following actions:");
        menuArgs.add("Add category");
        menuArgs.add("Delete category");
        menuArgs.add("Add Product");
        menuArgs.add("Delete Product");
        menuArgs.add("Set Discount on Product");
        menuArgs.add("Add giveaway deal");
        menuArgs.add("Back");
        int choice = makeMenu(menuArgs);
        switch (choice){
            case 1 -> admin.addCategory();
            case 2 -> admin.removeCategory();
            case 3 -> admin.addProduct();
            case 4 -> admin.removeProduct();
            case 5 -> admin.setProductDiscount();
            case 6 -> admin.addDeal();
            case 7 -> {return;}
        }
        this.adminMenu();
    }

    private void customerMainMenu(){
        ArrayList<String> menuArgs = new ArrayList<String>();
        menuArgs.add("Please choose any one of the following actions:");
        menuArgs.add("Sign up");
        menuArgs.add("Log in");
        menuArgs.add("Back");
        int choice = makeMenu(menuArgs);
        switch (choice){
            case 1 -> flipzon.addCustomer(sc);
            case 2 -> this.loginCustomer();
            case 3 -> {return;}
        }
        this.customerMainMenu();
    }
    private void loginCustomer(){
        System.out.println("Dear Admin,\nPlease enter your username and password.");
        System.out.print("Enter the username: ");
        String username = sc.nextLine();
        System.out.print("Enter the password: ");
        String password = sc.nextLine();
        if(flipzon.authorizeCustomer(username, password)){
            RegisteredUser customer = flipzon.getCustomer(username);
            this.customerMenu(customer);
        }else{
            System.out.println("Invalid username or password");
            return;
        }
    }
    private void customerMenu(RegisteredUser customer){
        ArrayList<String> menuArgs = new ArrayList<String>();
        menuArgs.add("Welcome " + customer.name);
        menuArgs.add("Browse products");
        menuArgs.add("Browse deals");
        menuArgs.add("Add a product to cart");
        menuArgs.add("Add products in deal to cart");
        menuArgs.add("View coupons");
        menuArgs.add("Check account balance");
        menuArgs.add("View cart");
        menuArgs.add("Empty cart");
        menuArgs.add("Checkout cart");
        menuArgs.add("Upgrade customer status");
        menuArgs.add("Add amount to wallet");
        menuArgs.add("Back");
        int choice = makeMenu(menuArgs);
        switch (choice){
            case 1 -> customer.browseProducts(flipzon);
            case 2 -> customer.browseDeals(flipzon);
            case 3 -> {
                System.out.print("Enter the product ID: ");
                String id = sc.nextLine();
                String[] ids = id.split("\\.", 2);
                String catId = ids[0], prodId = ids[1];
                Product product = flipzon.getProduct(Integer.parseInt(catId), Integer.parseInt(prodId));
                System.out.print("Enter the product quantity: ");
                int quantity = sc.nextInt();
                sc.nextLine();
                customer.addToCart(product, quantity);
            }
            case 4 -> {
                System.out.print("Enter the Deal ID: ");
                int dealId = sc.nextInt();
                sc.nextLine();
                Deal deal = flipzon.getDeal(dealId);
                System.out.print("Enter the product quantity: ");
                int quantity = sc.nextInt();
                sc.nextLine();
                customer.addToCart(deal, quantity);
            }
            case 5 -> customer.displayCoupons();
            case 6 -> customer.displayBalance();
            case 7 -> customer.displayCart();
            case 8 -> customer.emptyCart();
            case 9 -> customer.checkoutCart();
            case 10 -> {
                System.out.print("Enter the new status: ");
                String status = sc.nextLine();
                customer.upgradeStatus(status);
            }
            case 11 -> customer.updateBalance(sc);
            case 12 -> {return;}
        }
        this.customerMenu(customer);
    }

    private int makeMenu(ArrayList<String> menuFields){
        int option;
        System.out.println();
        System.out.println(menuFields.get(0));
        for(int i = 1; i < menuFields.size(); i++){
            System.out.printf("%d) %s\n", i, menuFields.get(i));
        }
        System.out.print("Choose an option: ");
        option = sc.nextInt();
        sc.nextLine();
        System.out.println();
        return option;
    }
}