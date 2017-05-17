import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by vasiliev on 5/17/2017.
 */
public class CommonsUtils {

    @SuppressWarnings("unused")
    public static void checkForPost(HttpServletRequest request) throws ServletException {
        if (!"POST".equals(request.getMethod()))
            throw new ServletException("method must be POST");
    }

    @SuppressWarnings("unused")
    public static byte[] readStream(InputStream input) throws IOException {
        return readStream(input, -1, true);
    }

    public static byte[] readStream(InputStream input, int length, boolean readAll) throws IOException {
        byte[] output = {};
        int position = 0;
        if (length == -1) length = Integer.MAX_VALUE;
        while (position < length) {
            int bytesToRead;
            if (position >= output.length) { // Only expand when there's no room
                bytesToRead = Math.min(length - position, output.length + 1024);
                if (output.length < position + bytesToRead)
                    output = Arrays.copyOf(output, position + bytesToRead);
            } else bytesToRead = output.length - position;
            int bytesRead = input.read(output, position, bytesToRead);
            if (bytesRead < 0) {
                if (!readAll || length == Integer.MAX_VALUE) {
                    if (output.length != position)
                        output = Arrays.copyOf(output, position);
                    break;
                } else throw new EOFException("Detect premature EOF");
            }
            position += bytesRead;
        }
        return output;
    }


}
