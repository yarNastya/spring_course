package spring.cource.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@PropertySource("classpath:application.yml")
public class FileServiceImpl implements FileService {
    @Value("${file.name}")
    private String fileName;

    @Value("${file.extension}")
    private String fileExtension;

    private final Locale locale = Locale.getDefault();

    public BufferedReader readFile() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(fileName + "_" + locale.toString() + "." + fileExtension);
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
