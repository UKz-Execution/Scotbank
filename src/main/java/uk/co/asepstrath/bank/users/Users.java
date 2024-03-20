package uk.co.asepstrath.bank.users;

import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Users {
    private static final Path path = new File("./src/main/java/uk/co/asepstrath/bank/users/users.csv").toPath();
    private static final HashSet<User> users = new HashSet<>();

    /**
     * Load the lines of the default file.
     * @return a list of all lines in the default file in order.
     */
    private static List<String> getFileData() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not find users.csv");
        }
    }

    /**
     * Overwrite the default file with a list of strings.
     * @param data the new data to overwrite the file with
     */
    private static void writeFileData(List<String> data) {
        try {
            Files.write(path, data);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to users.csv");
        }
    }

    /**
     * @return a set of unique {@link User} objects from the default file
     * @throws RuntimeException if two users with the same username exist in the file
     */
    public static HashSet<User> loadUsersFromFile() {
        List<String> lines = getFileData();

        users.clear();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.length() == 0) continue;
            String[] lineValues = line.split(",");

            String username = "", password = "";
            boolean isSuperUser = false;
            ArrayList<Account> accounts = new ArrayList<>();

            boolean lineValid = false;
            if (lineValues.length >= 3) {
                lineValid = true;
                username = lineValues[0];
                password = lineValues[1];
                if (username.equals("") || password.equals("")) lineValid = false;

                isSuperUser = Boolean.parseBoolean(lineValues[2]);

                for (int j = 3; j < lineValues.length && lineValid; j++) {
                    try (DatabaseAPI connection = DatabaseAPI.open()) {
                        UUID id = UUID.fromString(lineValues[j]);
                        Account account;
                        if ((account = connection.getAccountById(id)) == null) throw new SQLException();
                        accounts.add(account);
                    } catch (NumberFormatException | SQLException e) {
                        lineValid = false;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (!lineValid)
                throw new RuntimeException("Invalid properties in users.csv at line " + (i+1));

            if (!users.add(((isSuperUser) ? new SuperUser(username, password, accounts) : new User(username, password, accounts))))
                throw new RuntimeException("Duplicate user '" + username + "'");
        }
        return users;
    }

    /**
     * Update the values of a user stored in the default file. If the user does not already exist in the file they
     * are added.
     * @param user the user to update the values of
     */
    public static void storeUserData(User user) {
        users.add(user); // Add the user if they don't already exist in the set

        // Store the user in the users.csv file
        List<String> lines = getFileData();

        int userLineNum = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(user.getUsername() + ",")) {
                userLineNum = i;
                break;
            }
        }

        if (userLineNum == -1) lines.add(user.toString());
        else lines.set(userLineNum, user.toString());

        writeFileData(lines);
    }
    
    /**
     * @return all users currently loaded from the file
     */
    public static List<User> getUsers() {
        return users.stream().toList();
    }

    /**
     * Get the {@link User} object of a user currently loaded from the file.
     * @param username the username of the user
     * @return the user found or null if no user was found
     */
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

}
