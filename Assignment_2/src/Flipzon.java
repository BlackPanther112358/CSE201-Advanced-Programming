import java.util.*;

public class Flipzon {

    private ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
    public ArrayList<Category> categories = new ArrayList<Category>();
    public ArrayList<Deal> deals = new ArrayList<Deal>();

    public boolean authorizeCustomer(String username, String password){
        for (RegisteredUser user : users) {
            if (username.equals(user.name)) {
                return user.authorize(password);
            }
        }
        return false;
    }
    public RegisteredUser getCustomer(String username){
        for(RegisteredUser user : users){
            if(username.equals(user.name)) return user;
        }
        //Return the last user if the user is not found, ideally this condition should not occur as this function is called after authorizing.
        return users.get(users.size() - 1);
    }

    public void addCustomer(Scanner sc) {
        System.out.print("Please enter the username: ");
        String username = sc.nextLine();
        for(RegisteredUser user:users){
            if(username.equals(user.name)){
                System.out.println("The username has been already taken, try again");
                return;
            }
        }
        System.out.print("Please enter the password: ");
        String password = sc.nextLine();
        NormalUser newUser = new NormalUser(username, password);
    }

    public Product getProduct(int categoryId, int productId){
        return categories.get(categoryId).getProduct(productId);
    }
    public Deal getDeal(int dealId){
        return deals.get(0);
    }
}
