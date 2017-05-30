package servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vasiliev on 5/29/2017.
 */
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        PrintWriter out=resp.getWriter();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

/*        HttpSession session = request.getSession(false);
        String loginURI = "/fm/login";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {*/
            chain.doFilter(request, response);
/*        } else {
            request.setAttribute("requestURI", request.getRequestURI());
            response.sendRedirect(loginURI);
        }*/

    }

    public void init(FilterConfig config) throws ServletException {
    }

}
