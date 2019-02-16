package alex.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletMain extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.copy(Paths.get("src/main.html"),resp.getOutputStream());


//        Map<String, String[]> parameterMap = req.getParameterMap();
//
//
//        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
//            System.out.println(stringEntry);
//        }
//
////        String pathInfo = req.getPathInfo();
//
////        String x = req.getParameter("x");
////        String y = req.getParameter("y");
//
//
//        Cookie[] cookies = req.getCookies();
//        Enumeration<String> headerNames = req.getHeaderNames();
//
//        while (headerNames.hasMoreElements()){
//            String sn = headerNames.nextElement();
//            String sv = req.getHeader(sn);
//
//            System.out.printf("%s: %s\n",sn,sv);
//        }
//
////          how to write the response to server
//        BufferedReader br = new BufferedReader(new FileReader(new File("1.txt")));
//
////        should be called BEFORE getting resp.getWriter()
//        resp.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
//
//        PrintWriter writer = resp.getWriter();
//
//        writer.println(br.lines().collect(Collectors.joining()));
////        resp.sendStatus(404)

    }
}
