package servlets;

import services.FileInfo;
import services.ProjectUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by vasiliev on 5/18/2017.
 */

@MultipartConfig
public class FileManager extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getParameter("path");

        String pathFromConfig = ProjectUtils.getDefaultPath(this);

        String path = pathFromConfig + (requestPath != null ? requestPath : File.separator);
        req.setAttribute("path", requestPath == null || requestPath.isEmpty() ? File.separator : requestPath);

        Path childPath = Paths.get(path);
        Path rootPath = Paths.get(pathFromConfig);
        boolean isChild = childPath.toAbsolutePath().startsWith(pathFromConfig) && !(rootPath.equals(childPath));
        req.setAttribute("isChild", isChild);
        req.setAttribute("parentPath", isChild && childPath.getParent() != null ? URLEncoder.encode(File.separator + rootPath.relativize(childPath.getParent()).toString(), "UTF-8") : File.separator);

        File[] listFiles = new File(path).listFiles();
        ArrayList<FileInfo> contents = new ArrayList<>();
        if (listFiles != null) {
            for (File file : listFiles) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(file.getName());
                String relativePath = rootPath.relativize(childPath).toString();
                fileInfo.setRelativePath(relativePath.isEmpty() ? relativePath : File.separator + relativePath);
                if (file.isDirectory()) {
                    fileInfo.setDirectory(true);
                    fileInfo.setSize(getFolderSize(file));

                } else {
                    fileInfo.setDirectory(false);
                }
                if (file.isFile()) {
                    fileInfo.setFile(true);
                    fileInfo.setSize(file.length());
                } else {
                    fileInfo.setFile(false);
                }

                contents.add(fileInfo);
            }
        }

        req.setAttribute("contents", contents);
        req.setAttribute("separator", File.separator);

        req.getRequestDispatcher("views/filemanager.jsp").forward(req, resp);
    }

    private long getFolderSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else
                    size += getFolderSize(file);
            }
        }
        return size;
    }
}
