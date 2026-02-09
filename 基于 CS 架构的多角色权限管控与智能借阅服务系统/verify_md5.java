import java.security.MessageDigest;

public class verify_md5 {
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
            
            // Convert to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String calculatedMD5 = hexString.toString();
            System.out.println("Calculated MD5: " + calculatedMD5);
            
            // Stored MD5 from database
            String storedMD5 = "5f4dcc3b5aa765d61d8327deb882cf99";
            System.out.println("Stored MD5: " + storedMD5);
            System.out.println("Match: " + calculatedMD5.equals(storedMD5));
            System.out.println("Calculated length: " + calculatedMD5.length());
            System.out.println("Stored length: " + storedMD5.length());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 