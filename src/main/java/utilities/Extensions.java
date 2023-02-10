package utilities;

import java.io.*;
import java.util.Random;

/**
 * The utility object containing miscellaneous methods that
 * are needed throughout the application.
 */
public class Extensions {
    /**
     * Gets a random integer within two bounds.
     *
     * @param min the minimum value of the integer.
     * @param max the maximum value of the integer.
     * @return the random integer.
     */
    public static int getRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min)) + min;
    }

    /**
     * Reads an input stream and returns a string representation of the stream.
     *
     * @param is the input stream.
     * @return a string representation of the input stream.
     * @throws IOException if the input stream can not be read.
     */
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Writes a given string to a given output stream.
     *
     * @param str the given string.
     * @param os the given output stream.
     * @throws IOException if the string can not be written to the output stream.
     */
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
