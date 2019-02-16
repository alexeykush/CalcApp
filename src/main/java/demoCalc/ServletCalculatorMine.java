package demoCalc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ServletCalculatorMine extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.copy(Paths.get("src/calc.html"),resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String validQuery = "x=-?\\d+(.\\d+)?&y=-?\\d+(.\\d+)?&op=(add|sub|multi|div)";

        PrintWriter writer = resp.getWriter();

//        if (!req.getQueryString().matches(validQuery)) {
//            BufferedReader br = new BufferedReader(new FileReader(new File("badQuery.txt")));
//            writer.println(br.lines().collect(Collectors.joining()));
//        } else {

            double x = Double.parseDouble(req.getParameter("x"));
            double y = Double.parseDouble(req.getParameter("y"));

            String op = req.getParameter("op");

            double result = 0;

            switch (op) {
                case "add":
                    result = add(x, y);
                    break;
                case "sub":
                    result = sub(x, y);
                    break;
                case "multi":
                    result = multi(x, y);
                    break;
                case "div":
                    result = division(x, y);
                    break;
            }

            if (String.valueOf(result).matches("-?[0-9]+.0+")) {
                writer.printf("<html> <b> %s </b></html>",(int) result);
            } else {
                writer.printf("<html> <b> %s </b></html>",result);
            }

//        }

    }


    private double add(double x, double y) {
        return x + y;
    }

    private double sub(double x, double y) {
        return x - y;
    }

    private double multi(double x, double y) {
        return x * y;
    }

    private double division(double x, double y) {
        return x / y;
    }


}
