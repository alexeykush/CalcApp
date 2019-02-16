package alex;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalcHistory extends HttpServlet {
    private final Connection conn;

    public CalcHistory(Connection conn) {
        this.conn = conn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        try {
            String sql = "SELECT a,b,op,result,name FROM alexeyku_history JOIN alexeyku_users ON(alexeyku_history.user_id = alexeyku_users.id)";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rSet = stm.executeQuery();
            while (rSet.next()){
                int a = rSet.getInt("a");
                int b = rSet.getInt("b");
                String op = rSet.getString("op");
                int res = rSet.getInt("result");
                sb.append(rSet.getString("name") + ": ").append(a).append(op).append(b).append("=").append(res).append("<br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.append("<a href='/calcApp/calc'>Back</a></html>");
        resp.getWriter().println(sb);
    }
}
