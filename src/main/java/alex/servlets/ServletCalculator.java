package alex.servlets;

import alex.ParameterFromRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletCalculator extends HttpServlet {
    private Map<Integer,String> history = new HashMap<>();
    private int counter = 0;
    private final Connection conn;

    public ServletCalculator(Connection conn) {
        this.conn = conn;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        if(req.getParameter("history") != null){
//            String msg = "";
//
//            for (Map.Entry<Integer, String> map : history.entrySet()) {
//                msg += String.format("%s: %s<br>",map.getKey(),map.getValue());
//            }
//
//
//            resp.getWriter().printf("<html> %s <br><a href=\"/calcApp/calc\">Back</a></html>",msg);
//        }
//        else{

            boolean flag = false;
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String sn = headerNames.nextElement();
                String sv = req.getHeader(sn);

                if(sn.equals("Referer") && sv.matches(".*calcApp/(login|calc.*)")){
                    flag = true;
                    break;
                }
            }

            if(flag){
                Files.copy(Paths.get("src/calc.html"), resp.getOutputStream());
            }
            else{
                resp.getWriter().println("<html>" +
                        "<b>You are not authorized!!!<b/><br>"+
                        "<a href=\"/calcApp/auth\">Back</a>" +
                        "</html>");
            }

            if(resp.getHeader("calc") != null){
                history.put(++counter,resp.getHeader("calc"));
                resp.getOutputStream().print(resp.getHeader("calc"));
            }

//        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ParameterFromRequest pfr = new ParameterFromRequest(req);
        int a = pfr.getInt("a");
        int b = pfr.getInt("b");
        String command = pfr.getStr("op");
        int result = 0;
        String operation="";
        switch(command) {
            case "add":
                result = a + b;
                operation = "+";
                break;
            case "sub":
                result = a - b;
                operation = "-";
                break;
            case "mul":
                result = a * b;
                operation = "*";
                break;
            case "div":
                result = a / b;
                operation = "/";
                break;
        }
        saveOperationToDb(a,b,operation,result);
        resp.setHeader("calc",String.format("%d %s %d = %d", a, operation, b, result));
        doGet(req,resp);
    }

    private void saveOperationToDb(int a, int b, String op, int res) {

        try {

            String sql = "INSERT INTO alexeyku_history (a,b,op,result) VALUES (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1,a);
            stm.setInt(2,b);
            stm.setString(3,op);
            stm.setInt(4,res);
            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
