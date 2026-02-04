package user;

import utils.PropertyReader;

public class UserFactory {

    public static User withAdminPermission() {
        return new User(PropertyReader.getProperty("skyrexionewdemmo.admin_user"),
                PropertyReader.getProperty("skyrexionewdemmo.password"));
    }

    public static User withLockedPermission() {
        return new User(PropertyReader.getProperty("skyrexionewdemmo.locked_user"),
                PropertyReader.getProperty("skyrexionewdemmo.password"));
    }
}
