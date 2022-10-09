public class Deal {
    public Product prod1;
    public Product prod2;
    public int normalCost;
    public int primeCost;
    public int eliteCost;
    public Deal(Product prod1, Product prod2, int normalCost, int primeCost, int eliteCost){
        this.prod1 = prod1;
        this.prod2 = prod2;
        this.normalCost = normalCost;
        this.primeCost = primeCost;
        this.eliteCost = eliteCost;
    }

    public void displayDetails(String type){
        System.out.println("The details of products included are:-");
        this.prod1.displayDetails(type);
        System.out.println();
        this.prod2.displayDetails(type);
        int price = this.normalCost;
        switch (type){
            case "Prime" -> price = this.primeCost;
            case "Elite" -> price = this.eliteCost;
        }
        System.out.println("You can now buy them at special rate of Rs." + price + "/-");
    }

    public boolean checkAvailability(int quantity){
        return prod1.checkAvailability(quantity) && prod2.checkAvailability(quantity);
    }

    public double getAmount(String type){
        double price = 0;
        switch (type){
            case "Normal" -> price = this.normalCost;
            case "Prime" -> price = this.primeCost;
            case "Elite" -> price = this.eliteCost;
        }
        return price;
    }

    public void sell(int quantity){
        prod1.sell(quantity);
        prod2.sell(quantity);
    }
}
