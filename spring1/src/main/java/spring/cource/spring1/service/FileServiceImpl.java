package spring.cource.spring1.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileServiceImpl implements FileService {

    public BufferedReader readFile(String fileName) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(fileName);
            if (inputStream != null) {
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                return new BufferedReader(streamReader);
            } else {
                return null;
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            return null;
        }
    }
}
