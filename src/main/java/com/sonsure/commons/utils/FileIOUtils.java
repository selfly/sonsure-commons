package com.sonsure.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
