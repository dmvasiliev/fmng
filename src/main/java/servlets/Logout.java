package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by vasiliev on 5/29/2017.
 */
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/WEB-INF/views/logout.xhtml").include(request, response);
        HttpSession session = request.getSession();
        session.invalidate();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0);
        response.getWriter().write("SUCCESS");
//        response.sendRedirect("index.xhtml");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        request.getRequestDispatcher("/WEB-INF/views/logout.xhtml").include(request, response);

        HttpSession session = request.getSession();
        session.invalidate();

        out.print("You are successfully logged out!");

        out.close();*/
    }
}