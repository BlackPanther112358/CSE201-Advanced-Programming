import java.util.HashMap;
import java.util.Map;

public class Product {

    public String name;
    public int id;
    public int quantity = 0;
    HashMap<String, String> details;
    public double cost;
    public double normalDiscount = 0;
    public double primeDiscount = 0.05;
    public double eliteDiscount = 0.1;
    public Product(String name, int prodId, HashMap<String, String> details, int amount, int quantity){
        this.name = name;
        this.id = prodId;
        this.details = details;
        this.cost = amount;
        this.quantity = quantity;
    }
    public boolean checkAvailability(int quantity){
        return this.quantity >= quantity;
    }
    public void sell(int quantity){
        if(quantity < this.quantity) return;
        this.quantity -= quantity;
    }

    public void setDiscount(double normal, double prime, double elite){
        this.normalDiscount = normal;
        this.primeDiscount = prime;
        this.eliteDiscount = elite;
    }
    public double getAmount(String type){
        double price = this.cost;
        switch (type){
            case "Normal" -> price = this.cost*(1 - max(0, this.normalDiscount));
            case "Prime" -> price = this.cost*(1 - max(0.05, this.primeDiscount));
            case "Elite" -> price = this.cost*(1 - max(0.1, this.eliteDiscount));
        }
        return price;
    }
    public double getAmount(String type, double couponDiscount){
        double price = this.cost;
        switch (type){
            case "Normal" -> price = this.cost*(1 - max(0, this.normalDiscount, couponDiscount));
            case "Prime" -> price = this.cost*(1 - max(0.05, this.primeDiscount, couponDiscount));
            case "Elite" -> price = this.cost*(1 - max(0.1, this.eliteDiscount, couponDiscount));
        }
        return price;
    }
    public void displayDetails(String type){
        System.out.println("Product name: " + this.name);
        System.out.println("Product details:-");
        for(Map.Entry<String, String> attribute:details.entrySet()){
            System.out.println(attribute.getKey() + ": " + attribute.getValue());
        }
        double price = this.cost;
        switch (type){
            case "Normal" -> price = this.cost*(1 - max(0, this.normalDiscount));
            case "Prime" -> price = this.cost*(1 - max(0.05, this.primeDiscount));
            case "Elite" -> price = this.cost*(1 - max(0.1, this.eliteDiscount));
        }
        System.out.println("Price: Rs." + price + "/-");
        return;
    }

    public double max(double a, double b){
        if(Double.compare(a, b) > 0) return b;
        else return a;
    }
    public double max(double a, double b, double c){
        return max(a, max(b, c));
    }
}
