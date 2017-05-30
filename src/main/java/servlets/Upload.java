package servlets;

import org.apache.log4j.Logger;
import services.ProjectUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by vasiliev on 5/23/2017.
 */

@MultipartConfig
public class Upload extends HttpServlet {

    private final static Logger logger = Logger.getLogger(FileManager.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getParameter("path");

        String pathFromConfig = ProjectUtils.getDefaultPath(this);

        String path = pathFromConfig + (requestPath != null ? requestPath : File.separator);

        final Part filePart = req.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream fileContent = null;
        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            fileContent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            logger.debug("File" + fileName + "being uploaded to " + path);
        } catch (FileNotFoundException fne) {
            logger.error("Problems during file upload. Error: ", fne);
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }

        req.setAttribute("path", requestPath);
        req.getRequestDispatcher("fmanager").forward(req, resp);
//        resp.sendRedirect(requestPath != null ? "fmanager?path=" + URLEncoder.encode(requestPath, "UTF-8") : "fmanager");
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        logger.debug("Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
