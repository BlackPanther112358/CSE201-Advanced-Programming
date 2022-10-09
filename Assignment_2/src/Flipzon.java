import java.util.*;

public class Flipzon {

    ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
    public HashMap<Integer, Category> categories = new HashMap<Integer, Category>();
    public HashMap<Integer, Deal> deals = new HashMap<Integer, Deal>();

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
        this.users.add(newUser);
    }

    public Product getProduct(int categoryId, int productId){
        return categories.get(categoryId).getProduct(productId);
    }
    public Deal getDeal(int dealId){
        return deals.get(dealId);
    }
}
