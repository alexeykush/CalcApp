package alex.servlets.auth;

import alex.User;
import alex.UserDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServletAuth extends HttpServlet {

    private static final String validLogin = "[a-zA-Z0-9]{3,}";
    private static final String validPassword = "[a-zA-Z0-9?(-.@$%)]{3,}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.copy(Paths.get("src/registration.html"),resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(!login.matches(validLogin) || !password.matches(validPassword)){
            writer.println("<html>" +
                    "<b>Invalid login or password. Enter once more<b/><br>"+
                    "<a href=\"/calcApp/auth\">Back</a>" +
                    "</html>");
        }
        else{
            User user = new User(login,password);

            if(UserDB.existUser(user)){
                writer.println("<html>" +
                        "<b>Such user exists<b/><br>"+
                        "<a href=\"/calcApp/auth\">Back</a>" +
                        "</html>");
            }
            else{
                UserDB.add(user);
                resp.sendRedirect("/calcApp/login");
            }
        }

    }
}
