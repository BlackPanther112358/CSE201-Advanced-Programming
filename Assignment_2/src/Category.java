import java.util.*;

public class Category {

    public final String name;
    public final int id;
    HashMap<Integer, Product> products = new HashMap<Integer, Product>();
    public HashSet<String> details = new HashSet<String>();
    public Category(Scanner sc, String name, int id){
        this.name = name;
        this.id = id;
        System.out.println("Please enter the required details for products of this category:-");
        System.out.print("Please enter the number of details: ");
        int cnt = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < cnt; i++){
            System.out.print("Please enter the Field name: ");
            String attribute = sc.nextLine();
            details.add(attribute);
        }
        System.out.println("Please enter a product for this category:-");
        this.addProduct(sc);
    }
    public Product getProduct(int productId){
        return products.get(productId);
    }

    public void addProduct(Scanner sc){
        HashMap<String, String> features = new HashMap<String, String>();
        System.out.print("Please enter the name: ");
        String prodName = sc.nextLine();
        System.out.print("Please enter the product ID: ");
        String id = sc.nextLine();
        String[] ids = id.split("\\.", 2);
        String catId = ids[0], prodId = ids[1];
        if(Integer.parseInt(catId) != this.id){
            System.out.println("The Category ID does not match, please enter the correct Category ID");
            return;
        }
        if(products.containsKey(Integer.parseInt(prodId))){
            System.out.println("The product ID has been already used");
            return;
        }
        for(String attribute:details){
            System.out.print("Please enter " + attribute + ": ");
            String val = sc.nextLine();
            features.put(attribute, val);
        }
        System.out.print("Please enter the amount: ");
        int prodAmount = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the quantity: ");
        int prodQuantity = sc.nextInt();
        sc.nextLine();
        Product product = new Product(prodName, Integer.parseInt(prodId), features, prodAmount, prodQuantity);
        products.put(Integer.parseInt(prodId), product);
    }

    public void addProduct(Scanner sc, int prodId){
        if(products.containsKey(prodId)){
            System.out.println("The product ID has been already used");
            return;
        }
        HashMap<String, String> features = new HashMap<String, String>();
        System.out.print("Please enter the name: ");
        String prodName = sc.nextLine();
        for(String attribute:details){
            System.out.print("Please enter " + attribute + ": ");
            String val = sc.nextLine();
            features.put(attribute, val);
        }
        System.out.print("Please enter the amount: ");
        int prodAmount = sc.nextInt();
        sc.nextLine();
        System.out.print("Please enter the quantity: ");
        int prodQuantity = sc.nextInt();
        sc.nextLine();
        Product product = new Product(prodName, prodId, features, prodAmount, prodQuantity);
        products.put(prodId, product);
    }
}
