import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by vasiliev on 5/17/2017.
 */
class Search implements Files {
    public final Path start;
    public final String search;
    private PathMatcher matcher;

    public Search(Path start, String search) {
        this.start = start;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + (this.search = search));
    }

    @Override
    public String toString() {
        return start.toString();
    }

    @Override
    public File[] listFiles() {
        try {
            final List<File> files = new ArrayList<>();
            java.nio.file.Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                    Path name = file.getFileName();
                    if (name != null && matcher.matches(name))
                        files.add(file.toFile());
                    return CONTINUE;
                }
            });
            return files.toArray(new File[0]);
        } catch (IOException e) {
            return null;
        }
    }
}