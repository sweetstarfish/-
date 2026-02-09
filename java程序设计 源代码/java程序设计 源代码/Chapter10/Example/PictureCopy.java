package InOutStreamTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class PictureCopy {
	public static void main (String args[]){
	FileInputStream fis = null;
    FileOutputStream fos = null;
    try {
        fis = new FileInputStream("picture.jpg");
        fos = new FileOutputStream("picture-secrect.jpg");
        byte[] buffer = new byte[20];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) (buffer[i] ^ 5);
            }
            fos.write(buffer, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  }
}
