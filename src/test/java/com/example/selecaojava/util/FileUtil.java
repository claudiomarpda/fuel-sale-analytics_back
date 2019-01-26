package com.example.selecaojava.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {

    public static byte[] getFileBytes(String fullPath) throws IOException {
        File file = new File(fullPath);
        byte[] data = new byte[(int) file.length()];
        new DataInputStream(new FileInputStream(file)).readFully(data);
        return data;
    }
}
