import java.util.ArrayList;

public class Category {
    ArrayList<Product> products = new ArrayList<Product>();
    public Product getProduct(int productId){
        return products.get(productId);
    }
}
