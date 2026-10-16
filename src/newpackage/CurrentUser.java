package newpackage;

public class CurrentUser {
    private static String username = null;
    private static String gender = null; 

    public static void login(String user, String gen) {
        username = user;
        gender = gen;
    }

    public static void logout() {
        username = null;
        gender = null;
    }

    public static boolean isLoggedIn() {
        return username != null;
    }

    public static String getUsername() {
        return username;
    }

    public static String getGender() {
        return gender;
    }
}



