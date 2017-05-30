package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * Created by vasiliev on 5/29/2017.
 */
public class Registration extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
//        PrintWriter out = resp.getWriter();

        String name = req.getParameter("userName");
        InputStream propertiesAsStream = getClass().getClassLoader().getResourceAsStream("auth.properties");
        Properties properties = new Properties();
        properties.load(propertiesAsStream);

        String userKey = "name." + name;
        String user = (String) properties.get(userKey.toLowerCase());
        if (user != null) {
            System.out.println("This name already exist. Please try another name.");
            //// TODO: 5/29/2017 mesage
        } else {
            if (name != null) {
            Properties properties2 = new Properties();
                properties2.setProperty("name." + name.toLowerCase(), req.getParameter("userPassword"));

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(new File(getServletContext().getRealPath("/WEB-INF/classes/auth.properties")), true);
                    properties2.store(fos, "");
                } catch (Exception e) {
//                    System.out.println(e);
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
                resp.sendRedirect("/fm/first");
            } else {
                req.getRequestDispatcher("../views/registration.xhtml").forward(req, resp);
            }
        }
    }
}
