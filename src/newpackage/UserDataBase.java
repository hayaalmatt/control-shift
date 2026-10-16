package newpackage;

import java.io.*;

public class UserDataBase {

    private static final String FILE_NAME = "users.txt";

    public static boolean userExists(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) return true;
            }
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public static boolean addUser(String username, String password, String gender) {
        if (userExists(username)) return false;

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(username + "," + password + "," + gender + "\n");
            return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public static String validateLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[1].equals(password)) {
                    return data[2]; 
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}



