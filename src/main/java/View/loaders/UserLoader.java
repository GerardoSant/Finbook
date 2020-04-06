package View.loaders;

import Model.User.User;

public interface UserLoader {
    User loadUser(String RFC);
}
