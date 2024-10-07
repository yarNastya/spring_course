package spring.cource.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@PropertySource("classpath:application.properties")
public class FileServiceImpl implements FileService {
    @Value("${fileName}")
    private String fileName;


    public BufferedReader readFile() {
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
