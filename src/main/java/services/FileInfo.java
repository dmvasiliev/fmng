package services;

/**
 * Created by vasiliev on 5/19/2017.
 */
public class FileInfo {

    private String Name;
    private String relativePath;
    private boolean isDirectory;
    private boolean isFile;
    private long size;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String fileName) {
        this.Name = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
