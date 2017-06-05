package servlets;

import org.apache.log4j.Logger;
import utils.ProjectUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vasiliev on 5/23/2017.
 */

public class Download extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Download.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestPath = req.getParameter("path");
        String fileName = req.getParameter("fileName");

        String pathFromConfig = ProjectUtils.getDefaultPath(this, req);

        String path = pathFromConfig + (requestPath != null ? requestPath : File.separator);
        String downloadFilePath = path + File.separator + fileName;

        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(downloadFilePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
            logger.debug("MIME type: " + mimeType);
        }

        File downloadFile = new File(downloadFilePath);

        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        resp.setHeader(headerKey, headerValue);

        OutputStream outStream = resp.getOutputStream();

        FileInputStream fis = new FileInputStream(downloadFile);

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = fis.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        fis.close();
        outStream.close();
    }
}
