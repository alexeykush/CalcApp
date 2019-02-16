package alex;

import alex.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static List<User> users = new ArrayList<>();

    public static void add(User user){
        users.add(user);
    }

    public static boolean existUser(User user){
        return users.indexOf(user) != -1;
    }
}
