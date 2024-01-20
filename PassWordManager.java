import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PassWordManager {
    private static Map<String, String> passwordStorage = new HashMap<>();

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void addPassword(String username, String password) {
        try {
            String hashedPassword = hashPassword(password);
            passwordStorage.put(username, hashedPassword);
            System.out.println("Password added for user: " + username);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password. Please try again.");
        }
    }

    public static boolean verifyPassword(String username, String password) {
        if (passwordStorage.containsKey(username)) {
            try {
                String hashedPassword = hashPassword(password);
                return passwordStorage.get(username).equals(hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Error verifying password. Please try again.");
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPassword Manager Menu:");
            System.out.println("1. Add Password");
            System.out.println("2. Verify Password");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    addPassword(username, password);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String verifyUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String verifyPassword = scanner.next();
                    boolean isVerified = verifyPassword(verifyUsername, verifyPassword);
                    if (isVerified) {
                        System.out.println("Password verified. Access granted!");
                    } else {
                        System.out.println("Invalid username or password. Access denied.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting Password Manager. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}
