import java.util.Scanner;

public class Admin {
    public final String username = "aakarsh";

    public boolean authorize(String username, String password){
        String adminPassword = "2021507";
        return (username.equals(this.username)) && (password.equals(adminPassword));
    }
    public void addCategory(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the Category name: ");
        String catName = sc.nextLine();
        System.out.print("Please enter the Category ID: ");
        int catId = sc.nextInt();
        sc.nextLine();
        if(flipzon.categories.containsKey(catId)){
            System.out.println("The Category ID is already used, Please set a different ID.");
            return;
        }
        Category category = new Category(sc, catName, catId);
        flipzon.categories.put(catId, category);
    }
    public void removeCategory(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the Category ID: ");
        int catId = sc.nextInt();
        sc.nextLine();
        if(flipzon.categories.containsKey(catId)){
            flipzon.categories.remove(catId);
        }else{
            System.out.println("No Category available with provided key");
        }
    }
    public void addProduct(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the product ID: ");
        String id = sc.nextLine();
        String[] ids = id.split("\\.", 2);
        String catId = ids[0], prodId = ids[1];
        if(flipzon.categories.containsKey(Integer.parseInt(catId))){
            flipzon.categories.get(Integer.parseInt(catId)).addProduct(sc, Integer.parseInt(prodId));
        }else{
            System.out.println("Invalid category ID, please make a new category first.");
        }
    }
    public void removeProduct(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the product ID: ");
        String id = sc.nextLine();
        String[] ids = id.split("\\.", 2);
        String catId = ids[0], prodId = ids[1];
        if(flipzon.categories.containsKey(Integer.parseInt(catId))){
            if(flipzon.categories.get(Integer.parseInt(catId)).products.containsKey(Integer.parseInt(prodId))){
                flipzon.categories.get(Integer.parseInt(catId)).products.remove(Integer.parseInt(prodId));
                System.out.println("The product has been successfully removed");
                if(flipzon.categories.get(Integer.parseInt(catId)).products.size() == 0){
                    flipzon.categories.remove(Integer.parseInt(catId));
                    System.out.println("The category has been deleted as all products were deleted.");
                }
            }else{
                System.out.println("Invalid Product key");
            }
        }else{
            System.out.println("Invalid category ID");
        }
    }
    public void setProductDiscount(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the product ID: ");
        String id = sc.nextLine();
        String[] ids = id.split("\\.", 2);
        String catId = ids[0], prodId = ids[1];
        if(flipzon.categories.containsKey(Integer.parseInt(catId))){
            if(flipzon.categories.get(Integer.parseInt(catId)).products.containsKey(Integer.parseInt(prodId))){
                System.out.print("Please enter the discount value for Normal users: ");
                double normalDiscount = sc.nextDouble()/100;
                System.out.print("Please enter the discount value for Prime users: ");
                double primeDiscount = sc.nextDouble()/100;
                System.out.print("Please enter the discount value for Elite users: ");
                double eliteDiscount = sc.nextDouble()/100;
                flipzon.categories.get(Integer.parseInt(catId)).products.get(Integer.parseInt(prodId)).setDiscount(normalDiscount, primeDiscount, eliteDiscount);
            }else{
                System.out.println("Invalid Product key");
            }
        }else{
            System.out.println("Invalid category ID");
        }
    }
    public void addDeal(Flipzon flipzon, Scanner sc){
        System.out.print("Please enter the Deal ID: ");
        int dealId = sc.nextInt();
        sc.nextLine();
        if(flipzon.deals.containsKey(dealId)){
            System.out.println("The deal ID is already used, Please set a different ID.");
            return;
        }
        Product prod1, prod2;
        System.out.print("Please enter the product ID for first product: ");
        String id1 = sc.nextLine();
        String[] ids1 = id1.split("\\.", 2);
        String catId1 = ids1[0], prodId1 = ids1[1];
        if(flipzon.categories.containsKey(Integer.parseInt(catId1))){
            if(flipzon.categories.get(Integer.parseInt(catId1)).products.containsKey(Integer.parseInt(prodId1))){
                prod1 = flipzon.categories.get(Integer.parseInt(catId1)).products.get(Integer.parseInt(prodId1));
            }else{
                System.out.println("Invalid Product key");
                return;
            }
        }else{
            System.out.println("Invalid category ID");
            return;
        }
        System.out.print("Please enter the product ID for second product: ");
        String id2 = sc.nextLine();
        String[] ids2 = id2.split("\\.", 2);
        String catId2 = ids2[0], prodId2 = ids2[1];
        if(flipzon.categories.containsKey(Integer.parseInt(catId2))){
            if(flipzon.categories.get(Integer.parseInt(catId2)).products.containsKey(Integer.parseInt(prodId2))){
                prod2 = flipzon.categories.get(Integer.parseInt(catId2)).products.get(Integer.parseInt(prodId2));
            }else{
                System.out.println("Invalid Product key");
                return;
            }
        }else{
            System.out.println("Invalid category ID");
            return;
        }
        System.out.print("Enter the cost for Normal users: ");
        int normalCost = sc.nextInt();
        sc.nextLine();
        if((prod1.getAmount("Normal") + prod2.getAmount("Normal")) <= normalCost){
            System.out.println("The combined cost of products should be more than the cost of deal");
            return;
        }
        System.out.print("Enter the cost for Prime users: ");
        int primeCost = sc.nextInt();
        sc.nextLine();
        if((prod1.getAmount("Prime") + prod2.getAmount("Prime")) <= primeCost){
            System.out.println("The combined cost of products should be more than the cost of deal");
            return;
        }
        System.out.print("Enter the cost for Elite users: ");
        int eliteCost = sc.nextInt();
        if((prod1.getAmount("Elite") + prod2.getAmount("Elite")) <= eliteCost){
            System.out.println("The combined cost of products should be more than the cost of deal");
            return;
        }
        sc.nextLine();
        Deal deal = new Deal(prod1, prod2, normalCost, primeCost, eliteCost);
        flipzon.deals.put(dealId, deal);
    }
}