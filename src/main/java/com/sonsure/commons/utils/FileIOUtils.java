package com.sonsure.commons.utils;

import com.sonsure.commons.exception.SonsureException;

import java.io.*;

/**
 * @author liyd
 */
public class FileIOUtils {

    /**
     * To byte array byte [ ].
     *
     * @param input the input
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(input, output, 1024);
            return output.toByteArray();
        }
    }

    /**
     * Write byte array to file.
     *
     * @param file the file
     * @param data the data
     * @throws IOException the io exception
     */
    public static void writeByteArrayToFile(final File file, final byte[] data) {
        writeByteArrayToFile(file, data, false);
    }

    /**
     * Write byte array to file.
     *
     * @param file   the file
     * @param data   the data
     * @param append the append
     * @throws IOException the io exception
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final boolean append) {

        try (OutputStream out = new FileOutputStream(file, append)) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            throw new SonsureException(e);
        }
    }

    /**
     * Copy long.
     *
     * @param input      the input
     * @param output     the output
     * @param bufferSize the buffer size
     * @return the long
     * @throws IOException the io exception
     */
    public static long copy(final InputStream input, final OutputStream output, int bufferSize)
            throws IOException {
        byte[] buffer = new byte[bufferSize];
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
