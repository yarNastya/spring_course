package springCourse.storage.service;

import org.springframework.stereotype.Service;
import springCourse.storage.domain.Good;
import springCourse.storage.domain.Package;

import java.util.Collection;


@Service("packService")
public class PackService {
    private int orderNumber;

    //Собирает посылку
    public Package collect(Collection<Good> goods) throws Exception {
        System.out.println("Collecting order number " + ++orderNumber);
        Thread.sleep(2000);
        System.out.println("Order number " + orderNumber + " have been collected");
        return new Package(orderNumber);
    }

    //Запаковывает товар в коробку
    public Good packInBox(Good good) throws Exception {
        Thread.sleep(1000);
        System.out.println(good.getName() + " have been packed in box");
        return good;
    }

    //Запаковывает товар в пленку
    public Good packInMembrane(Good good) throws Exception {
        Thread.sleep(1000);
        System.out.println(good.getName() + " have been packed in membrane");
        return good;
    }
}
