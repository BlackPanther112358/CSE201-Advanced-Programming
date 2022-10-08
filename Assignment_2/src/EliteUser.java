public class EliteUser extends RegisteredUser{
    public EliteUser(String username, String password) {
        super(username, password);
        this.type = "Elite";
    }
    @Override
    public void upgradeStatus(String newStatus) {

    }
}
