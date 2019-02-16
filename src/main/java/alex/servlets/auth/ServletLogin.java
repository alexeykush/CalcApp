package alex.servlets.auth;

import alex.User;
import alex.UserDB;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.copy(Paths.get("src/login.html"),resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String login = req.getParameter("login");
        String password = req.getParameter("password");


        User user = new User(login,password);

        if(!UserDB.existUser(user)){
            writer.println("<html>" +
                    "<b>Incorrect login or password<b/><br>"+
                    "<a href=\"/calcApp/login\">Back</a>" +
                    "</html>");
        }
        else{
            resp.sendRedirect(String.format("/calcApp/cookies?user=%s",String.valueOf(user)));
        }

    }
}
