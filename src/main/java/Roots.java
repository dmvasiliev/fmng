import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Created by vasiliev on 5/17/2017.
 */
class Roots implements Files {
    private static final FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    @Override
    public String toString() {
        return "root";
    }

    @Override
    public File[] listFiles() {
        File[] roots = File.listRoots();
        for (int root = 0; root < roots.length; root++) {
            final File originalRoot = roots[root];
            roots[root] = new File(roots[root].toURI()) {
                private static final long serialVersionUID = 1l;

                @Override
                public String getName() {
                    String displayName = fileSystemView.getSystemDisplayName(originalRoot);
                    return displayName != null && !displayName.isEmpty() ? displayName : originalRoot.getPath();
                }
            };
        }
        return roots;
    }
}