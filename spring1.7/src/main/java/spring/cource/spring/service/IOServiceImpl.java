package spring.cource.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {


    @Override
    public void writeString(String message) {
        System.out.println(message);
    }

    @Override
    public String readString() {
        Scanner in = new Scanner(System.in);
        if (in.hasNextLine()) {
            return in.nextLine();
        }
        return "";
    }


}
