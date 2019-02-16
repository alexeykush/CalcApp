package alex.filters;

import alex.ParameterFromRequest;
import org.eclipse.jetty.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class CalculatorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        } else {
            throw new IllegalArgumentException("ServletRequest should be instance of HttpServletRequest");
        }

        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            try {
                ParameterFromRequest pfr = new ParameterFromRequest(req);
                int a = pfr.getInt("a");
                int b = pfr.getInt("b");
                String command = pfr.getStr("op");
                chain.doFilter(request, response);
            } catch (Exception e) {
                response.getWriter().println(e.getMessage());
            }
        } else {
            chain.doFilter(request, response);
        }
    }




    @Override
    public void destroy() {

    }
}
