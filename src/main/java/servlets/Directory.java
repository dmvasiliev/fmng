package servlets;

import com.google.gson.Gson;
import services.FileInfo;
import services.ProjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.nio.file.Paths.get;

/**
 * Created by vasiliev on 5/25/2017.
 */

public class Directory extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> options = new LinkedHashMap<>();

        String requestPath = request.getParameter("path");
        String pathFromConfig = ProjectUtils.getDefaultPath(this);
        String path = pathFromConfig + (requestPath != null ? File.separator + requestPath : File.separator);
        options.put("path", requestPath == null || requestPath.isEmpty() ? File.separator : requestPath);

        Path childPath = get(path);
        Path rootPath = get(pathFromConfig);

        Boolean isChild = childPath.toAbsolutePath().startsWith(pathFromConfig) && !(rootPath.equals(childPath));
        options.put("isChild", isChild.toString());

        options.put("parentPath", isChild && childPath.getParent() != null ? File.separator + rootPath.relativize(childPath.getParent()) : File.separator);

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

        options.put("contents", contents);
        options.put("separator", File.separator);

        String json = new Gson().toJson(options);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
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
