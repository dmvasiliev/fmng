import org.apache.commons.fileupload.ParameterParser;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Map;


/**
 * Created by vasiliev on 5/17/2017.
 */
@MultipartConfig
public class FileManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ENCODING = "UTF-8";
    private static final int BUFFER_SIZE = 4096;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Files files = null;
        File file = null, parent;
        String path = request.getParameter("path");
        String type = request.getContentType();
        String search = request.getParameter("search");
        String mode;

        //ToDo
        if (path == null) {
            path = "E:\\";
        }

        if (path == null || !(file = new File(path)).exists())
            files = new Roots();
        else if (request.getParameter("zip") != null) {
            zip(response, file);
        } else if (request.getParameter("delete") != null) {
            delete(file);
        } else if ((mode = request.getParameter("mode")) != null) {
            mode(file, mode);
        } else if (file.isFile())
            downloadFile(response, file);
        else if (file.isDirectory()) {
            files = directory(request, files, file, type, search);
        } else throw new ServletException("Unknown type of file or folder.");

        if (files != null) {
            final PrintWriter writer = response.getWriter();
            writer.println("<!DOCTYPE html><html><head><style>*,input[type=\"file\"]::-webkit-file-upload-button{font-family:monospace}</style></head><body>");
            writer.println("<p>Current directory: " + files + "</p><pre>");
            if (!(files instanceof Roots)) {
                writer.print("<form method=\"post\"><label for=\"search\">Search Files:</label> <input type=\"text\" name=\"search\" id=\"search\" value=\"" + (search != null ? search : "") + "\"> <button type=\"submit\">Search</button></form>");
                writer.print("<form method=\"post\" enctype=\"multipart/form-data\"><label for=\"upload\">Upload Files:</label> <button type=\"submit\">Upload</button> <button type=\"submit\" name=\"unzip\">Upload & Unzip</button> <input type=\"file\" name=\"upload[]\" id=\"upload\" multiple></form>");
                writer.println();
            }
            if (files instanceof Directory) {
                writer.println("+ <a href=\"?path=" + URLEncoder.encode(path, ENCODING) + "\">.</a>");
                if ((parent = file.getParentFile()) != null)
                    writer.println("+ <a href=\"?path=" + URLEncoder.encode(parent.getAbsolutePath(), ENCODING) + "\">..</a>");
                else writer.println("+ <a href=\"?path=\">..</a>");
            }

            for (File child : files.listFiles()) {
                writer.print(child.isDirectory() ? "+ " : "  ");
                writer.print("<a href=\"?path=" + URLEncoder.encode(child.getAbsolutePath(), ENCODING) + "\" title=\"" + child.getAbsolutePath() + "\">" + child.getName() + "</a>");
                if (child.isDirectory())
                    writer.print(" <a href=\"?path=" + URLEncoder.encode(child.getAbsolutePath(), ENCODING) + "&zip\" title=\"download\">&#8681;</a>");
                writer.println();
            }
            writer.print("</pre></body></html>");
            writer.flush();
        }
    }

    private Files directory(HttpServletRequest request, Files files, File file, String type, String search) throws IOException, ServletException {
        if (search != null && !search.isEmpty())
            files = new Search(file.toPath(), search);
        else if (type != null && type.startsWith("multipart/form-data")) {
            for (Part part : request.getParts()) {
                String name;
                if ((name = partFileName(part)) == null) //retrieves <input type="file" name="...">, no other (e.g. input) form fields
                    continue;
                if (request.getParameter("unzip") == null)
                    try (OutputStream output = new FileOutputStream(new File(file, name))) {
                        copyStream(part.getInputStream(), output);
                    }
                else ZipUtil.unpack(part.getInputStream(), file);
            }
        } else files = new Directory(file);
        return files;
    }

    private void mode(File file, String mode) {
        boolean add = mode.startsWith("+");
        if (mode.indexOf('r') > -1)
            file.setReadable(add);
        if (mode.indexOf('w') > -1)
            file.setWritable(add);
        if (mode.indexOf('x') > -1)
            file.setExecutable(add);
    }

    private void delete(File file) throws IOException {
        if (file.isFile())
            file.delete();
        else if (file.isDirectory()) {
            java.nio.file.Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    java.nio.file.Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    java.nio.file.Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    private void zip(HttpServletResponse response, File file) throws IOException {
        File zipFile = File.createTempFile(file.getName() + "-", ".zip");
        if (file.isFile())
            ZipUtil.addEntry(zipFile, file.getName(), file);
        else if (file.isDirectory())
            ZipUtil.pack(file, zipFile);
        downloadFile(response, zipFile, permamentName(zipFile.getName()), "application/zip");
    }

    public static void downloadFile(HttpServletResponse response, File file) throws IOException {
        downloadFile(response, file, file.getName());
    }

    public static void downloadFile(HttpServletResponse response, File file, String name) throws IOException {
        String contentType = java.nio.file.Files.probeContentType(file.toPath());
        downloadFile(response, file, name, contentType != null ? contentType : "application/octet-stream");
    }

    public static void downloadFile(HttpServletResponse response, File file, String name, String contentType) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        copyStream(new FileInputStream(file), response.getOutputStream());
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        int read;
        byte buffer[] = new byte[BUFFER_SIZE];
        while ((read = input.read(buffer)) > 0)
            output.write(buffer, 0, read);
    }

    public static String permamentName(String temporaryName) {
        return temporaryName.replaceAll("-\\d+(?=\\.(?!.*\\.))", "");
    }

    public String partFileName(Part part) {
        String header, file = null;
        if ((header = part.getHeader("content-disposition")) != null) {
            String lowerHeader = header.toLowerCase(Locale.ENGLISH);
            if (lowerHeader.startsWith("form-data") || lowerHeader.startsWith("attachment")) {
                ParameterParser parser = new ParameterParser();
                parser.setLowerCaseNames(true);
                Map<String, String> parameters = parser.parse(header, ';');
                if (parameters.containsKey("filename"))
                    file = (file = parameters.get("filename")) != null ? file.trim() : "";
            }
        }
        return file;
    }
}