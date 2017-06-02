package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vasiliev on 5/26/2017.
 */

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getParameterMap().isEmpty()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            InputStream propertiesAsStream = getClass().getClassLoader().getResourceAsStream("auth.properties");
            Properties properties = new Properties();
            properties.load(propertiesAsStream);

            String userKey = "user." + username;
            String userPassword = (String) properties.get(userKey);
            String message = null;
            if (userPassword != null && password.equals(userPassword)) {
                request.getSession().setAttribute("user", username);
                message = "SUCCESS";
            } else {
                message = "FAILURE";
            }
            response.getWriter().write(message);
        } else {
            request.getRequestDispatcher("index.xhtml").forward(request, response);
        }
    }
}