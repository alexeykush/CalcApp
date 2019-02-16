package alex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseApp {
    public static void main1(String[] args) {
        try {
            Connection conn = new DbConnection().connection();
            String sql = "INSERT INTO alexeyku_history (a,b,op,res) VALUES (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1,11);
            stm.setInt(2,3);
            stm.setString(3,"+");
            stm.setInt(4,14);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
