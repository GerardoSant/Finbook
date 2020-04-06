package View.daos;

import Implementations.SQLite.SQLiteUserLoader;
import Model.User.User;

public class UserDao {

    public UserDao() {
    }

    public User getUser(String RFC){
        return new SQLiteUserLoader().loadUser(RFC);
    }
}
