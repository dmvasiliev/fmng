package model;

import model.Files;

import java.io.File;
import java.util.Arrays;

/**
 * Created by vasiliev on 5/17/2017.
 */
public class Directory implements Files {
    public final File directory;

    public Directory(File directory) {
        if (!(this.directory = directory).isDirectory())
            throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return directory.getAbsolutePath();
    }

    @Override
    public File[] listFiles() {
        File[] files = directory.listFiles();
        Arrays.sort(files);
        return files;
    }
}
