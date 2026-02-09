import java.util.Base64;

public class decode_password {
    public static void main(String[] args) {
        try {
            String storedHash = "NWY0ZGNjM2I1YWE3NjVkNjFkODMyN2RlYjg4MmNmOTk=";
            
            // Decode Base64
            byte[] decodedBytes = Base64.getDecoder().decode(storedHash);
            System.out.println("Decoded bytes: " + java.util.Arrays.toString(decodedBytes));
            
            // Convert to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : decodedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            System.out.println("Hex string: " + hexString.toString());
            
            // Try to decode as string
            String decodedString = new String(decodedBytes, "UTF-8");
            System.out.println("Decoded as string: " + decodedString);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 