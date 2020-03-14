package User;

public class UserDao {

    public UserDao() {
    }

    public String getUserName(String RFC){
        return new SQLiteUserLoader().loadUser(RFC);
    }
}
