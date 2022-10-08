public class Admin {
    public final String username = "aakarsh";
    private final String password = "2021507";

    public boolean authorize(String username, String password){
        return (username.equals(this.username)) && (password.equals(this.password));
    }
    public void addCategory(){

    }
    public void removeCategory(){

    }
    public void addProduct(){

    }
    public void removeProduct(){

    }
    public void setProductDiscount(){

    }
    public void addDeal(){

    }
}
