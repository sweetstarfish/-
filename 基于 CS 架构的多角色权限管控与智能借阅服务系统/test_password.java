import java.security.MessageDigest;
import java.util.Base64;

public class test_password {
    public static void main(String[] args) {
        try {
            String password = "123456";
            String salt = "test123";
            String passwordWithSalt = password + salt;
            
            System.out.println("Password: " + password);
            System.out.println("Salt: " + salt);
            System.out.println("PasswordWithSalt: " + passwordWithSalt);
            
            // Calculate MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(passwordWithSalt.getBytes("UTF-8"));
            
            // Base64 encode
            String calculatedHash = Base64.getEncoder().encodeToString(hashBytes);
            System.out.println("CalculatedHash: " + calculatedHash);
            
            // Stored value in database
            String storedHash = "NWY0ZGNjM2I1YWE3NjVkNjFkODMyN2RlYjg4MmNmOTk=";
            System.out.println("StoredHash: " + storedHash);
            System.out.println("Match: " + calculatedHash.equals(storedHash));
            
            // Show byte arrays for debugging
            System.out.println("CalculatedHash bytes: " + java.util.Arrays.toString(hashBytes));
            System.out.println("CalculatedHash length: " + calculatedHash.length());
            System.out.println("StoredHash length: " + storedHash.length());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 