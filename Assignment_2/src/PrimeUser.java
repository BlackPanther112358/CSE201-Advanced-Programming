public class PrimeUser extends RegisteredUser{
    public PrimeUser(String username, String password) {
        super(username, password);
        this.type = "Prime";
    }
    @Override
    public void upgradeStatus(String newStatus) {

    }
}
