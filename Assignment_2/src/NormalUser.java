public class NormalUser extends RegisteredUser{
    public NormalUser(String username, String password) {
        super(username, password);
        this.type = "Normal";
    }

    @Override
    public void upgradeStatus(String newStatus) {

    }

}
