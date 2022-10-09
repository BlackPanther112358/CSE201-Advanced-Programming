import java.util.Map;

public class UnregisteredUser implements User{

    public final String type = "Unregistered";
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
}
