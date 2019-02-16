package alex;

import alex.filters.*;
import alex.servlets.auth.ServletAuth;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import alex.servlets.ServletCalculator;
import alex.servlets.ServletCookies;
import alex.servlets.ServletMain;
import alex.servlets.auth.ServletLogin;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.util.EnumSet;

public class App {
    public static void main(String[] args) throws Exception {
        Connection conn = new DbConnection().connection();


        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(new ServletHolder(new ServletCookies()), "/calcApp/cookies");

        handler.addServlet(new ServletHolder(new ServletMain()), "/");
        handler.addServlet(new ServletHolder(new ServletAuth()), "/calcApp/auth");
        handler.addServlet(new ServletHolder(new ServletLogin()), "/calcApp/login");
        handler.addServlet(new ServletHolder(new ServletCalculator(conn)), "/calcApp/calc/*");
        handler.addServlet(new ServletHolder(new CalcHistory(conn)), "/calcApp/calc/history/*");

        handler.addFilter(CalculatorFilter.class,"/calcApp/calc/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        handler.addFilter(CalculatorFilterDivByZero.class,"/calcApp/calc/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));


        Server server = new Server(80);

        server.setHandler(handler);

        server.start();
        server.join();



    }
}
