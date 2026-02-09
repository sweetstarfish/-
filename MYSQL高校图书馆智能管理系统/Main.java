package code6;
import code6.login.Login;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
            new Login().loginUi();
    }
}
