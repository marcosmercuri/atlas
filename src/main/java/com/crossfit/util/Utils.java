package com.crossfit.util;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Utils {

    /**
     * Reads the content of the resource file, using the UTF-8 encoding
     * @param fileName the resource file name
     * @return the file content
     * @throws UnableToOpenResourceException if an I/O error occurs.
     */
    public static String loadResource(String fileName) {
        try {
            return Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
        } catch (IOException e) {
            throw new UnableToOpenResourceException(fileName, e);
        }
    }
}
