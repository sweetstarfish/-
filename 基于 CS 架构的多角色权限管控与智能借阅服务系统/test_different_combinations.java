import java.security.MessageDigest;

public class test_different_combinations {
    public static void main(String[] args) {
        try {
            String password = "123456";
            String salt = "test123";
            String storedMD5 = "5f4dcc3b5aa765d61d8327deb882cf99";
            
            // Test different combinations
            String[] combinations = {
                password + salt,           // 123456test123
                salt + password,           // test123123456
                password + "test" + "123", // 123456test123
                "test" + "123" + password, // test123123456
                password,                  // 123456
                salt                       // test123
            };
            
            for (int i = 0; i < combinations.length; i++) {
                String combo = combinations[i];
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hashBytes = md.digest(combo.getBytes("UTF-8"));
                
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                String calculatedMD5 = hexString.toString();
                
                System.out.println("Combination " + (i+1) + ": " + combo);
                System.out.println("MD5: " + calculatedMD5);
                System.out.println("Match: " + calculatedMD5.equals(storedMD5));
                System.out.println();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 